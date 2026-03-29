package skeleton.tests;

import java.util.ArrayList;
import java.util.List;
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

        Snowplow snowplow = cleanerRole.getSnowplow();
        int beforeStock = snowplow.getBiokeroseneStock();

        cleanerRole.buy(store, item);

        int afterStock = snowplow.getBiokeroseneStock();
        if (afterStock > beforeStock) {
            Skeleton.printState("A hókotró biokerozin készlete nőtt.");
        }

        Skeleton.printReturn("");
    }
}