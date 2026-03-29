package skeleton.tests;

import java.util.Scanner;
import skeleton.src.*;

/**
 * 8. teszteset: Takarítás hányó fejjel teszt.
 *
 * A teszteset ellenőrzi, hogy a hókotró hányó fejjel megfelelően takarítja az utat.
 * A CleanerRole a hókotrót a takarítani kívánt útszakaszra irányítja, majd a hókotró
 * a hányó fej segítségével megpróbálja letakarítani az adott útszakaszt.
 *
 * A teszt során az alábbi esetek vizsgálhatók:
 * - havas útszakasz
 * - jégpáncélos útszakasz
 * - feltört jeges útszakasz
 *
 * Elvárt működés:
 * - jégpáncél esetén a hányó fej nem tud takarítani,
 * - hó vagy feltört jég esetén az út megtisztul,
 * - a CleanerRole jutalmat kap a sikeres takarításért.
 */
public class Test8 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     *
     * A teszt létrehozza a szükséges objektumokat, majd végrehajtja a
     * hányó fejjel történő takarítás folyamatát.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához
     */
    @Override
    public void run() {
        Skeleton.printCall("Test8", "run(scanner)");

        Game game = new Game();
        CleanerRole cleanerRole = new CleanerRole();
        Lane lane = new Lane();
        ThrowerHead throwerHead = new ThrowerHead();
        Snowplow snowplow = new Snowplow();

        // 1. A hókotróra hányó fej kerül
        snowplow.changeHead(throwerHead);

        // 2. A takarító irányítja a hókotrót
        cleanerRole.controlSnowplow(snowplow);

        // 3. A hókotró megpróbálja letakarítani az útszakaszt
        snowplow.clean(lane);

        // 4. Egy szimulációs lépés végrehajtása opcionálisan
        game.tick();

        Skeleton.printReturn("");
    }
}