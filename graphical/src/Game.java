package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A Game osztály a szimuláció központi vezérlője.
 * Feladata a teljes játékmenet koordinálása, a körök számának kezelése,
 * valamint a játékban szereplő járművek és játékosok nyilvántartása.
 */
public class Game extends ModelObservable {

    /** Az aktuális szimulációs kör sorszáma. */
    private int currentRound;

    /** A játék maximális időtartama körökben. */
    private int maxRound;

    /** A rendszerben lévő járművek listája. */
    private List<Vehicle> vehicles;

    /** A játékban résztvevő játékosok listája. */
    private List<Player> players;

    private RoadNetwork roadNetwork;
    private Weather weather;
    private Store store;

    /**
     * Létrehoz egy Game objektumot a szükséges kapcsolatokkal és kezdőértékekkel.
     */
    public Game(int currentRound, int maxRound, List<Vehicle> vehicles, List<Player> players) {
        this.currentRound = currentRound;
        this.maxRound = maxRound;
        this.vehicles = vehicles;
        this.players = players;
    }

    /**
     * Paraméter nélküli konstruktor alapértelmezett értékekkel.
     */
    public Game() {
        this.currentRound = 0;
        this.maxRound = 10;
        this.vehicles = new ArrayList<>();
        this.players = new ArrayList<>();
        this.roadNetwork = new RoadNetwork();
        this.weather = new Weather();
        this.store = new Store(new ArrayList<>());
    }

    /**
     * Egy egységgel előre lépteti a játékállapotot és frissíti a belső logikát.
     */
    public void tick() {
        currentRound++;
        for (Vehicle v : vehicles) {
            v.tick();
        }
        notifyObservers();
    }

    /**
     * Megvizsgálja, hogy a szimuláció elérte-e a maximális kört, vagy véget ért-e.
     *
     * @return true, ha a játék véget ért; false, ha a játék nem ért véget
     */
    public boolean isOver() {
        return currentRound >= maxRound;
    }

    /**
     * Lezárja a játékot.
     */
    public void end() {
    }

    /**
     * Az aktuális kör számát adja vissza, teszteléshez szükséges.
     *
     * @return aktuális kör száma
     */
    public int getCurrentRound(){
        return currentRound;
    }

    /**
     * A játékosok listáját adja vissza, teszteléshez szükséges.
     *
     * @return játékosok listája
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * Visszaadja a maximális körök számát.
     *
     * @return a maximális körök száma
     */
    public int getMaxRound() {
        return maxRound;
    }

    /**
     * Visszaadja a szimulációban résztvevő járművek listáját.
     *
     * @return a szimulációban résztvevő járművek listája
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    public void setRoadNetwork(RoadNetwork rn) {
        this.roadNetwork = rn;
    }

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather w) {
        this.weather = w;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store s) {
        this.store = s;
    }

    public void addVehicle(Vehicle v) {
        vehicles.add(v);
    }

    public void addPlayer(Player p) {
        players.add(p);
    }
}
