package skeleton.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a calculated path through the road network.
 * Consists of an ordered list of lanes that a vehicle must traverse.
 */
public class Route {
    private List<Lane> lanes = new ArrayList<>();

    /**
     * Determines the next lane on the route based on the vehicle's current lane.
     * In the skeleton phase, this returns a mock next lane.
     *
     * @param current the lane the vehicle is currently on
     * @return the next Lane to traverse
     */
    public Lane getNextLane(Lane current) {
        Skeleton.printCall("Route", "getNextLane(currentLane)");
        
        Lane nextLane = new Lane();
        
        Skeleton.printReturn("nextLane");
        return nextLane;
    }
}