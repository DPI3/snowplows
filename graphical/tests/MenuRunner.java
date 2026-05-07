package tests;

import java.util.Scanner;

/**
 * Központi menü felület a Zúzmaraváros szimulációhoz.
 * Lehetővé teszi az interaktív mód vagy egyedi tesztek futtatását.
 */
public class MenuRunner {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("==========================================");
        System.out.println("   Zúzmaraváros Szimulációs Rendszer");
        System.out.println("==========================================");

        while (running) {
            System.out.println("\nKérlek, válassz egy opciót:");
            System.out.println("  1. Interaktív tesztelő indítása");
            System.out.println("  2. Egyedi teszt futtatása (pl. test1, test14)");
            System.out.println("  3. Kilépés");
            System.out.print("\nVálasztás (1-3): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    System.out.println("\n>>> Interaktív mód indítása...\n");
                    // Meghívjuk a már meglévő InteractiveRunner-t
                    InteractiveRunner.main(new String[]{});
                    break;
                case "2":
                    System.out.print("\nAdd meg a teszt nevét (pl. test14): ");
                    String testName = scanner.nextLine().trim();
                    if (!testName.isEmpty()) {
                        System.out.println("\n>>> A(z) " + testName + " futtatása...\n");
                        // Meghívjuk a MainRunner-t a megadott teszt névvel
                        MainRunner.main(new String[]{testName});
                    } else {
                        System.out.println("Hibás teszt név!");
                    }
                    break;
                case "3":
                    running = false;
                    System.out.println("\nKilépés a Zúzmaraváros rendszerből. Viszlát!");
                    break;
                default:
                    System.out.println("\n[Hiba] Érvénytelen választás. Kérlek, az 1, 2 vagy 3 opciók közül válassz!");
            }
        }

        scanner.close();
    }
}