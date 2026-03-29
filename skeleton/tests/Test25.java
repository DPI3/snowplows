package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 25: Car reaching its destination test.
 * Simulates a car entering a node (like an intersection or residence),
 * checking if it is the destination, and removing the car from traffic if so.
 */
public class Test25 implements TestCase {

    /**
     * Runs the test sequence.
     * Triggers the node's vehicle entry logic.
     *
     * @param scanner the scanner object to read user input
     */
    @Override
    public void run() {
        Node node = new Intersection(); // Using an Intersection as the concrete Node
        Car car = new Car();
        
        // The sequence starts when the vehicle enters the node
        node.onVehicleEnter(car);
    }
}