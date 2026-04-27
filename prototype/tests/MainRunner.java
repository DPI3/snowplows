package tests;

public class MainRunner {

    public static void main(String[] args) {
        // 1. Ellenőrizzük, hogy kaptunk-e paramétert (pl. "test16")
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