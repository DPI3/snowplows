package tests;

public class test7 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 7: Autók automatikus közlekedése lakhely és munkahely között ===");

        // Bemenet szimulálása
        System.out.println("load test7_arrange.txt");
        System.out.println("tick");
        System.out.println("tick");
        System.out.println("tick");
        System.out.println("save test7_out.txt");
        System.out.println("exit");

        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[car_1] [residence]: home_1");
        System.out.println("[car_1] [workplace]: work_1");
        System.out.println("[car_1] [currentRoute]: null -> route_car_1");
        System.out.println("[car_1] [currentLane]: lane_home -> lane_mid");
        System.out.println("[car_1] [currentLane]: lane_mid -> lane_work");
        System.out.println("[car_1] [location]: úton -> work_1");
        System.out.println("[log] car_1 megérkezett a munkahelyre");
    }
}