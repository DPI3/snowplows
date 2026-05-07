package tests;

import src.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Teszteset 19: Hó és jég leolvasztása sárkányfejjel.
 * DragonHead.clean: 1 biokerozin fogyasztása + Lane.change(nagy érték),
 * a DeepSnow -> Clear átmenet kiváltása.
 */
public class test19 implements TestCase {

    @Override
    public void run() {
        Lane lane_start = new Lane("lane_start", null, null);
        Snowplow plow_1 = new Snowplow("plow_1", lane_start, 0, new SweeperHead());
        plow_1.setBiokeroseneStock(5);

        Lane lane_mixed = new Lane("lane_mixed", null, null);
        lane_mixed.setState(new DeepSnow());
        lane_mixed.setSnowThickness(8.0);

        CleanerRole cleaner_1 = new CleanerRole("cleaner_1", 150, plow_1);

        Map<String, Lane> lanes = new HashMap<>();
        lanes.put("lane_start", lane_start);
        lanes.put("lane_mixed", lane_mixed);

        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test19_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, plow_1, cleaner_1, lanes);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }
    }
}
