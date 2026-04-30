package tests;

import src.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Teszteset 18: Jég felolvasztása sószórással.
 * SaltSpreaderHead.clean: 1 só fogyasztása + Lane.change(amount),
 * az IceSheet -> Clear átmenet kiváltása.
 */
public class test18 implements TestCase {

    @Override
    public void run() {
        Lane lane_start = new Lane("lane_start", null, null);
        Snowplow plow_1 = new Snowplow("plow_1", lane_start, 0, new SweeperHead());
        plow_1.setSaltStock(10);

        Lane lane_ice2 = new Lane("lane_ice2", null, null);
        lane_ice2.setState(new IceSheet());
        lane_ice2.setIceThickness(5.0);

        CleanerRole cleaner_1 = new CleanerRole("cleaner_1", 100, plow_1);

        Map<String, Lane> lanes = new HashMap<>();
        lanes.put("lane_start", lane_start);
        lanes.put("lane_ice2", lane_ice2);

        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test18_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, plow_1, cleaner_1, lanes);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }
    }
}
