package skeleton.src;

import java.util.ArrayList;
import java.util.List;

/**
 * A csomópontokból és utakból álló teljes úthálózatot képviseli.
 * Felelős az útvonalkereső algoritmusok és a globális időjárási események kezeléséért.
 */
public class RoadNetwork {
    private List<Node> nodes = new ArrayList<>();
    private List<Road> roads = new ArrayList<>();

    /**
     * Kiszámítja a legrövidebb útvonalat két csomópont között, figyelembe véve a dinamikus 
     * súlyozásokat és a járhatatlan sávokat. A szkeleton fázisban ez egy mock (ál) útvonalat ad vissza.
     *
     * @param from a kezdő csomópont
     * @param to a cél csomópont
     * @return a kiszámított útvonal, amely tartalmazza az áthaladandó sávok sorrendjét
     */
    public Route getShortestPath(Node from, Node to) {
        Skeleton.printCall("RoadNetwork", "getShortestPath(from, dest)");
        
        Route r = new Route();
        
        Skeleton.printReturn("r");
        return r;
    }
}