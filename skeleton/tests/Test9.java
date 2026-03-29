package skeleton.tests;

import skeleton.src.*;

/**
 * 9. teszteset: Takarítás jégtörő fejjel teszt.
 *
 * A teszteset ellenőrzi, hogy a hókotró jégtörő fejjel
 * megfelelően takarítja az utat.
 */
public class Test9 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     *
     * @param scanner a felhasználói bemenet olvasására szolgáló objektum
     */
    @Override
    public void run() {

        Skeleton.printCall("Test9", "run()");

        CleanerRole cleanerRole = new CleanerRole();
        Lane lane = new Lane();
        IcebreakerHead iceBreakerHead = new IcebreakerHead();
        Snowplow snowplow = new Snowplow();

        // 1. Előfeltétel: a hókotró aktuális feje a jégtörő fej
        snowplow.changeHead(iceBreakerHead);

        // 2. A takarító a hókotrót a tisztítandó sávra irányítja
        cleanerRole.controlSnowplow(snowplow, lane);

        Skeleton.printReturn("");
    }
}