package skeleton.tests;

import skeleton.src.*;

/**
 * 8. teszteset: Takarítás hányó fejjel teszt.
 *
 * A teszteset ellenőrzi, hogy a hókotró hányó fejjel megfelelően takarítja az utat.
 */
public class Test8 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     */
    @Override
    public void run() {

        Skeleton.printCall("Test8", "run()");

        CleanerRole cleanerRole = new CleanerRole();
        Lane lane = new Lane();
        ThrowerHead throwerHead = new ThrowerHead();
        Snowplow snowplow = new Snowplow();

        // 1. Előfeltétel: a hókotró aktuális feje a hányó fej
        snowplow.changeHead(throwerHead);

        // 2. A takarító irányítja a hókotrót a tisztítandó sávra
        cleanerRole.controlSnowplow(snowplow);

        Skeleton.printReturn("");
    }
}