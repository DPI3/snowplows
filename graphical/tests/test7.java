package tests;

import src.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

/**
 * Teszteset 7: Autó automatikus útvonal-haladása (tick).
 * Valódi domain logikával: Car auto-route, tick, munkahely-megérkezés.
 */
public class test7 implements TestCase {

    @Override
    public void run() {
        TestContext ctx = new TestContext();

        Lane lane_home = new Lane("lane_home", null, null);
        Lane lane_mid  = new Lane("lane_mid",  null, null);
        Lane lane_work = new Lane("lane_work", null, null);
        ctx.lanes.put("lane_home", lane_home);
        ctx.lanes.put("lane_mid",  lane_mid);
        ctx.lanes.put("lane_work", lane_work);

        Residence home_1 = new Residence("home_1");
        Workplace work_1 = new Workplace("work_1");

        Car car_1 = new Car("car_1", lane_home, 50.0, home_1, work_1);
        ctx.cars.put("car_1", car_1);
        ctx.setVehicleLane("car_1", "lane_home");
        ctx.defaultSpeed.put("car_1", 50.0);
        ctx.vehicleRoute.put("car_1", null);

        ctx.carAutoRoute.put("car_1", "route_car_1");
        ctx.carResidence.put("car_1", "home_1");
        ctx.carWorkplace.put("car_1", "work_1");

        ctx.routeLanes.put("route_car_1",
                Arrays.asList("lane_home", "lane_mid", "lane_work"));

        ctx.carArrivalLane.put("lane_work", "work_1");

        try {
            List<String> lines = Files.readAllLines(
                    Paths.get("test_data/input/test7_in.txt"));
            for (String line : lines) {
                TestSupport.dispatch(line, ctx);
            }
        } catch (IOException e) {
            System.err.println("[IO hiba] " + e.getMessage());
        }
    }
}
