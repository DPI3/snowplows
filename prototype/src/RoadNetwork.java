package prototype.src;

import java.util.*;

/**
 * A RoadNetwork osztály felelős Zúzmaraváros teljes úthálózatának reprezentálásáért és kezeléséért[cite: 138].
 * Ez az osztály működik a játék útvonalkereső motorjaként: nyilvántartja a csomópontokat és útszakaszokat, 
 * valamint kiszámítja a járművek számára az aktuálisan járható legrövidebb utat[cite: 139].
 */
public class RoadNetwork {
    private List<Node> nodes = new ArrayList<>(); // Az úthálózatot felépítő összes csomópont listája[cite: 152].
    private List<Road> roads = new ArrayList<>(); // Az úthálózatot felépítő összes útszakasz listája[cite: 152].

    /**
     * Meghatározza a két megadott csomópont közötti legrövidebb, akadálymentes útvonalat[cite: 159].
     * A számítás során a lane.getDynamicWeight() értéket használja súlyként[cite: 160].
     * A Dijkstra-algoritmust alkalmazza a dinamikus súlyok felhasználásával[cite: 163].
     */
    public Route getShortestPath(Node from, Node to) {
        // Távolságok és elődök (sávok) tárolása a Dijkstra-hoz [cite: 163]
        Map<Node, Double> distance = new HashMap<>();
        Map<Node, Lane> elod = new HashMap<>();
        
        // Min-priority queue a csomópontok és aktuális távolságuk tárolására [cite: 163]
        PriorityQueue<NodeDistance> Q = new PriorityQueue<>(Comparator.comparingDouble(nd -> nd.dist));

        // Inicializálás: minden csomópont végtelen távolságra, kivéve a kezdőpontot [cite: 164-167]
        for (Node n : nodes) {
            distance.put(n, Double.POSITIVE_INFINITY);
        }
        distance.put(from, 0.0);
        Q.add(new NodeDistance(from, 0.0)); // Q.insert(from, 0) [cite: 167]

        while (!Q.isEmpty()) {
            Node current = Q.poll().node; // current = Q.extractMin() [cite: 169]

            if (current.equals(to)) break; // if current = to: break [cite: 170]

            // Megkeressük az összes sávot, ami a jelenlegi csomópontból indul [cite: 171]
            for (Road r : roads) {
                // Feltételezzük, hogy a Road ismeri a forrását (source)
                if (r.getSource() != null && r.getSource().equals(current)) {
                    for (Lane l : r.getLanes()) {
                        // Ha a sáv nem járható (DeepSnow, IceSheet, Impassable), kihagyjuk [cite: 162, 172]
                        if (!l.isPassable()) continue;

                        double w = l.getDynamicWeight(); // w = l.getDynamicWeight() [cite: 173]
                        Node neighbor = l.getDestination(); // neighbor = lane célcsomópontja [cite: 174]

                        // Relaxáció [cite: 175]
                        double newDist = distance.get(current) + w;
                        if (newDist < distance.get(neighbor)) {
                            distance.put(neighbor, newDist);
                            elod.put(neighbor, l); // előd[neighbor] = l [cite: 177]
                            Q.add(new NodeDistance(neighbor, newDist)); // Q.insert(neighbor, dist) [cite: 178]
                        }
                    }
                }
            }
        }

        // Útvonal felépítése az elődök alapján (buildRoute) [cite: 179]
        return buildRoute(elod, to);
    }

    /**
     * Segédmetódus az útvonal összeállításához a célcsomóponttól visszafelé haladva.
     */
    private Route buildRoute(Map<Node, Lane> elod, Node to) {
        Route route = new Route();
        Node current = to;
        
        // Visszafelé haladunk a célállomástól a forrásig a sávok mentén
        while (elod.containsKey(current)) {
            Lane l = elod.get(current);
            route.getLanes().add(0, l); // Mindig az elejére szúrjuk be a helyes sorrendhez
            // A sáv forrása lesz a következő vizsgált csomópont
            // (Feltételezzük, hogy a Lane osztályban tárolva van a sourceNode)
            current = l.getSource(); 
        }
        return route;
    }

    /**
     * Visszaadja a paraméterként megadott koordinátákhoz vagy azonosítóhoz tartozó konkrét sáv objektumot[cite: 180].
     */
    public Lane getLane(Lane coord) {
        for (Road r : roads) { // for each road r in roads [cite: 182]
            for (Lane l : r.getLanes()) { // for each lane l in r.lanes [cite: 183]
                // Megkeressük az egyező sávot [cite: 184]
                if (l.equals(coord)) {
                    return l;
                }
            }
        }
        return null; // return null [cite: 185]
    }

    // Segédosztály a PriorityQueue-hoz
    private static class NodeDistance {
        Node node;
        double dist;
        NodeDistance(Node node, double dist) {
            this.node = node;
            this.dist = dist;
        }
    }
}