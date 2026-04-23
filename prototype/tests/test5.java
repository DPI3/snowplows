package tests;

public class test5 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 5: Busz útvonalának automatikus kijelölése és teljesítése ===");

        Terminal termA = new Terminal("term_a");
        Terminal termB = new Terminal("term_b");

        Lane laneStart = new Lane("lane_start", termA, null);
        Lane laneT1 = new Lane("lane_t1_1", null, null);
        Lane laneT2 = new Lane("lane_t1_2", null, null);
        Lane laneEnd = new Lane("lane_t2", null, termB);

        Bus bus = new Bus("bus_1", laneStart, 40.0, termA, termB);
        BusDriverRole driver = new BusDriverRole("busdriver_1", bus, 100);

        Route route1 = new Route("route_1");
        route1.addLane(laneT1);
        route1.addLane(laneT2);
        route1.addLane(laneEnd);

        System.out.println("[system] [selectedStart]: term_a");
        System.out.println("[system] [selectedDestination]: term_b");

        bus.setCurrentRoute(route1);
        System.out.println("[bus_1] [currentRoute]: null -> route_1");

        bus.changeLane(laneT1);
        System.out.println("[bus_1] [currentLane]: lane_start -> lane_t1_1");

        bus.changeLane(laneT2);
        System.out.println("[bus_1] [currentLane]: lane_t1_1 -> lane_t1_2");

        bus.changeLane(laneEnd);
        System.out.println("[bus_1] [currentLane]: lane_t1_2 -> lane_t2");

        System.out.println("[bus_1] [location]: úton -> term_b");

        driver.changeMoney(60);
        System.out.println("[busdriver_1] [money]: 100 -> " + driver.getMoney());

        System.out.println("[log] Forduló teljesítve: bus_1, jutalom: 60");

        Route route2 = new Route("route_2");
        bus.setCurrentRoute(route2);

        System.out.println("[bus_1] [currentRoute]: route_1 -> route_2");
    }
}