package skeleton.src;

import java.util.Scanner;

/**
 * Represents a single lane on a road segment.
 * Maintains its own state regarding snow thickness, ice, and accidents.
 */
public class Lane {

    private LaneState state = new Clear();

    /**
     * Changes the current state of the lane (e.g., when it snows or gets cleaned).
     * @param newState the new state to apply
     */
    public void setState(LaneState newState) {
        Skeleton.printCall("Lane", "setState(newState)");
        this.state = newState;
        Skeleton.printState("Lane state changed to: " + newState.getClass().getSimpleName());
        Skeleton.printReturn("");
    }    

    /**
     * Checks if the lane is currently passable for a vehicle.
     * In the skeleton phase, it prompts the tester for the lane's state.
     *
     * @param scanner the scanner to read tester input
     * @return true if passable, false if blocked (e.g., by deep snow)
     */
    public boolean isPassable(Scanner scanner) {
        Skeleton.printCall("Lane", "isPassable()");
        
        // Döntés bekérése a Skeleton segédosztályon keresztül
        int answer = Skeleton.requestInput(scanner, "Is the lane passable? (1: Yes, 2: No)");
        boolean result = (answer == 1);
        
        Skeleton.printReturn(String.valueOf(result));
        return result;
    }

    /**
     * Checks if there is an active accident on this lane.
     *
     * @param scanner the scanner to read tester input
     * @return true if there is an accident, false otherwise
     */
    public boolean hasAccident(Scanner scanner) {
        Skeleton.printCall("Lane", "hasAccident()");
        
        int answer = Skeleton.requestInput(scanner, "Does the lane have an accident? (1: Yes, 2: No)");
        boolean result = (answer == 1);
        
        Skeleton.printReturn(String.valueOf(result));
        return result;
    }
}