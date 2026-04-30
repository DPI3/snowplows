package tests;

public class test18 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 18: Jég felolvasztása sószórással ===");

        // Bemenet szimulálása
        System.out.println("load test18_arrange.txt");
        System.out.println("fej_csere plow_1 saltspreader");
        System.out.println("mozgas plow_1 lane_ice2");
        System.out.println("takarit plow_1");
        System.out.println("save test18_out.txt");
        System.out.println("exit");
        
        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[plow_1] [currentHead]: SweeperHead -> SaltSpreaderHead");
        System.out.println("[plow_1] [currentLane]: lane_start -> lane_ice2");
        System.out.println("[plow_1] [saltStock]: 10 -> 9");
        System.out.println("[lane_ice2] [currentState]: IceSheet -> Clear");
        System.out.println("[lane_ice2] [iceThickness]: 5.0 -> 0.0");
        System.out.println("[cleaner_1] [money]: 100 -> 150");
    }
}