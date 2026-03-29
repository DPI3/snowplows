package skeleton.tests;

import skeleton.src.*;

/**
 * 11. teszteset: Takarítás sárkány fejjel teszt.
 * A teszteset ellenőrzi, hogy a hókotró sárkány fejjel megfelelően takarítja az utat.
 */
public class Test11 implements TestCase {

    @Override
    public void run() {
        Skeleton.printCall("Test11", "run()");

        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();
        Lane targetLane = new Lane();
        snowplow.changeLane(targetLane);

        DragonHead dragonHead = new DragonHead();

        snowplow.changeHead(dragonHead);

        cleanerRole.controlSnowplow(snowplow);

        Skeleton.printReturn("");
    }
}