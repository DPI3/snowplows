package tests;

public class test16 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 16: Vastag hóréteg eltakarítása ThrowerHead-del ===");

        // Bemenet szimulálása
        System.out.println("load paja_config.txt");
        System.out.println("fej_csere plow_1 thrower");
        System.out.println("mozgas plow_1 lane_5");
        System.out.println("takarit plow_1");
        System.out.println("save kimenet_16.txt");
        System.out.println("exit");
        
        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[plow_1] [currentHead]: SweeperHead -> ThrowerHead");
        System.out.println("[plow_1] [currentLane]: lane_1 -> lane_5");
        System.out.println("[lane_5] [currentState]: DeepSnow -> Clear");
        System.out.println("[lane_5] [snowThickness]: 5.0 -> 0.0");
        System.out.println("[cleaner_1] [money]: 100 -> 150");
    }
}