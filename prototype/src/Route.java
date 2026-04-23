package skeleton.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy kiszámított útvonalat képvisel az úthálózaton keresztül.
 * Azon sávok rendezett listájából áll, amelyeken egy járműnek át kell haladnia.
 */
public class Route {
    private List<Lane> lanes = new ArrayList<>();

    /**
     * Meghatározza az útvonal következő sávját a jármű jelenlegi sávja alapján.
     * A szkeleton fázisban egy mock (ál) következő sávot ad vissza.
     *
     * @param current az a sáv, amelyen a jármű jelenleg tartózkodik
     * @return a következő áthaladandó sáv
     */
    public Lane getNextLane(Lane current) {
        Skeleton.printCall("Route", "getNextLane(currentLane)");
        
        Lane nextLane = new Lane();
        
        Skeleton.printReturn("nextLane");
        return nextLane;
    }

    public List<Lane> getLanes() {
        return lanes;
    }
}