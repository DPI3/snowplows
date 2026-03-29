package skeleton.tests;

import java.util.ArrayList;
import java.util.Scanner;
import skeleton.src.*;

/**
 * 9. teszteset: Takarítás jégtörő fejjel teszt.
 *
 * A teszteset ellenőrzi, hogy a hókotró jégtörő fejjel megfelelően takarítja az utat.
 *
 * Forgatókönyv:
 * 1. Előfeltétel: a hókotró aktuális feje a jégtörő fej
 * 2. A CleanerRole a hókotrót a takarítani kívánt útszakaszra irányítja
 * 3. Döntés: az útszakasz havas, jégpáncélos vagy feltört jeges
 * 4. Ha az útszakasz havas vagy feltört jeges, a jégtörő fej nem tudja eltakarítani
 * 5. Ha az útszakasz jégpáncélos, a jégtörő fej feltöri a jeget
 * 6. A jégtörő fej feltört jeges útszakaszt hagy maga után
 */
public class Test9 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     *
     * @param scanner a felhasználói bemenet olvasására szolgáló objektum
     */
    @Override
    public void run() {
        Skeleton.printCall("Test9", "run(scanner)");

        Game game = new Game(0, 10, new ArrayList<>(), new ArrayList<>());
        CleanerRole cleanerRole = new CleanerRole();
        Lane lane = new Lane();
        IcebreakerHead iceBreakerHead = new IcebreakerHead();
        Snowplow snowplow = new Snowplow();

        // 1. Előfeltétel: a hókotró aktuális feje a jégtörő fej
        snowplow.changeHead(iceBreakerHead);

        // 2. A CleanerRole a hókotrót a takarítani kívánt útszakaszra irányítja
        cleanerRole.controlSnowplow(snowplow);

        // 3-6. A hókotró megpróbálja megtisztítani az útszakaszt
        // A konkrét viselkedés:
        // - havas vagy feltört jeges útszakasz esetén nincs sikeres takarítás
        // - jégpáncél esetén a fej feltöri a jeget
        // - a visszamaradó állapot feltört jég
        snowplow.clean(lane);

        // Opcionális léptetés
        game.tick();

        Skeleton.printReturn("");
    }
}