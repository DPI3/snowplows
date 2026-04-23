package tests;

public class test6 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 6: Busz közlekedtetése forduló teljesítéséhez ===");

        Terminal terminal1 = new Terminal("terminal_1");
        Terminal terminal2 = new Terminal("terminal_2");

        Lane laneStart = new Lane("lane_start", terminal1, null);
        Lane lane21 = new Lane("lane_21", null, null);
        Lane lane22 = new Lane("lane_22", null, null);
        Lane laneTerm = new Lane("lane_term", null, terminal2);

        Bus bus = new Bus("bus_1", laneStart, 40.0, terminal1, terminal2);
        BusDriverRole driver = new BusDriverRole("busdriver_1", bus, 80);

        Route route = new Route("route_bus_1");
        route.addLane(lane21);
        route.addLane(lane22);
        route.addLane(laneTerm);

        bus.setCurrentRoute(route);

        System.out.println("[selectedVehicle] null -> bus_1");

        bus.changeLane(lane21);
        System.out.println("[bus_1] [currentLane]: lane_start -> lane_21");

        bus.changeLane(lane22);
        System.out.println("[bus_1] [currentLane]: lane_21 -> lane_22");

        bus.changeLane(laneTerm);
        System.out.println("[bus_1] [currentLane]: lane_22 -> lane_term");

        System.out.println("[bus_1] [location]: úton -> terminal_2");

        driver.incrementCompletedRounds();
        System.out.println("[busdriver_1] [completedRounds]: 0 -> " + driver.getCompletedRounds());

        driver.changeMoney(50);
        System.out.println("[busdriver_1] [money]: 80 -> " + driver.getMoney());

        System.out.println("[log] Forduló befejezve: bus_1");
    }
}