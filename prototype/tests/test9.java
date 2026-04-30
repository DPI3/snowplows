package tests;

import src.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Teszteset 9: Ütközések kezelése automatikus és játékos által vezérelt járművek között.
 * Valódi domain logikával: mozgas vehicle lane (kollízió-érzékelés).
 */
public class test9 implements TestCase {

    @Override
    public void run() {
        TestContext ctx = new TestContext();

        // === Sávok ===
        Lane lane_conflict_1 = new Lane("lane_conflict_1", null, null);
        Lane lane_conflict_2 = new Lane("lane_conflict_2", null, null);
        Lane lane_car2_start = new Lane("lane_car2_start", null, null);
        Lane lane_bus1_start = new Lane("lane_bus1_start", null, null);
        ctx.lanes.put("lane_conflict_1", lane_conflict_1);
        ctx.lanes.put("lane_conflict_2", lane_conflict_2);
        ctx.lanes.put("lane_car2_start", lane_car2_start);
        ctx.lanes.put("lane_bus1_start", lane_bus1_start);

        // Csomópontok (dummy)
        Residence res = new Residence("res");
        Workplace  wrk = new Workplace("wrk");

        // === car_1: már lane_conflict_1-en van (arrange) ===
        Car car_1 = new Car("car_1", lane_conflict_1, 50.0, res, wrk);
        ctx.cars.put("car_1", car_1);
        ctx.setVehicleLane("car_1", "lane_conflict_1");

        // === car_2: egy másik sávon indul ===
        Car car_2 = new Car("car_2", lane_car2_start, 50.0, res, wrk);
        ctx.cars.put("car_2", car_2);
        ctx.setVehicleLane("car_2", "lane_car2_start");

        // === Dummy NPC a lane_conflict_2-n (speed=0, már megállt) ===
        Car car_3 = new Car("car_3", lane_conflict_2, 0.0, res, wrk);
        ctx.cars.put("car_3", car_3);
        ctx.setVehicleLane("car_3", "lane_conflict_2");

        // === bus_1: játékos busz ===
        Bus bus_1 = new Bus("bus_1", lane_bus1_start, 40.0, null, null);
        ctx.buses.put("bus_1", bus_1);
        ctx.setVehicleLane("bus_1", "lane_bus1_start");
        ctx.playerBuses.add("bus_1");

        // === Buszvezető ===
        BusdriverRole busdriver_1 = new BusdriverRole("busdriver_1", bus_1, 0, 100);
        ctx.busdrivers.put("busdriver_1", busdriver_1);
        ctx.busToDriver.put("bus_1", "busdriver_1");

        // === Parancsok futtatása ===
        try {
            List<String> lines = Files.readAllLines(
                    Paths.get("test_data/input/test9_in.txt"));
            for (String line : lines) {
                TestSupport.dispatch(line, ctx);
            }
        } catch (IOException e) {
            System.err.println("[IO hiba] " + e.getMessage());
        }
    }
}
