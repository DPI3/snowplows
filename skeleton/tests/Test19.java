package tests;

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
        CleanerRole cleanerRole = new CleanerRole();
        Snowplow snowplow = new Snowplow();
        
        // Starting the action defined in the sequence diagram
        cleanerRole.controlSnowplow(snowplow);
    }
}