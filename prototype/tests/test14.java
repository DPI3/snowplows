package tests;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import src.*;


public class test14 implements TestCase{
     @Override
    public void run() {
        Lane lane_1=new Lane("lane_start", null, null);
        Snowplow plow_1= new Snowplow("plow_1",lane_1, 0, new SweeperHead());
        Lane lane_5=new Lane("lane_ice2", null, null);
        lane_5.setState(new IceSheet());
        lane_5.setGravelThickness(0);
        plow_1.setGravelStock(10);
        
        CleanerRole cleaner_1 = new CleanerRole("cleaner_1", 100, plow_1);

        Map<String, Lane> lanes = new HashMap<>();
        lanes.put("lane_start", lane_1);
        lanes.put("lane_ice2", lane_5);

        // 2. Bemenet beolvasása és a parancsok feldolgozása
        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test14_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, plow_1, cleaner_1, lanes);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }

    }
}
