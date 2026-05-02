package tests;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import src.*;

/**
 * Teszteset 13: Sáv állapotának lekérdezése.
 * A szimuláció célja a  kiválasztott Lane objektum attribútumainak pontos lekérdezése.
 * A sáv áthaladhatóságának (isPassable()) és dinamikus súlyának (getDynamicWeight()) helyes 
 * kiszámítása az aktuális állapot alapján.
 */
public class test13  implements TestCase {
     @Override
    public void run() {
       Lane lane_1 = new Lane("lane_1", null, null);
       lane_1.setSnowThickness(3);
       lane_1.setIceThickness(0);
       lane_1.setGravelThickness(0);
       lane_1.setState(new ThinSnow());

       Snowplow plow_1 = new Snowplow("plow_1", lane_1, 0, null);
        CleanerRole cleaner_1 = new CleanerRole("cleaner_1", 100, plow_1);

        Map<String, Lane> lanes = new HashMap<>();
        lanes.put("lane_1", lane_1);

         // 2. Bemenet beolvasása és a parancsok feldolgozása
        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test13_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, plow_1, cleaner_1, lanes);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }
    }
}
