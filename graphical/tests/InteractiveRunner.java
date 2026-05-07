package tests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Consumer;

/**
 * Interaktív (konzolos) tesztelő futtató — a 7. heti dokumentumban definiált
 * konzolos parancsnyelvet implementálja, mind a 16 parancsalapú teszthez (4–19).
 *
 * Használat (a meglévő run_tests.bat / run_tests.sh fordítása után):
 *   java -cp bin tests.InteractiveRunner
 *
 * A felhasználó soronként gépeli a parancsokat, ahogy a
 * test_data/input/test{N}_in.txt fájlokban szerepelnek.
 *
 * A `load <fájl>` parancs beolvassa a test_data/arrange/&lt;fájl&gt;-t és a
 * deklaratív arrange-parancsokkal felépíti a kezdő szcenáriót (ugyanazon
 * az {@link ArrangeContext}-en, amit a batch tesztek is használnak az
 * {@link ArrangeRunner}-en keresztül). Onnantól minden további parancs ezen
 * a szcenárión fut le; a kimenet ugyanaz a "[entitás] [mező]: régi -> új"
 * formátum, mint a batch-futtatásnál.
 *
 * Az arrange-nyelv leírása az {@link ArrangeContext} osztály doc-jában
 * található; a futás-idejű parancsok (fej_csere, mozgas, takarit, vasarol,
 * inspect, stb.) a {@link TestSupport} dispatch-jén keresztül futnak.
 */
public class InteractiveRunner {

    private static final Path ARRANGE_DIR = Paths.get("test_data", "arrange");

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(System.in, StandardCharsets.UTF_8));

        System.out.println("=== Zúzmaraváros — Konzolos Tesztelő ===");
        System.out.println("Soronként gépeld be a parancsokat (lásd test_data/input/test{N}_in.txt).");
        System.out.println("Kezdd egy load paranccsal, pl.: load test14_arrange.txt");
        System.out.println("'exit' vagy Ctrl+Z/Ctrl+D = kilépés.");
        System.out.println();

        Consumer<String> dispatch = null;

        try {
            while (true) {
                System.out.print("> ");
                System.out.flush();
                String line = reader.readLine();
                if (line == null) break;
                String trimmed = line.trim();
                if (trimmed.equalsIgnoreCase("exit")) break;

                if (trimmed.toLowerCase().startsWith("load ")) {
                    String arg = trimmed.substring(5).trim();
                    Consumer<String> loaded = loadScene(arg);
                    if (loaded != null) {
                        dispatch = loaded;
                        System.out.println("[load] " + arg + " betöltve.");
                    }
                    continue;
                }

                if (dispatch == null) {
                    if (trimmed.isEmpty()) continue;
                    System.err.println("[Hiba] Először tölts be egy szcenáriót: load test{N}_arrange.txt");
                    continue;
                }

                try {
                    dispatch.accept(line);
                } catch (Exception ex) {
                    System.err.println("[Hiba a parancs futtatása közben]: " + ex.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Bemenet olvasási hiba: " + e.getMessage());
        }

        System.out.println("Kilépés.");
    }

    /** Beolvas egy arrange-fájlt és visszaad egy futás-idejű parancsfeldolgozót. */
    private static Consumer<String> loadScene(String arg) {
        String fileName = stripPath(arg);
        Path arrangePath = ARRANGE_DIR.resolve(fileName);
        if (!Files.exists(arrangePath)) {
            System.err.println("[Hiba] Nem található arrange-fájl: " + arrangePath);
            return null;
        }
        try {
            List<String> lines = Files.readAllLines(arrangePath, StandardCharsets.UTF_8);
            ArrangeContext arr = new ArrangeContext();
            int lineNo = 0;
            for (String l : lines) {
                lineNo++;
                try {
                    arr.execute(l);
                } catch (Exception ex) {
                    System.err.println("[Hiba] " + arrangePath + ":" + lineNo + " — " + ex.getMessage());
                    return null;
                }
            }
            return arr.dispatcher();
        } catch (IOException ex) {
            System.err.println("[Hiba] arrange-fájl olvasása: " + ex.getMessage());
            return null;
        }
    }

    private static String stripPath(String arg) {
        int lastSlash = Math.max(arg.lastIndexOf('/'), arg.lastIndexOf('\\'));
        return (lastSlash >= 0) ? arg.substring(lastSlash + 1) : arg;
    }
}
