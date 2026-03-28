package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 23: Car moving on a passable lane test.
 * Verifies that a car can successfully move to the next lane if it is clear.
 */
public class Test23 implements TestCase {

    /**
     * Runs the test sequence.
     *
     * @param scanner the scanner object to read user input
     */
    @Override
    public void run(Scanner scanner) {
        Car car = new Car();
        
        // Starting the action. In the skeleton, isPassable() should return true
        // based on the testfile input.
        car.move();
    }
}