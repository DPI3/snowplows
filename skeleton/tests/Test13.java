package skeleton.tests;

import java.util.ArrayList;
import java.util.List;
import skeleton.src.*;

/**
 * 13. teszteset: Sikeres kotrófej vásárlás teszt.
 *
 * A teszteset ellenőrzi, hogy a CleanerRole megfelelően vásárol kotrófejet
 * (söprő, hányó, jégtörő, sószóró, sárkány fejet) a Store-ból, ha van elég pénze.
 *
 * Forgatókönyv:
 * 1. Előfeltétel: CleanerRole.money >= item.getPrice() (a CleanerRole-nak van elég pénze a fej megvételéhez)
 * 2. A CleanerRole buy(cleanerRole, item) műveletet hív a Store-on
 * 3. A Store ellenőrzi a CleanerRole pénzét (Itt történik a Decision)
 * 4. A Store levonja a fej árát a CleanerRole pénzéből
 * 5. A CleanerRole megkapja az új fejet
 */
public class Test13 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     *
     * @param scanner a felhasználói bemenet olvasására szolgáló objektum
     */
    @Override
    public void run() {

        Skeleton.printCall("Test13", "run(scanner)");

        // Szereplők és elemek inicializálása
        CleanerRole cleanerRole = new CleanerRole();
        DragonHead dragonHead = new DragonHead();

        // Bolt készletének összeállítása és a Store inicializálása
        List<Buyable> inventory = new ArrayList<>();
        inventory.add(dragonHead);
        Store store = new Store(inventory);

        // 1. Előfeltétel: A CleanerRole-nak van elég pénze.
        // A Skeleton a Store.buy() metóduson belül egy Decision keretében kérdezi meg a tesztelőt (vagy olvassa a test13_in.txt-ből az '1'-es választ).

        // 2. A CleanerRole buy műveletet hív a Store-on
        // A Store végzi el a 3-5. lépéseket (ellenőrzés, levonás, átadás)
        store.buy(cleanerRole, dragonHead);

        Skeleton.printReturn("");
    }
}