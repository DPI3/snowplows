package src;

import java.util.*;

/**
 * A RoadNetwork osztály felelős Zúzmaraváros teljes úthálózatának reprezentálásáért és kezeléséért[cite: 138].
 * Ez az osztály működik a játék útvonalkereső motorjaként: nyilvántartja a csomópontokat és útszakaszokat, 
 * valamint kiszámítja a járművek számára az aktuálisan járható legrövidebb utat[cite: 139].
 */
public class RoadNetwork {
    /** Az úthálózatot felépítő összes csomópont listája[cite: 152]. */
    private List<Node> nodes = new ArrayList<>();

    /** Az úthálózatot felépítő összes útszakasz listája[cite: 152]. */
    private List<Road> roads = new ArrayList<>();

    /**
     * Meghatározza a két megadott csomópont közötti legrövidebb, akadálymentes útvonalat[cite: 159].
     * A számítás során a lane.getDynamicWeight() értéket használja súlyként[cite: 160].
     * A Dijkstra-algoritmust alkalmazza a dinamikus súlyok felhasználásával[cite: 163].
     * 
     * @param from a kiinduló csomópont
     * @param to a cél csomópont
     */
    public Route getShortestPath(Node from, Node to) {
        Map<Node, Double> distance = new HashMap<>();
        Map<Node, Lane> elod = new HashMap<>();

        PriorityQueue<NodeDistance> Q = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.dist));

        for (Node n : nodes) {
            distance.put(n, Double.POSITIVE_INFINITY);
        }
        distance.put(from, 0.0);
        Q.add(new NodeDistance(from, 0.0));

        while (!Q.isEmpty()) {
            Node current = Q.poll().node;

            if (current.equals(to)) break;

            for (Road r : roads) {
                if (r.getSource() != null && r.getSource().equals(current)) {
                    for (Lane l : r.getLanes()) {
                        if (!l.isPassable()) continue;

                        double w = l.getDynamicWeight();
                        Node neighbor = l.getDestination();

                        double newDist = distance.get(current) + w;
                        if (newDist < distance.get(neighbor)) {
                            distance.put(neighbor, newDist);
                            elod.put(neighbor, l);
                            Q.add(new NodeDistance(neighbor, newDist));
                        }
                    }
                }
            }
        }

        return buildRoute(elod, to);
    }

    /**
     * Segédmetódus az útvonal összeállításához a célcsomóponttól visszafelé haladva.
     */
    private Route buildRoute(Map<Node, Lane> elod, Node to) {
        Route route = new Route();
        Node current = to;

        while (elod.containsKey(current)) {
            Lane l = elod.get(current);
            route.getLanes().add(0, l);
            current = l.getSource();
        }
        return route;
    }

    /**
     * Visszaadja a paraméterként megadott koordinátákhoz vagy azonosítóhoz tartozó konkrét sáv objektumot[cite: 180].
     * 
     * @param coord az azonosító
     * @return a hozzá tartozó sáv
     */
    public Lane getLane(Lane coord) {
        for (Road r : roads) {
            for (Lane l : r.getLanes()) {
                if (l.equals(coord)) {
                    return l;
                }
            }
        }
        return null;
    }

    private static class NodeDistance {
        Node node;
        double dist;
        NodeDistance(Node node, double dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    public void addNode(Node node) {
        if (node != null && !nodes.contains(node)) {
            nodes.add(node);
        }
    }

    public void addRoad(Road road) {
        if (road != null && !roads.contains(road)) {
            roads.add(road);
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Road> getRoads() {
        return roads;
    }
}