package tests;

import src.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Teszteset 4: Vásárlás a boltban.
 * bolt_nyit → selectedItem beállítás → só vásárlás → pénz és készlet változás.
 * TestSupport 5-paraméteres dispatch-et használ (Store-ral).
 */
public class test4 implements TestCase {

    @Override
    public void run() {
        // Kezdőállapot ("load test4_arrange.txt" hatása)
        Lane lane_1           = new Lane("lane_1", null, null);
        Snowplow plow_1       = new Snowplow("plow_1", lane_1, 0, new SaltSpreaderHead());
        CleanerRole cleaner_1 = new CleanerRole("cleaner_1", 100, plow_1);
        Store store           = new Store(new ArrayList<>());

        Map<String, Lane> lanes = new HashMap<>();
        lanes.put("lane_1", lane_1);

        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test4_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, plow_1, cleaner_1, lanes, store);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }
    }
}
