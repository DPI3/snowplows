package tests;

public class test8 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 8: Útvonal újratervezése akadály esetén ===");

        Lane laneCurrent = new Lane("lane_current", null, null);
        Lane laneNext = new Lane("lane_next", null, null);
        Lane laneBypass1 = new Lane("lane_bypass_1", null, null);

        Route routeOriginal = new Route("route_original");
        Route routeRecalculated = new Route("route_recalculated");

        Car car1 = new Car("car_1", laneCurrent, 50.0);
        car1.setCurrentRoute(routeOriginal);


        System.out.println("[lane_next] [currentState]: Clear -> DeepSnow");

        System.out.println("[Console]: \"Figyelmeztetés: A következő útszakasz járhatatlan. Útvonal újratervezése...\"");
        
        car1.setCurrentRoute(routeRecalculated);
        System.out.println("[car_1] [currentRoute]: route_original -> route_recalculated");
        
        System.out.println("[Console]: \"Új útvonal sikeresen kijelölve.\"");

        car1.changeLane(laneBypass1);
        System.out.println("[car_1] [currentLane]: lane_current -> lane_bypass_1");
    }
}