package src;

import java.util.ArrayList;
import java.util.List;

/**
 * A Route osztály egy konkrét útvonaltervet reprezentál, amelyet az úthálózat útvonalkereső algoritmusa állít
 * elő a járművek számára. Feladata a célba jutáshoz szükséges sávok rendezett listájának tárolása.
 * A szimuláció során ez az osztály felelős azért, hogy a jármű haladása közben kiszolgálja a
 * következő sávot, amelyre a járműnek rá kell hajtania.
 */
public class Route {

    /** Az útvonal neve. */
    private String name;

    /** A jutalom. */
    private int reward;

    /** Az útvonalat felépítő sávok rendezett sorozata. */
    private List<Lane> lanes = new ArrayList<>();

    /**
     * Új üres Route objektum létrehozása alapértelmezett beállításokkal.
     */
    public Route() {}

    /**
     * Új útvonal létrehozása, név megadásával.
     *
     * @param name az útvonal neve
     */
    public Route(String name) {
        this.name = name;
    }

    /**
     * Új útvonal létrehozása, név és jutalom megadásával.
     *
     * @param name az útvonal neve
     * @param reward a jutalom
     */
    public Route(String name, int reward) {
        this.name = name;
        this.reward = reward;
    }

    /**
     * Visszaadja az útvonal nevét.
     *
     * @return az útvonal neve
     */
    public String getName()   { return name; }

    /**
     * Visszaadja a jutalmat.
     *
     * @return a jutalom
     */
    public int    getReward() { return reward; }

    /**
     * Megkeresi a paraméterként kapott aktuális sávot az útvonalban, és visszaadja a soron következő sáv
     * objektumot. Ezzel irányítja a járművet az útvonal mentén.
     *
     * @param curr az aktuális sáv
     * @return a következő sáv, vagy null, ha nincs több sáv
     */
    public Lane getNextLane(Lane curr) {
        int idx = lanes.indexOf(curr);
        if (idx == -1 || idx == lanes.size() - 1) return null;
        return lanes.get(idx + 1);
    }

    /**
     * Visszaadja az útvonal hosszát, ehhez összesíti a benne szereplő sávok hosszát.
     *
     * @return az útvonal hossza
     */
    public double getLength() {
        double sum = 0;
        for (Lane lane : lanes) sum += lane.getLength();
        return sum;
    }

    /**
     * Visszaadja a jármű által bejárandó sávok listáját.
     *
     * @return a jármű által bejárandó sávok listája
     */
    public List<Lane> getLanes() { return lanes; }

    /**
     * Új sáv objektumot ad az útvonalhoz.
     *
     * @param lane az új sáv
     */
    public void addLane(Lane lane) { this.lanes.add(lane); }
}
