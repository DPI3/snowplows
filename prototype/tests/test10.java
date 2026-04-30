package tests;

import src.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Teszteset 10: Autó és busz elakadása mély hóban, majd kiszabadítás.
 * Valódi domain logikával: allapot_allit, mozgas (elakadás), takarit, mozgas (kiszabadítás).
 */
public class test10 implements TestCase {

    @Override
    public void run() {
        TestContext ctx = new TestContext();

        // === Sávok ===
        Lane lane_snow_car  = new Lane("lane_snow_car",  null, null);
        Lane lane_snow_bus  = new Lane("lane_snow_bus",  null, null);
        Lane lane_next_car  = new Lane("lane_next_car",  null, null);
        Lane lane_next_bus  = new Lane("lane_next_bus",  null, null);
        Lane lane_car_start = new Lane("lane_car_start", null, null);
        Lane lane_bus_start = new Lane("lane_bus_start", null, null);
        ctx.lanes.put("lane_snow_car",  lane_snow_car);
        ctx.lanes.put("lane_snow_bus",  lane_snow_bus);
        ctx.lanes.put("lane_next_car",  lane_next_car);
        ctx.lanes.put("lane_next_bus",  lane_next_bus);
        ctx.lanes.put("lane_car_start", lane_car_start);
        ctx.lanes.put("lane_bus_start", lane_bus_start);

        // Csomópontok (dummy)
        Residence res = new Residence("res");
        Workplace  wrk = new Workplace("wrk");

        // === Autó: lane_car_start-ról indul ===
        Car car_1 = new Car("car_1", lane_car_start, 50.0, res, wrk);
        ctx.cars.put("car_1", car_1);
        ctx.setVehicleLane("car_1", "lane_car_start");
        ctx.defaultSpeed.put("car_1", 50.0);

        // Autó útvonala (az elakadás után: lane_snow_car → lane_next_car)
        ctx.routeLanes.put("car_1_route",
                Arrays.asList("lane_car_start", "lane_snow_car", "lane_next_car"));
        ctx.vehicleRoute.put("car_1", "car_1_route");

        // === Busz: lane_bus_start-ról indul ===
        Bus bus_1 = new Bus("bus_1", lane_bus_start, 40.0, null, null);
        ctx.buses.put("bus_1", bus_1);
        ctx.setVehicleLane("bus_1", "lane_bus_start");
        ctx.defaultSpeed.put("bus_1", 40.0);

        // Busz útvonala (az elakadás után: lane_snow_bus → lane_next_bus)
        ctx.routeLanes.put("bus_1_route",
                Arrays.asList("lane_bus_start", "lane_snow_bus", "lane_next_bus"));
        ctx.vehicleRoute.put("bus_1", "bus_1_route");

        // === Hókotró (ThrowerHead, képes DeepSnow-t eltávolítani) ===
        Snowplow plow_1 = new Snowplow("plow_1", null, 0, new ThrowerHead());
        ctx.plows.put("plow_1", plow_1);

        // === Parancsok futtatása ===
        try {
            List<String> lines = Files.readAllLines(
                    Paths.get("test_data/input/test10_in.txt"));
            for (String line : lines) {
                TestSupport.dispatch(line, ctx);
            }
        } catch (IOException e) {
            System.err.println("[IO hiba] " + e.getMessage());
        }
    }
}
