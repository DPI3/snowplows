package skeleton.tests;

import skeleton.src.*;

import java.util.Scanner;

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
public class Test8 extends TestCase {

    /**
     * A tesztszekvencia futtatása.
     *
     * A teszt létrehozza a szükséges objektumokat, majd végrehajtja a
     * hányó fejjel történő takarítás folyamatát.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához
     */
    @Override
    public void run(Scanner scanner) {
        Game game = new Game();
        game.start();

        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();

        // A modell alapján itt azt feltételezzük, hogy a Snowplow alapból
        // rendelkezik ThrowerHead-del, vagy a konstruktorában létrejön.
        //
        // Az alábbi metódushívásokat a saját osztályaitok tényleges neveihez kell igazítani.
        // A logika a use-case alapján van felépítve:

        // 1. A takarító a hókotrót a megfelelő útszakaszra irányítja
        cleanerRole.cleanRoad(snowplow);

        // 2. A hókotró megpróbálja letakarítani az útszakaszt hányó fejjel
        snowplow.cleanWithThrowerHead();

        // 3. A CleanerRole jutalma nő
        cleanerRole.increaseMoney();
    }
}