package skeleton.tests;

import skeleton.src.*;

/**
 * 17. teszteset: Kotrófej cseréje teszt.
 *
 * A teszt ellenőrzi, hogy a CleanerRole képes-e lecserélni egy Snowplow 
 * aktuális kotrófejét egy másikra.
 */
public class Test17 implements TestCase {

    @Override
    public void run() {
        Skeleton.printCall("Test17", "run()");

        // 1. A takarító kiválasztja a hókotrót és az új fejet.
        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();
        DragonHead newHead = new DragonHead();

        // 2. A szerepkör meghívja a fejcsere műveletet az adott hókotrón.
        Skeleton.printCall("CleanerRole", "changeHead(snowplow, newHead)");
        snowplow.changeHead(newHead);
        Skeleton.printReturn("");

        // 3. A Snowplow.changeHead hívásban a currentHead értéke newHead-re változik.
        Skeleton.printReturn("");
    }
}