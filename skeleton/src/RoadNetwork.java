package skeleton.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the entire road network consisting of nodes and roads.
 * Responsible for managing pathfinding algorithms and global weather events.
 */
public class RoadNetwork {
    private List<Node> nodes = new ArrayList<>();
    private List<Road> roads = new ArrayList<>();

    /**
     * Calculates the shortest path between two nodes considering dynamic weights 
     * and impassable lanes. In the skeleton phase, this returns a mock Route.
     *
     * @param from the starting Node
     * @param to the destination Node
     * @return the calculated Route containing the sequence of lanes
     */
    public Route getShortestPath(Node from, Node to) {
        Skeleton.printCall("RoadNetwork", "getShortestPath(from, dest)");
        
        Route r = new Route();
        
        Skeleton.printReturn("r");
        return r;
    }
}