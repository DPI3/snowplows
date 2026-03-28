package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 22: Bus completing a round test.
 * Simulates a bus reaching its destination terminal, incrementing the driver's
 * completed rounds, and obtaining a new route for the return trip.
 */
public class Test22 implements TestCase {

    /**
     * Runs the test sequence.
     *
     * @param scanner the scanner object to read user input
     */
    @Override
    public void run(Scanner scanner) {
        Bus bus = new Bus();
        
        // Starting the movement. The internal logic of the domain classes 
        // should handle the checkTerminalReached() logic.
        bus.move(scanner); 
    }
}