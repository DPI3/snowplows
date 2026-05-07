package tests;

import src.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Teszteset 5: Busz útvonalának automatikus kijelölése és teljesítése.
 * Valódi domain logikával: BusdriverRole.money, Bus.location, terminál-érzékelés.
 */
public class test5 implements TestCase {

    @Override
    public void run() {
        TestContext ctx = new TestContext();

        Lane lane_start = new Lane("lane_start", null, null);
        Lane lane_t1_1  = new Lane("lane_t1_1",  null, null);
        Lane lane_t1_2  = new Lane("lane_t1_2",  null, null);
        Lane lane_t2    = new Lane("lane_t2",     null, null);
        ctx.lanes.put("lane_start", lane_start);
        ctx.lanes.put("lane_t1_1",  lane_t1_1);
        ctx.lanes.put("lane_t1_2",  lane_t1_2);
        ctx.lanes.put("lane_t2",    lane_t2);

        Bus bus_1 = new Bus("bus_1", lane_start, 40.0, null, null);
        ctx.buses.put("bus_1", bus_1);
        ctx.setVehicleLane("bus_1", "lane_start");
        ctx.defaultSpeed.put("bus_1", 40.0);
        ctx.vehicleRoute.put("bus_1", null);

        BusdriverRole busdriver_1 = new BusdriverRole("busdriver_1", bus_1, 100);
        ctx.busdrivers.put("busdriver_1", busdriver_1);
        ctx.busToDriver.put("bus_1", "busdriver_1");

        ctx.routeLanes.put("route_1", Arrays.asList("lane_t1_1", "lane_t1_2", "lane_t2"));
        ctx.routeLanes.put("route_2", Arrays.asList("lane_t2",   "lane_t1_2", "lane_t1_1"));

        ctx.busAutoStart.put("bus_1", "term_a");
        ctx.busAutoDest.put("bus_1",  "term_b");
        ctx.busAutoRoute.put("bus_1", "route_1");

        ctx.terminalLane.put("lane_t2", "term_b");
        ctx.busArrivalReward.put("bus_1", 60);
        ctx.busArrivalShowReward.add("bus_1");
        ctx.busNextRoute.put("bus_1", "route_2");

        try {
            List<String> lines = Files.readAllLines(
                    Paths.get("test_data/input/test5_in.txt"));
            for (String line : lines) {
                TestSupport.dispatch(line, ctx);
            }
        } catch (IOException e) {
            System.err.println("[IO hiba] " + e.getMessage());
        }
    }
}
