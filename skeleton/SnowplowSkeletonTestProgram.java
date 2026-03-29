package skeleton;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import skeleton.tests.*;

/**
 * A SnowplowSkeletonTestProgram osztály a szkeleton rendszer
 * terminál alapú kezelői felületét valósítja meg.
 *
 * A program lehetőséget biztosít előre definiált tesztesetek
 * futtatására parancssoros módon.
 *
 * Támogatott parancsok:
 * - help: parancsok listázása
 * - ls: tesztesetek listázása
 * - run test <szám>: adott teszt futtatása
 * - exit: program leállítása
 *
 * A tesztek a tests csomagban található osztályokként vannak implementálva,
 * és a szekvenciadiagramoknak megfelelő kimenetet generálnak.
 *
 * A program célja a rendszer működésének ellenőrzése és demonstrálása.
 */
public class SnowplowSkeletonTestProgram {

    private static final Map<Integer, String> TESTS = new LinkedHashMap<>();

    static {
        TESTS.put(1, "Játék indítása, inicializáló teszt");
        TESTS.put(2, "Havazás hatása az útállapotra teszt");
        TESTS.put(3, "Alagút időjárás-mentessége teszt");
        TESTS.put(4, "Jármű behajtása kereszteződésbe teszt");
        TESTS.put(5, "Jármű sávváltás teszt");
        TESTS.put(6, "Hókotró haladása teszt");
        TESTS.put(7, "Takarítás söprő fejjel teszt");
        TESTS.put(8, "Takarítás hányó fejjel teszt");
        TESTS.put(9, "Takarítás jégtörő fejjel teszt");
        TESTS.put(10, "Takarítás sószóró fejjel teszt");
        TESTS.put(11, "Takarítás sárkány fejjel teszt");
        TESTS.put(12, "Sikeres hókotró vásárlás teszt");
        TESTS.put(13, "Sikeres kotrófej vásárlás teszt");
        TESTS.put(14, "Sikeres biokerozin vásárlás teszt");
        TESTS.put(15, "Sikeres só vásárlás teszt");
        TESTS.put(16, "Sikertelen vásárlás teszt");
        TESTS.put(17, "Kotrófej cseréje teszt");
        TESTS.put(18, "Nyersanyag kifogyás teszt");
        TESTS.put(19, "Pontszerzés takarítással teszt");
        TESTS.put(20, "Busz útvonalhoz rendelés teszt");
        TESTS.put(21, "Busz közlekedés teszt");
        TESTS.put(22, "Busz forduló teljesítése teszt");
        TESTS.put(23, "Autó haladása járható sávban teszt");
        TESTS.put(24, "Autó elakadása járhatatlan úton teszt");
        TESTS.put(25, "Autó célba érése teszt");
        TESTS.put(26, "Autó útvonalának újratervezése akadály esetén teszt");
        TESTS.put(27, "Jégpáncél kialakulása teszt");
        TESTS.put(28, "Ütközés: autó-autó teszt");
        TESTS.put(29, "Ütközés: autó-busz teszt");
        TESTS.put(30, "Ütközés: busz-busz teszt");
        TESTS.put(31, "Busz mozgásképtelenség megszűnése teszt");
        TESTS.put(32, "Játék kiértékelése teszt");
        TESTS.put(33, "Útvonalkereső algoritmus, legrövidebb út tesztje");
    }


    /**
     * A SnowplowSkeletonTestProgram osztály a szkeleton rendszer
     * terminál alapú kezelői felületét valósítja meg.
     *
     * A program lehetőséget biztosít előre definiált tesztesetek
     * futtatására parancssoros módon.
     *
     * Támogatott parancsok:
     * - help: parancsok listázása
     * - ls: tesztesetek listázása
     * - run test <szám>: adott teszt futtatása
     * - exit: program leállítása
     *
     * A tesztek a tests csomagban található osztályokként vannak implementálva,
     * és a szekvenciadiagramoknak megfelelő kimenetet generálnak.
     *
     * A program célja a rendszer működésének ellenőrzése és demonstrálása.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        skeleton.src.Skeleton.setScanner(scanner);

        if (args.length > 0) {
            try {
                int testNum = Integer.parseInt(args[0]);
                runTest(testNum);
                return; // A teszt lefutása után kilépünk, nem megyünk a menübe
            } catch (NumberFormatException e) {
                // Ha nem szám az argumentum, megyünk tovább a normál menüre
            }
        }

        printWelcome();

        boolean running = true;
        while (running && scanner.hasNextLine()) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("help")) {
                printHelp();
            } else if (input.equalsIgnoreCase("ls")) {
                listTests();
            } else if (input.equalsIgnoreCase("exit")) {
                System.out.println("Snowplow Skeleton TestProgram terminated.");
                running = false;
            } else if (input.toLowerCase().startsWith("run test")) {
                runTestCommand(input);
            } else if (input.isEmpty()) {
                // no operation
            } else {
                System.out.println("[ERROR] Unknown command.");
                System.out.println("Type 'help' for the list of available commands.");
            }
        }

        scanner.close();
    }

    private static void printWelcome() {
        System.out.println("Snowplow Skeleton TestProgram");
        System.out.println("For help type help and press enter after the > mark.");
        System.out.println("For exit type exit after the > mark.");
    }

    private static void printHelp() {
        System.out.println("Write commands after the > mark.");
        System.out.println("help: Lists all available commands and their usage.");
        System.out.println("ls: Lists all available test cases with their numbers.");
        System.out.println("run test <test_number>: Runs the selected test sequence.");
        System.out.println("exit: Terminates the Snowplow Skeleton TestProgram.");
    }

    private static void listTests() {
        for (Map.Entry<Integer, String> entry : TESTS.entrySet()) {
            System.out.println(entry.getKey() + ". " + entry.getValue());
        }
    }


    /**
     * Lefuttatja a megadott sorszámú tesztet.
     *
     * A metódus kiválasztja a megfelelő tesztosztályt,
     * majd meghívja annak run() metódusát.
     *
     * @param testNumber a futtatandó teszt sorszáma
     */
    private static void runTestCommand(String input) {
        String[] parts = input.split("\\s+");

        if (parts.length != 3) {
            System.out.println("[ERROR] Invalid command format.");
            System.out.println("Usage: run test <test_number>");
            return;
        }

        try {
            int testNumber = Integer.parseInt(parts[2]);
            runTest(testNumber);
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Test number must be an integer.");
        }
    }

    private static void runTest(int testNumber) {
        if (!TESTS.containsKey(testNumber)) {
            System.out.println("[ERROR] Test not found.");
            return;
        }

        TestCase test = getTest(testNumber);

        if (test == null) {
            System.out.println("[ERROR] Test implementation missing.");
            return;
        }

        // 1. Eredeti konzol kimenet elmentése
        PrintStream originalOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // 2. "T-elágazás" létrehozása: egy stream, ami egyszerre ír a képernyőre ÉS a memóriába
        java.io.OutputStream teeStream = new java.io.OutputStream() {
            @Override
            public void write(int b) {
                originalOut.write(b);
                baos.write(b);
            }
            @Override
            public void write(byte[] b, int off, int len) {
                originalOut.write(b, off, len);
                baos.write(b, off, len);
            }
        };
        PrintStream customOut = new PrintStream(teeStream, true);

        // 3. Kimenet átirányítása a kettős streambe
        System.setOut(customOut);

        // 4. Teszt futtatása (a RUNNING és RESULT is ide kerül, hogy benne legyen az assertben!)
        System.out.println("[RUNNING] " + testNumber + ". " + TESTS.get(testNumber));
        
        test.run();
        
        System.out.println("[RESULT] The test completed successfully.");

        // 5. Eredeti kimenet visszaállítása
        System.setOut(originalOut);

        // 6. Összehasonlítás az assert fájllal
        try {
            String assertFilePath = "asserts/test" + testNumber + "_assert.txt";
            String expectedOutput = new String(Files.readAllBytes(Paths.get(assertFilePath)));

            String actualOutput = baos.toString();

            // Sortörések (Windows \r\n vs Linux \n) és extra szóközök egységesítése
            String normalizedActual = actualOutput.replaceAll("\\r\\n", "\n").trim();
            String normalizedExpected = expectedOutput.replaceAll("\\r\\n", "\n").trim();

            if (normalizedActual.equals(normalizedExpected)) {
                // System.err-t használunk, hogy a .bat fájlos fájlba-irányítást (> temp_out.txt) ez ne rontsa el!
                System.err.println("\n[ZÖLD] A " + testNumber + ". teszt SIKERES! (Kimenet megegyezik)");
            } else {
                System.err.println("\n[PIROS] A " + testNumber + ". teszt ELBUKOTT! (Kimenet eltér)");
            }
        } catch (IOException e) {
            System.err.println("\n[SARGA] A teszt lefutott, de nem található az assert fájl: asserts/test" + testNumber + "_assert.txt");
        }
    }


    /**
     * A tesztszám alapján visszaadja a megfelelő tesztobjektumot.
     *
     * A kiválasztás switch-case szerkezet segítségével történik.
     *
     * @param testNumber a teszt sorszáma
     * @return a megfelelő TestCase példány, vagy null ha nincs implementálva
     */
    private static TestCase getTest(int testNumber) {
        switch (testNumber) {
            case 1: return new Test1();
            case 2: return new Test2();
            case 3: return new Test3();
            //case 4: return new Test4();
            case 5: return new Test5();
            case 6: return new Test6();
            case 7: return new Test7();
            case 8: return new Test8();
            //case 9: return new Test9();
            //case 10: return new Test10();
            case 11: return new Test11();
            //case 12: return new Test12();*/
            case 13: return new Test13();
            /*case 14: return new Test14();
            case 15: return new Test15();
            case 16: return new Test16();
            case 17: return new Test17();
            case 18: return new Test18();
            case 19: return new Test19();
            case 20: return new Test20();*/
            //case 21: return new Test21();
            /*case 22: return new Test22();
            case 23: return new Test23();
            case 24: return new Test24();
            case 25: return new Test25();
            case 26: return new Test26();
            case 27: return new Test27();
            case 28: return new Test28();
            case 29: return new Test29();
            case 30: return new Test30();
            case 31: return new Test31();
            case 32: return new Test32();
            case 33: return new Test33();*/
            default: return null;
        }
    }
}