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
 * Teszteset 11: Fej hatástalanná válása készlet hiányában.
 * 1. takarit: SaltSpreader 0 só → figyelmeztető Console üzenet
 * 2. vasarol: só-csomag vásárlás (50 Ft → 10 egység)
 * 3. takarit: sikeres tisztítás, IceSheet → Clear Console üzenet
 * TestSupport 5-paraméteres dispatch-et használ (Store-ral, közvetlen clean = nincs pénzjutalom).
 */
public class test11 implements TestCase {

    @Override
    public void run() {
        // Kezdőállapot ("load test11_arrange.txt" hatása)
        Lane lane_ice_1       = new Lane("lane_ice_1", null, null);
        lane_ice_1.setState(new IceSheet());
        // iceThickness szándékosan 0 marad → change(5) elvégzi a Clear-re váltást
        // anélkül, hogy iceThickness változást kelljen kiírni

        Snowplow plow_1       = new Snowplow("plow_1", lane_ice_1, 0, new SaltSpreaderHead());
        // saltStock = 0 (alapértelmezett) → első takarit sikertelen

        CleanerRole cleaner_1 = new CleanerRole("cleaner_1", 200, plow_1);
        Store store           = new Store(new ArrayList<>());

        Map<String, Lane> lanes = new HashMap<>();
        lanes.put("lane_ice_1", lane_ice_1);

        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test11_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, plow_1, cleaner_1, lanes, store);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }
    }
}
