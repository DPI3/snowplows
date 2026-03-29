package skeleton.tests;

import java.util.Scanner;
import skeleton.src.*;

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
    public void run() {
        // 1. A szimuláció kezdete (Game szint)
        Skeleton.printCall("Game", "tick()");
        
        // Itt létrehozol egy buszt
        Bus bus = new Bus();
        
        // 2. A Game meghívja a Bus tick-jét
        bus.tick(); 

        // 3. Lezárjuk a Game.tick() hívást
        Skeleton.printReturn(""); 
    }
}