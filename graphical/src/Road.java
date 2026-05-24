package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt alaposztály minden úttípushoz.
 * Kezeli a sávok listáját és az időjárási hatásokat.
 */
public abstract class Road {
    
    /** Az útszakaszt felépítő sávok gyűjteménye. */
    protected List<Lane> lanes = new ArrayList<>();

    /** Az útszakasz kiinduló csomópontja. */
    protected Node source;

    /** Az útszakasz célállomását jelentő csomópont. */
    protected Node destination;

    /** Az útszakaszon lévő aktuális hómennyiség */
    protected int snowLevel = 0;

    public Road() {}

    /**
     * Visszaadja az útszakaszt felépítő sávok gyűjteményét.
     * 
     * @return az útszakaszt felépítő sávok gyűjteménye.
     */
    public List<Lane> getLanes() {
        return lanes;
    }

    /** 
     * Növeli az útszakaszon levő hómennyiséget.
     * 
     * @return a hómennyiség
    */
    public void increaseSnowLevel() {
        snowLevel++;
    }

    /** 
     * Csökkenti az útszakaszon levő hómennyiséget.
     * 
    */
    public void reduceSnowLevel() {
        if (snowLevel > 0) {
            snowLevel--;
        }
    }

    /** 
     * Visszaadja az útszakaszon levő hómennyiséget.
     * 
     * @return a hómennyiség
    */
    public int getSnowLevel() {
        return snowLevel;
    }

    /**
     * Megkeresi és visszaadja a paraméterként kapott sáv melletti 
     * szomszédos sávot a megadott index (irány) alapján.
     * 
     * @param lane a szomszédos sáv
     * @param index az irány
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
        increaseSnowLevel();
    }
    
    /**
     * Hozzáadja a paraméterben átvett Lane objektumot a lanes listához.
     * 
     * @param lane az új sáv, amit hozzáad
     */
    public void addLane(Lane lane) {
        if (lane != null) {
            lanes.add(lane);
            lane.setParentRoad(this);
            lane.setSource(source);
            lane.setDestination(destination);
        }
    }

    /**
     * Visszaadja az útszakasz kiinduló csomópontját.
     * 
     * @return az útszakasz kiinduló csomópontja
     */
    public Node getSource() {
        return source;
    }

     /**
     * Beállítja az útszakasz kiinduló csomópontját.
     * 
     * @param source az új útszakasz kiinduló csomópont
     */
    public void setSource(Node source) {
        this.source = source;
    }


    /**
     * Visszaadja az útszakasz célállomását.
     * 
     * @return a célállomását jelentő csomópont
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Beállítja az útszaksz célállomását.
     * 
     * @param destination az új célállomását jelentő csomópont
     */
    public void setDestination(Node destination) {
        this.destination = destination;
    }

    public Lane getLaneAfter(Lane lane, int distance) {
        int pos = lanes.indexOf(lane);
        if (pos == -1) return null;

        int newPos = pos + distance;

        if (newPos < 0 || newPos >= lanes.size()) {
            return null;
        }

        return lanes.get(newPos);
    }
}