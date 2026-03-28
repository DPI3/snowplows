package skeleton.src;

/**
 * Represents a terminal station specifically used by buses.
 * Serves as the starting and ending points of bus routes.
 */
public class Terminal extends Node {
    
    public Terminal(String id) {
        super(id);
    }

    /**
     * Handles a vehicle entering the terminal. If it is a bus, it may 
     * increment completed rounds and get a new route.
     *
     * @param vehicle the vehicle entering the terminal
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        Skeleton.printCall("Terminal", "onVehicleEnter(vehicle)");
        checkDestination();
        Skeleton.printReturn("");
    }
}