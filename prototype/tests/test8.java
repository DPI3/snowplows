package tests;

import src.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Teszteset 8: Útvonal újratervezése akadály esetén.
 * Valódi domain logikával: allapot_allit, mozgas car (auto), reroute.
 */
public class test8 implements TestCase {

    @Override
    public void run() {
        TestContext ctx = new TestContext();

        // === Sávok ===
        Lane lane_current = new Lane("lane_current", null, null);
        Lane lane_next    = new Lane("lane_next",    null, null);
        Lane lane_bypass_1 = new Lane("lane_bypass_1", null, null);
        ctx.lanes.put("lane_current",  lane_current);
        ctx.lanes.put("lane_next",     lane_next);
        ctx.lanes.put("lane_bypass_1", lane_bypass_1);

        // === Autó ===
        Car car_1 = new Car("car_1", lane_current, 50.0, null, null);
        ctx.cars.put("car_1", car_1);
        ctx.setVehicleLane("car_1", "lane_current");
        ctx.defaultSpeed.put("car_1", 50.0);

        // === Útvonalak ===
        // route_original: lane_current → lane_next (lane_next lesz blokkolva)
        ctx.routeLanes.put("route_original",
                Arrays.asList("lane_current", "lane_next"));
        // route_recalculated: lane_bypass_1 (egyetlen sáv)
        ctx.routeLanes.put("route_recalculated",
                Arrays.asList("lane_bypass_1"));
        ctx.vehicleRoute.put("car_1", "route_original");

        // === Átirányítás ===
        Map<String, String> reroute = new HashMap<>();
        reroute.put("route_original", "route_recalculated");
        ctx.carReroute.put("car_1", reroute);

        // === Parancsok futtatása ===
        try {
            List<String> lines = Files.readAllLines(
                    Paths.get("test_data/input/test8_in.txt"));
            for (String line : lines) {
                TestSupport.dispatch(line, ctx);
            }
        } catch (IOException e) {
            System.err.println("[IO hiba] " + e.getMessage());
        }
    }
}
