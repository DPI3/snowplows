package tests;

public class test8 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 8: Útvonal újratervezése akadály esetén ===");

        // Bemenet szimulálása
        System.out.println("load test8_arrange.txt");
        System.out.println("allapot_allit lane_next deepsnow");
        System.out.println("mozgas car_1");
        System.out.println("mozgas car_1");
        System.out.println("save test8_out.txt");
        System.out.println("exit");
        
        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[lane_next] [currentState]: Clear -> DeepSnow");
        System.out.println("[Console]: Figyelmeztetés: A következő útszakasz járhatatlan. Útvonal újratervezése...");
        System.out.println("[car_1] [currentRoute]: route_original -> route_recalculated");
        System.out.println("[Console]: Új útvonal sikeresen kijelölve.");
        System.out.println("[car_1] [currentLane]: lane_current -> lane_bypass_1");
    }
}