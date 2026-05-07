package tests;

import src.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Teszteset 16: Vastag hóréteg eltakarítása ThrowerHead-del.
 * A teszt a {@code test_data/input/test16_in.txt} parancsfájlból olvassa
 * a műveleteket, valódi domain-objektumokon hajtja végre őket, és csak
 * az állapot-átmeneteket írja ki — formátuma az assert fájllal egyezik.
 */
public class test16 implements TestCase {

    @Override
    public void run() {
        Lane lane_1 = new Lane("lane_1", null, null);
        Snowplow plow_1 = new Snowplow("plow_1", lane_1, 0, new SweeperHead());

        Lane lane_5 = new Lane("lane_5", null, null);
        lane_5.setState(new DeepSnow());
        lane_5.setSnowThickness(5.0);

        CleanerRole cleaner_1 = new CleanerRole("cleaner_1", 100, plow_1);

        Map<String, Lane> lanes = new HashMap<>();
        lanes.put("lane_1", lane_1);
        lanes.put("lane_5", lane_5);

        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test16_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, plow_1, cleaner_1, lanes);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }
    }
}
