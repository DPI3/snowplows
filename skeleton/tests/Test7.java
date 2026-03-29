package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * 7. teszteset: Takarítás sörpő fejjel teszt
 * A teszteset ellenőrzi, hogy a hókotró söprő fejjel megfelelően takarítja az utat.
 */
public class Test7 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Inicializálja a takarítót, a hókotrót a söprő fejjel, valamint egy útsávot.
     */
    @Override
    public void run() {
        // Inicializálás
        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();
        Lane targetLane = new Lane();
        snowplow.changeLane(targetLane);
        
        SweeperHead sweeperHead = new SweeperHead();

        // Előfeltétel: A hókotró feje söprő fej
        snowplow.changeHead(sweeperHead);

        // A takarítási folyamat indítása a szekvenciadiagramnak megfelelően
        cleanerRole.controlSnowplow(snowplow, targetLane);
    }
}