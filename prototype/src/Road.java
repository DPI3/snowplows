package prototype.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt alaposztály minden úttípushoz.
 * Kezeli a sávok listáját és az időjárási hatásokat.
 */
public abstract class Road {
    
    protected List<Lane> lanes = new ArrayList<>();
    protected Node source;
    protected Node destination;

    /**
     * Megkeresi és visszaadja a paraméterként kapott sáv melletti 
     * szomszédos sávot a megadott index (irány) alapján.
     */
    public Lane getAdjacentLane(Lane lane, int index) {
        int pos = lanes.indexOf(lane);
        if (pos == -1) return null;
        
        int newPos = pos + index;
        if (newPos < 0 || newPos >= lanes.size()) {
            return null;
        }
        
        return lanes.get(newPos);
    }

    /**
     * Absztrakt metódus, amelyet a leszármazottak felüldefiniálnak.
     * Az időjárás hatását alkalmazza az útszakasz sávjaira.
     */
    public abstract void applyWeatherEffects(Weather weather);
    
    /**
     * Hozzáadja a paraméterben átvett Lane objektumot a lanes listához.
     */
    public void addLane(Lane lane) {
        lanes.add(lane);
    }
}