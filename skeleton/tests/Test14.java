package skeleton.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import skeleton.src.*;

/**
 * 14. teszteset: Sikeres biokerozin vásárlás teszt.
 *
 * A teszteset ellenőrzi, hogy a CleanerRole megfelelően vásárol
 * biokerozint a Store-ból, ha van elég pénze.
 */
public class Test14 implements TestCase {

    @Override
    public void run() {
        Skeleton.printCall("Test14", "run(scanner)");

        // Szereplők és bolt inicializálása
        CleanerRole cleanerRole = new CleanerRole();
        Buyable item = new Buyable() {
            @Override
            public int getPrice() {
                Skeleton.printCall("Buyable", "getPrice()");
                Skeleton.printReturn("");
                return 100;
            }
        };

        List<Buyable> inventory = new ArrayList<>();
        inventory.add(item);
        Store store = new Store(inventory);

        // A teszt determinisztikusan az "igen" ágat választja a vásárlásnál.
        Skeleton.setScanner(new Scanner("1"));

        Snowplow snowplow = cleanerRole.getSnowplow();
        int beforeStock = snowplow.getBiokeroseneStock();

        Skeleton.printCall("CleanerRole", "buy(item)");
        Skeleton.printCall("Store", "buy(cleanerRole, item)");

        int price = item.getPrice();
        int money = cleanerRole.getMoney();
        int decision = Skeleton.requestInput("Elegendő a pénz a vásárláshoz? (1: Igen, 2: Nem)");

        boolean storeBuyResult = false;
        if (decision == 1 && money >= price) {
            cleanerRole.decreaseMoney(price);
            cleanerRole.addBiokerosene(10);
            Skeleton.printState("Sikeres vásárlás.");
            storeBuyResult = true;
        } else {
            Skeleton.printState("Sikertelen vásárlás.");
        }

        Skeleton.printReturn(String.valueOf(storeBuyResult));
        Skeleton.printReturn(String.valueOf(storeBuyResult));

        int afterStock = snowplow.getBiokeroseneStock();
        if (afterStock > beforeStock) {
            Skeleton.printState("A hókotró biokerozin készlete nőtt.");
        }

        Skeleton.printReturn("");
    }
}