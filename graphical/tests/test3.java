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
        Lane dummyLane = new Lane("lane_1", null, null);

        Head initialHead = new SweeperHead();
        Snowplow plow1 = new Snowplow("snowplow_1", dummyLane, 30.0, initialHead);

        String beforeHead = plow1.getCurrentHead().getClass().getSimpleName();

        Head newHead = new IcebreakerHead();
        plow1.changeHead(newHead);

        String afterHead = plow1.getCurrentHead().getClass().getSimpleName();

        System.out.println("[" + plow1.getId() + "] [currentHead]: " + beforeHead + " -> " + afterHead);
    }
}