package tests;

public class test9 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 9: Ütközések kezelése automatikus és játékos által vezérelt járművek között ===");

        // Bemenet szimulálása
        System.out.println("load test9_arrange.txt");
        System.out.println("mozgas car_1 lane_conflict_1");
        System.out.println("mozgas car_2 lane_conflict_1");
        System.out.println("mozgas bus_1 lane_conflict_2");
        System.out.println("save test9_out.txt");
        System.out.println("exit");
        
        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[lane_conflict_1] [hasAccident]: false -> true");
        System.out.println("[Console]: Ütközés történt két automatikus jármű között (car_1, car_2). Az útszakasz blokkolva.");
        System.out.println("[car_1] [speed]: 50.0 -> 0.0");
        System.out.println("[car_2] [speed]: 50.0 -> 0.0");
        System.out.println("[lane_conflict_2] [hasAccident]: false -> true");
        System.out.println("[Console]: Baleset: A játékos által vezetett bus_1 ütközött. Büntetés kiszabva.");
        System.out.println("[bus_1] [speed]: 40.0 -> 0.0");
        System.out.println("[busdriver_1] [score]: 100 -> 50");
    }
}