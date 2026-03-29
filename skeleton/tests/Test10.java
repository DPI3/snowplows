package skeleton.tests;

import java.util.ArrayList;
import java.util.Scanner;
import skeleton.src.*;

/**
 * 10. teszteset: Takarítás sószóró fejjel teszt.
 *
 * A teszteset ellenőrzi, hogy a hókotró sószóró fejjel megfelelően takarítja az utat.
 *
 * Forgatókönyv:
 * 1. Előfeltétel: a hókotró aktuális feje a sószóró fej
 * 2. A CleanerRole a hókotrót a takarítani kívánt útszakaszra irányítja
 * 3. Döntés: van-e megfelelő mennyiségű só a hókotróban
 * 4. Ha nincs elég só, a sószóró fej hatástalanná válik
 * 5. Ha van elég só, a sószóró fej beszórja sóval az utat
 * 6. A só elolvasztja a havat vagy jeget
 * 7. A CleanerRole megkapja a jutalmat
 * 8. A sószóró fej tiszta útszakaszt hagy maga után
 */
public class Test10 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     *
     * @param scanner a felhasználói bemenet olvasására szolgáló objektum
     */
    @Override
    public void run(Scanner scanner) {
        Skeleton.printCall("Test10", "run(scanner)");

        Game game = new Game(0, 10, new ArrayList<>(), new ArrayList<>());
        CleanerRole cleanerRole = new CleanerRole();
        Lane lane = new Lane();
        SaltSpreaderHead saltSpreaderHead = new SaltSpreaderHead();
        Snowplow snowplow = new Snowplow();

        // 1. Előfeltétel: a hókotró aktuális feje a sószóró fej
        snowplow.changeHead(saltSpreaderHead);

        // 2. A CleanerRole a hókotrót a takarítani kívánt útszakaszra irányítja
        cleanerRole.controlSnowplow(snowplow);

        // 3-8. A hókotró megpróbálja letakarítani az útszakaszt
        // A konkrét viselkedést a SaltSpreaderHead.clean(...) valósítja meg:
        // - ha nincs elég só, nincs változás
        // - ha van elég só, az út megtisztul
        snowplow.clean(lane);

        // opcionális játék-léptetés
        game.tick();

        Skeleton.printReturn("");
    }
}