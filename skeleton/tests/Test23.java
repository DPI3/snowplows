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
        // A teszt indítása a Skeleton.tick() hívással [cite: 4048]
        Skeleton.printCall("Skeleton", "tick()");

        Car car = new Car();
        
        // Itt a car.move() hívás következik
        car.move(scanner);

        // A tick() hívás vége
        Skeleton.printReturn("");
    }
}