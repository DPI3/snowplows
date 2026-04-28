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

    public Road() {
    }

    public List<Lane> getLanes() {
        return lanes;
    }

    protected int snowLevel = 0;

    public void increaseSnowLevel() {
        snowLevel++;
    }

    public void reduceSnowLevel() {
        if (snowLevel > 0) {
            snowLevel--;
        }
    }

    public int getSnowLevel() {
        return snowLevel;
    }

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
    public void applyWeatherEffects(Weather weather) {
        // csak reagál, nem irányít
        increaseSnowLevel();
    }
    
    /**
     * Hozzáadja a paraméterben átvett Lane objektumot a lanes listához.
     */
    public void addLane(Lane lane) {
        if (lane != null) {
            lanes.add(lane);
            lane.setParentRoad(this);
            lane.setSource(source);
            lane.setDestination(destination);
        }
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public Node getDestination() {
        return destination;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }
}