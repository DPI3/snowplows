package skeleton.tests;

import java.util.ArrayList;
import java.util.List;
import skeleton.src.*;

/**
 * 15. teszteset: Sikeres só vásárlás teszt.
 *
 * A teszt ellenőrzi, hogy a CleanerRole képes-e sót vásárolni a Store-ból,
 * és a vásárolt mennyiség bekerül-e a Snowplow saltStock készletébe.
 */
public class Test15 implements TestCase {

    @Override
    public void run() {
        Skeleton.printCall("Test15", "run(scanner)");

        // Szereplők és hókotró inicializálása
        CleanerRole cleanerRole = new CleanerRole();

        // Só reprezentálása Buyable objektumként (mivel nincs külön osztály)
        Buyable saltItem = new Buyable() {
            @Override
            public int getPrice() {
                Skeleton.printCall("Buyable", "getPrice()");
                Skeleton.printReturn("");
                return 30;
            }
        };

        // Bolt összeállítása
        List<Buyable> inventory = new ArrayList<>();
        inventory.add(saltItem);
        Store store = new Store(inventory) {
            @Override
            public boolean buy(CleanerRole cleanerRole, Buyable item) {
                Skeleton.printCall("Store", "buy(cleanerRole, item)");

                int price = item.getPrice();
                int money = cleanerRole.getMoney();
                int decision = Skeleton.requestInput("Elegendő a pénz a vásárláshoz? (1: Igen, 2: Nem)");

                if (decision == 1 && money >= price) {
                    cleanerRole.decreaseMoney(price);

                    Skeleton.printCall("CleanerRole", "addSalt(amount)");
                    Skeleton.printCall("Snowplow", "addSalt(amount)");
                    Skeleton.printState("saltStock értéke megnövelve.");
                    Skeleton.printReturn("");
                    Skeleton.printReturn("");

                    Skeleton.printReturn("true");
                    return true;
                }

                Skeleton.printReturn("false");
                return false;
            }
        };

        // A vásárlás indítása a CleanerRole-on keresztül
        Skeleton.printCall("CleanerRole", "buy(item)");
        store.buy(cleanerRole, saltItem);
        Skeleton.printReturn("");

        Skeleton.printReturn("");
    }
}