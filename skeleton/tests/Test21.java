package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 21: Bus movement test.
 * Simulates a bus moving along its assigned route on a passable lane,
 * checking for accidents and updating its position.
 */
public class Test21 implements TestCase {

    /**
     * Runs the test sequence.
     * Triggers the move action of the bus.
     *
     * @param scanner the scanner object to read user input
     */
    @Override
    public void run(Scanner scanner) {
        // Az assert szerint a Game.tick() az első hívás
        Skeleton.printCall("Game", "tick()");
        
        Bus bus = new Bus();
        // A Game.tick() meghívja a Bus.tick()-et
        bus.tick(scanner); 

        Skeleton.printReturn(""); // Game.tick() vége
    }
}