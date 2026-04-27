package tests;

public class test17 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 17: Jégpáncél feltörése jégtörővel ===");

        // Bemenet szimulálása
        System.out.println("load test17_arrange.txt");
        System.out.println("fej_csere plow_1 icebreaker");
        System.out.println("mozgas plow_1 lane_ice");
        System.out.println("takarit plow_1");
        System.out.println("save test17_out.txt");
        System.out.println("exit");
        
        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[plow_1] [currentHead]: SweeperHead -> IcebreakerHead");
        System.out.println("[plow_1] [currentLane]: lane_start -> lane_ice");
        System.out.println("[lane_ice] [currentState]: IceSheet -> BrokenIce");
    }
}