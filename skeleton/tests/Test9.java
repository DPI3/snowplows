package skeleton.tests;

import skeleton.src.*;

/**
 * 9. teszteset: Takarítás jégtörő fejjel teszt
 * A teszteset ellenőrzi, hogy a hókotró jégtörő fejjel megfelelően takarítja az utat.
 */
public class Test9 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Inicializálja a takarítót, a hókotrót a jégtörő fejjel, valamint egy útsávot.
     */
    @Override
    public void run() {
        Skeleton.printCall("Test9", "run()");

        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();
        Lane targetLane = new Lane();
        snowplow.changeLane(targetLane);

        IcebreakerHead icebreakerHead = new IcebreakerHead();

        // Előfeltétel: A hókotró feje jégtörő fej
        snowplow.changeHead(icebreakerHead);

        // A takarítási folyamat indítása
        cleanerRole.controlSnowplow(snowplow);

        Skeleton.printReturn("");
    }
}