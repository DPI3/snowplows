package skeleton.src;

/**
 * Abstract base class for all connection points (intersections, terminals, 
 * workplaces, residences) in the road network.
 */
public abstract class Node {
    protected String id;

    /**
     * Handles the event when a vehicle enters this node.
     * Specific behavior is implemented by subclasses.
     *
     * @param vehicle the vehicle entering the node
     */
    public void onVehicleEnter(Vehicle vehicle) {
        Skeleton.printCall("Node", "onVehicleEnter(vehicle)");
        Skeleton.printReturn("");
    }

    /**
     * Checks if this node is the final destination for the vehicle.
     * In the skeleton phase, it defaults to true for successful arrival tests.
     *
     * @return true if the node is the destination, false otherwise
     */
    public boolean checkDestination() {
        Skeleton.printCall("Node", "checkDestination()");
        Skeleton.printReturn("true");
        return true;
    }
    
    /**
     * Removes the vehicle from active traffic once it reaches its destination.
     */
    public void removeFromTraffic() {
        Skeleton.printCall("Node", "removeFromTraffic()");
        Skeleton.printState("Vehicle becomes inactive, disappears from the network");
        Skeleton.printReturn("");
    }
}