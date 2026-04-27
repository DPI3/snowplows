package prototype.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy kiszámított útvonalat képvisel az úthálózaton keresztül.
 */
public class Route {
    
    private List<Lane> lanes = new ArrayList<>();

    /**
     * Megkeresi a paraméterként kapott aktuális sávot az útvonalban, 
     * és visszaadja a soron következő sáv objektumot.
     */
    public Lane getNextLane(Lane curr) {
        int idx = lanes.indexOf(curr);
        
        // Ha nem található, vagy a legutolsó sávon van
        if (idx == -1 || idx == lanes.size() - 1) {
            return null;
        }
        
        return lanes.get(idx + 1);
    }

    /**
     * Visszaadja az útvonal teljes hosszát.
     */
    public double getLength() {
        double sum = 0;
        for (Lane lane : lanes) {
            sum += lane.getLength();
        }
        return sum;
    }

    /**
     * Visszaadja az útvonalon szereplő sávokat.
     */
    public List<Lane> getLanes() {
        return lanes;
    }
    
    public void addLane(Lane lane) {
        this.lanes.add(lane);
    }
}