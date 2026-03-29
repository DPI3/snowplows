package skeleton.tests;

import skeleton.src.*;

/**
 * 10. teszteset: Takarítás sószóró fejjel teszt.
 *
 * A teszteset ellenőrzi, hogy a hókotró sószóró fejjel
 * megfelelően takarítja az utat.
 */
public class Test10 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     *
     * @param scanner a felhasználói bemenet olvasására szolgáló objektum
     */
    @Override
    public void run() {

        Skeleton.printCall("Test10", "run()");

        CleanerRole cleanerRole = new CleanerRole();
        Lane lane = new Lane();
        SaltSpreaderHead saltSpreaderHead = new SaltSpreaderHead();
        Snowplow snowplow = new Snowplow();

        // 1. Előfeltétel: a hókotró aktuális feje a sószóró fej
        snowplow.changeHead(saltSpreaderHead);

        // 2. A takarító a hókotrót a tisztítandó sávra irányítja
        cleanerRole.controlSnowplow(snowplow);

        Skeleton.printReturn("");
    }
}