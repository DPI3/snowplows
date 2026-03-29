package skeleton.tests;

import java.util.ArrayList;
import java.util.List;
import skeleton.src.*;

/**
 * 16. teszteset: Sikertelen vásárlás teszt.
 *
 * A teszt azt vizsgálja, hogy ha a felhasználó a Skeleton döntési pontjánál
 * a "Nem" (2) opciót választja (nincs elég pénz), akkor a vásárlás 
 * nem megy végbe, és nem történik levonás vagy készletmódosítás.
 */
public class Test16 implements TestCase {

    @Override
    public void run() {
        Skeleton.printCall("Test16", "run(scanner)");

        // Szereplők inicializálása
        CleanerRole cleanerRole = new CleanerRole();

        // Egy tetszőleges Buyable tétel létrehozása
        Buyable item = new Buyable() {
            @Override
            public int getPrice() {
                Skeleton.printCall("Buyable", "getPrice()");
                Skeleton.printReturn(""); // Magas ár a sikertelenséghez
                return 1000;
            }
        };

        List<Buyable> inventory = new ArrayList<>();
        inventory.add(item);
        Store store = new Store(inventory) {
            @Override
            public boolean buy(CleanerRole cleanerRole, Buyable item) {
                Skeleton.printCall("Store", "buy(cleanerRole, item)");

                int price = item.getPrice();
                int money = cleanerRole.getMoney();
                int decision = Skeleton.requestInput("Elegendő a pénz a vásárláshoz? (1: Igen, 2: Nem)");

                if (decision == 1 && money >= price) {
                    Skeleton.printState("Sikeres vásárlás.");
                    System.out.println("      <<<return true");
                    return true;
                }

                Skeleton.printState("Sikertelen vásárlás: nincs elég pénz.");
                System.out.println("      <<<return false");
                return false;
            }
        };

        // A hívási lánc indítása: CleanerRole -> Store
        // A test16_in.txt-ben lévő '2' miatt a Store.buy false-szal tér majd vissza
        cleanerRole.buy(store, item);

        Skeleton.printReturn("");
        Skeleton.printReturn("");
    }
}