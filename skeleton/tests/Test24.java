package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 24: Car stuck on an impassable road test.
 * Verifies that if the next lane is impassable (e.g., due to an accident 
 * or heavy snow), the car's speed is set to 0 and it waits.
 */
public class Test24 implements TestCase {

    /**
     * Runs the test sequence.
     *
     * @param scanner the scanner object to read user input
     */
    @Override
    public void run(Scanner scanner) {
        Car car = new Car();
        
        // Starting the action. Inside move(), the nextLane.isPassable(scanner) 
        // will ask the user, and the testfile will provide 'false'.
        car.move(scanner);
    }
}