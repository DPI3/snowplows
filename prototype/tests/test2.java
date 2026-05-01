package tests;

import src.*;

/**
 * Teszteset 2: Jármű sávváltásának és az út járhatóságának tesztelése.
 * Ellenőrzi, hogy a jármű át tud-e menni egy tiszta sávra, 
 * illetve megakadályozza-e a rendszer, hogy járhatatlan (DeepSnow) sávra lépjen.
 */
public class test2 implements TestCase {

   @Override
    public void run() {
        // 1. Objektumok felépítése ("load test_map.txt" szimulálása)
        
        Terminal termA = new Terminal("Terminal_A");
        Lane laneTermA = new Lane("Terminal_A", null, null); 
        Lane lane12 = new Lane("lane_12", termA, null);
        
        // Marad a DeepSnow, ahogy kérted
        lane12.setState(new DeepSnow());
        lane12.setSnowThickness(5.0);

        // Hókotró (üzemanyaggal és állapottal) és a takarító
        Snowplow plow1 = new Snowplow("snowplow_1", laneTermA, 30.0, new ThrowerHead());
        CleanerRole cleaner = new CleanerRole("player_cleaner", 100, plow1);

        // --- TAKARÍTÁS ELŐTTI ÁLLAPOTOK KIMENTÉSE ---
        String beforeLane = plow1.getCurrentLane().getName();
        String beforeState = plow1.getState().name();
        int beforeFuel = plow1.getFuel();
        
        double beforeSnow = lane12.getSnowThickness();
        boolean beforePassable = lane12.isPassable();
        String beforeLaneState = lane12.getLaneState().getClass().getSimpleName();
        int beforePoints = cleaner.getScore(); 

        // 2. Parancsok végrehajtása (Mozgás és Takarítás)
        
        // "mozgas snowplow_1 lane_12"
        plow1.setCurrentLane(lane12); 
        
        // "takarit snowplow_1"
        cleaner.controlSnowplow(plow1);
        
        // --- TAKARÍTÁS UTÁNI ÁLLAPOTOK KIMENTÉSE ---
        String afterLane = plow1.getCurrentLane().getName();
        String afterState = plow1.getState().name();
        int afterFuel = plow1.getFuel();

        double afterSnow = lane12.getSnowThickness();
        boolean afterPassable = lane12.isPassable();
        String afterLaneState = lane12.getLaneState().getClass().getSimpleName();
        int afterPoints = cleaner.getScore(); 

        // 3. Elvárt kimenet generálása a lekérdezett valós adatok alapján
        
        System.out.println("[" + plow1.getId() + "] [currentLane]: " + beforeLane + " -> " + afterLane);
        System.out.println("[" + plow1.getId() + "] [state]: " + beforeState + " -> " + afterState); 
        System.out.println("[" + plow1.getId() + "] [fuel]: " + beforeFuel + " -> " + afterFuel); 
        System.out.println();
        
        System.out.println("[" + lane12.getName() + "] [snowThickness]: " + beforeSnow + " -> " + afterSnow);
        System.out.println("[" + lane12.getName() + "] [currentState]: " + beforeLaneState + " -> " + afterLaneState);
        System.out.println("[" + lane12.getName() + "] [isPassable]: " + beforePassable + " -> " + afterPassable);
        System.out.println();
        
        System.out.println("[" + cleaner.getName() + "] [points]: " + beforePoints + " -> " + afterPoints);
    }
}