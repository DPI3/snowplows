package tests;

public class test19 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 19: Hó és jég leolvasztása sárkányfejjel ===");

        // Bemenet szimulálása
        System.out.println("load test19_arrange.txt");
        System.out.println("fej_csere plow_1 dragonhead");
        System.out.println("mozgas plow_1 lane_mixed");
        System.out.println("takarit plow_1");
        System.out.println("save test19_out.txt");
        System.out.println("exit");
        
        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[plow_1] [currentHead]: SweeperHead -> DragonHead");
        System.out.println("[plow_1] [currentLane]: lane_start -> lane_mixed");
        System.out.println("[plow_1] [biokeroseneStock]: 5 -> 4");
        System.out.println("[lane_mixed] [currentState]: DeepSnow -> Clear");
        System.out.println("[lane_mixed] [snowThickness]: 8.0 -> 0.0");
        System.out.println("[cleaner_1] [money]: 150 -> 200");
    }
}