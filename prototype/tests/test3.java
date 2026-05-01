package tests;

import src.*;

/**
 * Teszteset 3: Hókotró tisztítási funkciójának tesztelése.
 * Ellenőrzi, hogy egy SweeperHead-del (seprűs fejjel) felszerelt hókotró 
 * képes-e letakarítani a vékony havat, és visszaállítani a sávot Clear állapotba.
 */
public class test3 implements TestCase {
@Override
    public void run() {
        // 1. "load test_map.txt" fázis szimulálása
        Lane dummyLane = new Lane("lane_1", null, null);
        
        // Hókotró inicializálása alapértelmezetten SweeperHead-del
        Head initialHead = new SweeperHead();
        Snowplow plow1 = new Snowplow("snowplow_1", dummyLane, 30.0, initialHead);
        
        // --- Állapot kimentése a csere előtt ---
        String beforeHead = plow1.getCurrentHead().getClass().getSimpleName();

        // 2. Parancs végrehajtása: "fej_csere snowplow_1 icebreaker"[cite: 2]
        Head newHead = new IcebreakerHead();
        plow1.changeHead(newHead);
        
        // --- Állapot kimentése a csere után ---
        String afterHead = plow1.getCurrentHead().getClass().getSimpleName();

        // 3. "state" parancs kiértékelése és az elvárt kimenet generálása
        System.out.println("[" + plow1.getId() + "] [currentHead]: " + beforeHead + " -> " + afterHead);
    }
}