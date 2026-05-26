package src;

import java.util.*;

/**
 * A RoadNetwork osztály felelős Zúzmaraváros teljes úthálózatának reprezentálásáért és kezeléséért.
 * Ez az osztály működik a játék útvonalkereső motorjaként: nyilvántartja a csomópontokat és útszakaszokat,
 * valamint kiszámítja a járművek számára az aktuálisan járható legrövidebb utat.
 */
public class RoadNetwork {
    /** Az úthálózatot felépítő összes csomópont listája. */
    private List<Node> nodes = new ArrayList<>();

    /** Az úthálózatot felépítő összes útszakasz listája. */
    private List<Road> roads = new ArrayList<>();

    /**
     * Meghatározza a két megadott csomópont közötti legrövidebb, akadálymentes útvonalat.
     * A számítás során a lane.getDynamicWeight() értéket használja súlyként.
     * A Dijkstra-algoritmust alkalmazza a dinamikus súlyok felhasználásával.
     *
     * @param from a kiinduló csomópont
     * @param to a cél csomópont
     * @return a kiszámított legrövidebb útvonal
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
     *
     * @param elod az előd-sávok leképezése csomópontonként
     * @param to a célcsomópont
     * @return az összeállított útvonal
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
     * Visszaadja a paraméterként megadott sávhoz tartozó konkrét sáv objektumot az úthálózatból.
     *
     * @param coord a keresett sáv
     * @return a megtalált sáv, vagy null ha nem létezik
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

    /**
     * Belső segédosztály a Dijkstra-algoritmushoz, amely egy csomópontot és annak távolságát tárolja.
     */
    private static class NodeDistance {
        /** A csomópont. */
        Node node;
        /** A csomópont távolsága a kiindulóponttól. */
        double dist;

        /**
         * NodeDistance példány létrehozása.
         *
         * @param node a csomópont
         * @param dist a távolság
         */
        NodeDistance(Node node, double dist) {
            this.node = node;
            this.dist = dist;
        }
    }

    /**
     * Új csomópont hozzáadása az úthálózathoz.
     *
     * @param node a hozzáadandó csomópont
     */
    public void addNode(Node node) {
        if (node != null && !nodes.contains(node)) {
            nodes.add(node);
        }
    }

    /**
     * Új útszakasz hozzáadása az úthálózathoz.
     *
     * @param road a hozzáadandó útszakasz
     */
    public void addRoad(Road road) {
        if (road != null && !roads.contains(road)) {
            roads.add(road);
        }
    }

    /**
     * Visszaadja az úthálózat összes csomópontját.
     *
     * @return a csomópontok listája
     */
    public List<Node> getNodes() {
        return nodes;
    }

    /**
     * Visszaadja az úthálózat összes útszakaszát.
     *
     * @return az útszakaszok listája
     */
    public List<Road> getRoads() {
        return roads;
    }
}
