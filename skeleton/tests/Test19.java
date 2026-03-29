package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 19: Earning points by cleaning.
 * This test verifies that a CleanerRole earns points when successfully 
 * controlling a snowplow to clean a lane.
 */
public class Test19 implements TestCase {

    /**
     * Runs the test sequence.
     * Initializes the necessary domain objects and triggers the cleaning process.
     *
     * @param scanner the scanner object to read user input (if needed by the domain objects)
     */
    @Override
    public void run(Scanner scanner) {
        CleanerRole c = new CleanerRole();
        Snowplow sp = new Snowplow();
        Lane l = new Lane();
        
        // 1. Lépés: Hókotró irányítása
        c.controlSnowplow(sp, l);
        
        // 2. Lépés: Pontszám lekérdezése
        c.getScore();
    }
}