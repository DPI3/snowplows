package skeleton.tests;

import skeleton.src.*;

/**
 * 10. teszteset: Takarítás sószóró fejjel teszt.
 * A teszteset ellenőrzi, hogy a hókotró sószóró fejjel megfelelően takarítja az utat.
 */
public class Test10 implements TestCase {

    @Override
    public void run() {
        Skeleton.printCall("Test10", "run()");

        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();
        Lane targetLane = new Lane();
        snowplow.changeLane(targetLane);

        SaltSpreaderHead saltSpreaderHead = new SaltSpreaderHead();

        snowplow.changeHead(saltSpreaderHead);

        cleanerRole.controlSnowplow(snowplow);

        Skeleton.printReturn("");
    }
}