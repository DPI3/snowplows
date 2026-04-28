package tests;

public class test10 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 10: Autó és busz elakadása mély hóban ===");

        // Bemenet szimulálása
        System.out.println("load test10_arrange.txt");
        System.out.println("allapot_allit lane_snow_car deepsnow");
        System.out.println("allapot_allit lane_snow_bus deepsnow");
        System.out.println("mozgas car_1 lane_snow_car");
        System.out.println("mozgas bus_1 lane_snow_bus");
        System.out.println("takarit plow_1 lane_snow_car");
        System.out.println("takarit plow_1 lane_snow_bus");
        System.out.println("mozgas car_1");
        System.out.println("mozgas bus_1");
        System.out.println("save test10_out.txt");
        System.out.println("exit");
        
        System.out.println();
        System.out.println("--- Elvárt kimenet ---");

        // Elvárt kimenet generálása a dokumentum alapján
        System.out.println("[lane_snow_car] [currentState]: Clear -> DeepSnow");
        System.out.println("[lane_snow_bus] [currentState]: Clear -> DeepSnow");
        System.out.println("[Console]: Az autó (car_1) elakadt a mély hóban.");
        System.out.println("[car_1] [speed]: 50.0 -> 0.0");
        System.out.println("[Console]: A busz (bus_1) elakadt a mély hóban.");
        System.out.println("[bus_1] [speed]: 40.0 -> 0.0");
        System.out.println("[lane_snow_car] [currentState]: DeepSnow -> Clear");
        System.out.println("[lane_snow_bus] [currentState]: DeepSnow -> Clear");
        System.out.println("[Console]: Az út letakarítva. Az autó (car_1) kiszabadult az elakadásból.");
        System.out.println("[car_1] [speed]: 0.0 -> 50.0");
        System.out.println("[car_1] [currentLane]: lane_snow_car -> lane_next_car");
        System.out.println("[Console]: Az út letakarítva. A busz (bus_1) kiszabadult az elakadásból.");
        System.out.println("[bus_1] [speed]: 0.0 -> 40.0");
        System.out.println("[bus_1] [currentLane]: lane_snow_bus -> lane_next_bus");
    }
}