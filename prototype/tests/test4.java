package tests;

public class test4 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 4: Vásárlás a boltban ===");

        // Bemenet szimulálása
        System.out.println("load test4_arrange.txt");
        System.out.println("bolt_nyit");
        System.out.println("vasarol salt 3");
        System.out.println("save test4_out.txt");
        System.out.println("exit");

        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[store] [open]: false -> true");
        System.out.println("[store] [selectedItem]: null -> salt");
        System.out.println("[cleaner_1] [money]: 100 -> 70");
        System.out.println("[plow_1] [saltStock]: 0 -> 3");
        System.out.println("[log] Vásárlás sikeres: salt x3");
    }
}