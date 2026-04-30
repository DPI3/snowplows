package tests;

public class test5 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 5: Busz útvonalának automatikus kijelölése és teljesítése ===");

        // Bemenet szimulálása
        System.out.println("load test5_arrange.txt");
        System.out.println("auto_utvonal bus_1");
        System.out.println("mozgas bus_1 lane_t1_1");
        System.out.println("mozgas bus_1 lane_t1_2");
        System.out.println("mozgas bus_1 lane_t2");
        System.out.println("save test5_out.txt");
        System.out.println("exit");

        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[system] [selectedStart]: term_a");
        System.out.println("[system] [selectedDestination]: term_b");
        System.out.println("[bus_1] [currentRoute]: null -> route_1");
        System.out.println("[bus_1] [currentLane]: lane_start -> lane_t1_1");
        System.out.println("[bus_1] [currentLane]: lane_t1_1 -> lane_t1_2");
        System.out.println("[bus_1] [currentLane]: lane_t1_2 -> lane_t2");
        System.out.println("[bus_1] [location]: úton -> term_b");
        System.out.println("[busdriver_1] [money]: 100 -> 160");
        System.out.println("[log] Forduló teljesítve: bus_1, jutalom: 60");
        System.out.println("[bus_1] [currentRoute]: route_1 -> route_2");
    }
}