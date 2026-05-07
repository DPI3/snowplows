package tests;

/**
 * A batch tesztelő belépési pontja: a parancssoron megkapott teszt-azonosító
 * (pl. "test14") alapján reflexióval példányosítja a megfelelő
 * {@link TestCase} osztályt és lefuttatja annak {@code run()} metódusát.
 *
 * A {@code run_tests.bat}/{@code run_tests.sh} ezt hívja minden tesztre,
 * a kimenetet fájlba irányítja és összehasonlítja a megfelelő assert fájllal.
 */
public class MainRunner {

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Hiba: Nem adtál meg teszt nevet! Használat: java tests.MainRunner <teszt_neve>");
            return;
        }

        String testName = args[0];

        try { 
            Class<?> clazz = Class.forName("tests." + testName);

            if (TestCase.class.isAssignableFrom(clazz)) {
                TestCase testInstance = (TestCase) clazz.getDeclaredConstructor().newInstance();
                testInstance.run();
            } else {
                System.err.println("Hiba: A(z) " + testName + " osztály nem valósítja meg a TestCase interfészt!");
            }

        } catch (ClassNotFoundException e) {
            System.err.println("Hiba: Nem található ilyen nevű teszt: " + testName);
        } catch (Exception e) {
            System.err.println("Váratlan hiba történt a(z) " + testName + " futtatása során:");
            e.printStackTrace();
        }
    }
}