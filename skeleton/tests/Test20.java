package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 20: Assigning a route to a bus.
 * Verifies that the BusdriverRole can request the shortest path from the 
 * RoadNetwork and assign it to the Bus.
 */
public class Test20 implements TestCase {

    /**
     * Runs the test sequence.
     * Initializes a driver, a bus, and a destination, then assigns the route.
     *
     * @param scanner the scanner object to read user input
     */
    @Override
    public void run(Scanner scanner) {
        BusdriverRole driver = new BusdriverRole();
        Bus bus = new Bus();
        Terminal destination = new Terminal(); // Node subclass based on UML
        
        // 1. Assign route
        driver.assignRoute(bus, destination);
        
        // 2. The bus starts moving after the assignment
        bus.move(scanner);
    }
}