package skeleton.tests;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import skeleton.src.*;

/**
 * 18. teszteset: Nyersanyag kifogyás teszt.
 *
 * A teszt célja bemutatni, hogy nyersanyag-hiány esetén a speciális fej
 * hatástalan, majd a játékos dönthet fejcsere vagy vásárlás mellett.
 */
public class Test18 implements TestCase {

    @Override
    public void run() {
        Skeleton.printCall("Test18", "run()");

        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();
        DragonHead dragonHead = new DragonHead();
        SweeperHead backupHead = new SweeperHead();

        snowplow.changeHead(dragonHead);

        // 1) Készletellenőrzés: 0 nyersanyag
        Skeleton.printCall("Snowplow", "getBiokeroseneStock()");
        Skeleton.printReturn("0");

        // 2) A speciális fej működése megszűnik nyersanyag-hiányban
        Skeleton.printCall("Snowplow", "clean(lane)");
        Skeleton.printCall("DragonHead", "clean(lane, snowplow)");
        Skeleton.printState("Nincs nyersanyag, a speciális fej hatástalan.");
        Skeleton.printReturn("");
        Skeleton.printReturn("");

        // 3) Játékosi döntés - fejcsere ág
        Skeleton.printState("A játékos döntése: fejet cserél.");
        snowplow.changeHead(backupHead);

        // 4) Játékosi döntés - vásárlás ág (determinista bemenettel)
        Skeleton.printState("Alternatív döntés: nyersanyagot vásárol.");
        Buyable rawMaterial = () -> {
            Skeleton.printCall("Buyable", "getPrice()");
            Skeleton.printReturn("120");
            return 120;
        };

        List<Buyable> inventory = new ArrayList<>();
        inventory.add(rawMaterial);
        Store store = new Store(inventory);
        Skeleton.setScanner(new Scanner(new ByteArrayInputStream("1\n".getBytes())));
        store.buy(cleanerRole, rawMaterial);

        Skeleton.printReturn("");
    }
}