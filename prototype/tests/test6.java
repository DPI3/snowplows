package tests;

public class test6 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 6: Busz közlekedtetése forduló teljesítéséhez ===");

        // Bemenet szimulálása
        System.out.println("load test6_arrange.txt");
        System.out.println("kijelol bus_1");
        System.out.println("mozgas bus_1 lane_21");
        System.out.println("mozgas bus_1 lane_22");
        System.out.println("mozgas bus_1 lane_term");
        System.out.println("save test6_out.txt");
        System.out.println("exit");

        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[selectedVehicle] null -> bus_1");
        System.out.println("[bus_1] [currentLane]: lane_start -> lane_21");
        System.out.println("[bus_1] [currentLane]: lane_21 -> lane_22");
        System.out.println("[bus_1] [currentLane]: lane_22 -> lane_term");
        System.out.println("[bus_1] [location]: úton -> terminal_2");
        System.out.println("[busdriver_1] [completedRounds]: 0 -> 1");
        System.out.println("[busdriver_1] [money]: 80 -> 130");
        System.out.println("[log] Forduló befejezve: bus_1");
    }
}