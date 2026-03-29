package skeleton.tests;

import java.util.ArrayList;
import skeleton.src.*;

/**
 * 11. teszteset: Takarítás sárkány fejjel teszt.
 *
 * A teszteset ellenőrzi, hogy a hókotró sárkány fejjel
 * megfelelően takarítja az utat.
 *
 * Forgatókönyv:
 * 1. Előfeltétel: a hókotró aktuális feje a sárkány fej
 * 2. A CleanerRole a hókotrót a takarítani kívánt útszakaszra irányítja
 * 3. Döntés: van-e megfelelő mennyiségű biokerozin a hókotróban
 * 4. Ha nincs elég biokerozin, a sárkány fej hatástalan
 * 5. Ha van elég biokerozin, a sárkány fej elolvasztja a havat vagy jeget
 * 6. A CleanerRole megkapja a jutalmat
 * 7. A sárkány fej tiszta útszakaszt hagy maga után
 */
public class Test11 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     *
     * @param scanner a felhasználói bemenet olvasására szolgáló objektum
     */
    @Override
    public void run() {

        Skeleton.printCall("Test11", "run(scanner)");

        Game game = new Game(0, 10, new ArrayList<>(), new ArrayList<>());
        CleanerRole cleanerRole = new CleanerRole();
        Lane lane = new Lane();
        DragonHead dragonHead = new DragonHead();
        Snowplow snowplow = new Snowplow();

        // 1. Előfeltétel: a hókotró aktuális feje a sárkány fej
        snowplow.changeHead(dragonHead);

        // 2. A CleanerRole a hókotrót a takarítani kívánt útszakaszra irányítja
        cleanerRole.controlSnowplow(snowplow, lane);

        // 3-7. A hókotró megpróbálja letakarítani az útszakaszt
        // A konkrét logika a DragonHead.clean(...) metódusban van:
        // - ha nincs elég biokerozin, nincs változás
        // - ha van elég biokerozin, az út megtisztul
        snowplow.clean(lane);

        // opcionális léptetés
        game.tick();

        Skeleton.printReturn("");

    }
}