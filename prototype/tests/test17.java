package tests;

import src.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Teszteset 17: Jégpáncél feltörése jégtörővel.
 * Az IcebreakerHead.clean polimorf hívás eredménye:
 * IceSheet -> BrokenIce, jégvastagság változatlan, fizetés nincs.
 */
public class test17 implements TestCase {

    @Override
    public void run() {
        Lane lane_start = new Lane("lane_start", null, null);
        Snowplow plow_1 = new Snowplow("plow_1", lane_start, 0, new SweeperHead());

        Lane lane_ice = new Lane("lane_ice", null, null);
        lane_ice.setState(new IceSheet());
        lane_ice.setIceThickness(5.0);

        CleanerRole cleaner_1 = new CleanerRole("cleaner_1", 100, plow_1);

        Map<String, Lane> lanes = new HashMap<>();
        lanes.put("lane_start", lane_start);
        lanes.put("lane_ice", lane_ice);

        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test17_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, plow_1, cleaner_1, lanes);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }
    }
}
