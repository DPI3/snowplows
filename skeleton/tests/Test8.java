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
        Snowplow snowplow = new Snowplow();
        Lane targetLane = new Lane();
        snowplow.changeLane(targetLane);

        // Előfeltétel: A hókotró feje hányó fej
        ThrowerHead throwerHead = new ThrowerHead();

        // A takarítási folyamat indítása a szekvenciadiagramnak megfelelően
        snowplow.changeHead(throwerHead);

        // A takarítási folyamat indítása a szekvenciadiagramnak megfelelően
        cleanerRole.controlSnowplow(snowplow);

        Skeleton.printReturn("");
    }
}