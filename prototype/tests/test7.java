package tests;

public class test7 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 7: Autók automatikus közlekedése lakhely és munkahely között ===");

        Residence home = new Residence("home_1");
        Workplace work = new Workplace("work_1");

        Lane laneHome = new Lane("lane_home", home, null);
        Lane laneMid = new Lane("lane_mid", null, null);
        Lane laneWork = new Lane("lane_work", null, work);

        Car car = new Car("car_1", laneHome, 50.0, home, work);

        Route route = new Route("route_car_1");
        route.addLane(laneMid);
        route.addLane(laneWork);

        System.out.println("[car_1] [residence]: home_1");
        System.out.println("[car_1] [workplace]: work_1");

        car.setCurrentRoute(route);
        System.out.println("[car_1] [currentRoute]: null -> route_car_1");

        car.changeLane(laneMid);
        System.out.println("[car_1] [currentLane]: lane_home -> lane_mid");

        car.changeLane(laneWork);
        System.out.println("[car_1] [currentLane]: lane_mid -> lane_work");

        System.out.println("[car_1] [location]: úton -> work_1");
        System.out.println("[log] car_1 megérkezett a munkahelyre");
    }
}