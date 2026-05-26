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

    /** Az útszakaszon lévő aktuális hómennyiség. */
    protected int snowLevel = 0;

    /**
     * Paraméter nélküli konstruktor.
     */
    public Road() {}

    /**
     * Visszaadja az útszakaszt felépítő sávok gyűjteményét.
     *
     * @return az útszakaszt felépítő sávok gyűjteménye
     */
    public List<Lane> getLanes() {
        return lanes;
    }

    /**
     * Növeli az útszakaszon lévő hómennyiséget eggyel.
     */
    public void increaseSnowLevel() {
        snowLevel++;
    }

    /**
     * Csökkenti az útszakaszon lévő hómennyiséget eggyel, ha az nagyobb mint nulla.
     */
    public void reduceSnowLevel() {
        if (snowLevel > 0) {
            snowLevel--;
        }
    }

    /**
     * Visszaadja az útszakaszon lévő hómennyiséget.
     *
     * @return a hómennyiség
     */
    public int getSnowLevel() {
        return snowLevel;
    }

    /**
     * Megkeresi és visszaadja a paraméterként kapott sáv melletti
     * szomszédos sávot a megadott irány alapján.
     *
     * @param lane a referencia sáv
     * @param index az irány (-1 balra, +1 jobbra)
     * @return a szomszédos sáv, vagy null, ha nincs ilyen
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
     * Az időjárás hatását alkalmazza az útszakasz sávjaira.
     * Az alapértelmezett megvalósítás növeli a hómennyiséget.
     *
     * @param weather az aktuális időjárás objektum
     */
    public void applyWeatherEffects(Weather weather) {
        increaseSnowLevel();
    }

    /**
     * Hozzáadja a paraméterben átvett Lane objektumot a sávok listájához,
     * és beállítja annak szülő útját és csomópontjait.
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
     * @param source az új kiinduló csomópont
     */
    public void setSource(Node source) {
        this.source = source;
    }

    /**
     * Visszaadja az útszakasz célállomását.
     *
     * @return a célállomást jelentő csomópont
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Beállítja az útszakasz célállomását.
     *
     * @param destination az új célállomást jelentő csomópont
     */
    public void setDestination(Node destination) {
        this.destination = destination;
    }

    /**
     * Visszaadja a megadott sáv utáni sávot a megadott távolságra.
     *
     * @param lane a referencia sáv
     * @param distance a távolság (pozitív vagy negatív irányban)
     * @return a megtalált sáv, vagy null, ha az index kívül esik a tartományon
     */
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
