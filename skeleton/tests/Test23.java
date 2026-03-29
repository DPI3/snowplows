package skeleton.tests;

import java.util.Scanner;
import skeleton.src.*;

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
    public void run() {
        // A teszt indítása a Skeleton.tick() hívással [cite: 4048]
        Skeleton.printCall("Skeleton", "tick()");

        Car car = new Car();
        
        // Itt a car.move() hívás következik
        car.move();

        // A tick() hívás vége
        Skeleton.printReturn("");
    }
}