package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 11: Takarítás sárkány fejjel teszt.
 * This test verifies that a snowplow equipped with a dragon head
 * correctly cleans the lane using biokerosene and rewards the CleanerRole.
 */
public class Test11 implements TestCase {

    /**
     * Runs the test sequence.
     * Initializes the necessary domain objects, sets up the precondition,
     * and triggers the cleaning process.
     *
     * @param scanner the scanner object to read user input for the biokerosene decision
     */
    @Override
    public void run(Scanner scanner) {
        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();
        DragonHead dragonHead = new DragonHead();
        Lane lane = new Lane();
        
        // 1. Előfeltétel: A hókotró aktuális feje a sárkány fej
        snowplow.setHead(dragonHead);
        
        // 2. A CleanerRole a hókotrót a takarítani kívánt útszakaszra irányítja
        cleanerRole.controlSnowplow(snowplow, lane);
    }
}