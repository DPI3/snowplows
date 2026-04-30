package tests;

import src.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Teszteset 6: Busz közlekedtetése forduló teljesítéséhez.
 * Valódi domain logikával: kijelol, mozgas, terminál-érzékelés, completedRounds++, money.
 */
public class test6 implements TestCase {

    @Override
    public void run() {
        TestContext ctx = new TestContext();

        // === Sávok ===
        Lane lane_start = new Lane("lane_start", null, null);
        Lane lane_21    = new Lane("lane_21",    null, null);
        Lane lane_22    = new Lane("lane_22",    null, null);
        Lane lane_term  = new Lane("lane_term",  null, null);
        ctx.lanes.put("lane_start", lane_start);
        ctx.lanes.put("lane_21",    lane_21);
        ctx.lanes.put("lane_22",    lane_22);
        ctx.lanes.put("lane_term",  lane_term);

        // === Busz ===
        Bus bus_1 = new Bus("bus_1", lane_start, 40.0, null, null);
        ctx.buses.put("bus_1", bus_1);
        ctx.setVehicleLane("bus_1", "lane_start");
        ctx.defaultSpeed.put("bus_1", 40.0);
        ctx.vehicleRoute.put("bus_1", null);

        // === Buszvezető ===
        BusdriverRole busdriver_1 = new BusdriverRole("busdriver_1", bus_1, 80);
        ctx.busdrivers.put("busdriver_1", busdriver_1);
        ctx.busToDriver.put("bus_1", "busdriver_1");

        // === Terminál-megérkezés ===
        ctx.terminalLane.put("lane_term", "terminal_2");
        ctx.busArrivalReward.put("bus_1", 50);
        ctx.busArrivalIncrRounds.add("bus_1");   // completedRounds++
        // busArrivalShowReward NEM tartalmazza → "Forduló befejezve: ..."
        // busNextRoute NEM tartalmazza → nincs következő útvonal

        // === Parancsok futtatása ===
        try {
            List<String> lines = Files.readAllLines(
                    Paths.get("test_data/input/test6_in.txt"));
            for (String line : lines) {
                TestSupport.dispatch(line, ctx);
            }
        } catch (IOException e) {
            System.err.println("[IO hiba] " + e.getMessage());
        }
    }
}
