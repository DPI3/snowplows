package src;

import java.util.ArrayList;
import java.util.List;

/**
 * A Game osztály a szimuláció központi vezérlője.
 * Feladata a teljes játékmenet koordinálása, a körök számának kezelése,
 * valamint a játékban szereplő járművek és játékosok nyilvántartása.
 */
public class Game {
    
    /** Az aktuális szimulációs kör sorszáma. */
    private int currentRound;
    
    /** A játék maximális időtartama körökben. */
    private int maxRound;
    
    /** A rendszerben lévő járművek listája. */
    private List<Vehicle> vehicles;
    
    /** A játékban résztvevő játékosok listája. */
    private List<Player> players;

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
     * (Itt távolítottuk el a hibás, paraméter nélküli jármű és szerepkör létrehozásokat).
     */
    public Game() {
        this.currentRound = 0;
        this.maxRound = 10;
        this.vehicles = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    /**
     * Egy egységgel előre lépteti a játékállapotot és frissíti a belső logikát.
     */
    public void tick() {
        currentRound++;
        for (Vehicle v : vehicles) {
            v.tick();
        }
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

    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }

    /**
     * Visszaadja a szimulációban résztvevő járművek listáját.
     * 
     * @return a szimulációban résztvevő járművek listája
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Visszaadja az első Snowplow típusú járművet.
     *
     * @return Snowplow objektum vagy null
     */
    public Snowplow getSnowplow() {
        for (Vehicle v : vehicles) {
            if (v instanceof Snowplow) {
                return (Snowplow) v;
            }
        }
        return null;
    }

    /**
     * Visszaadja az első Bus típusú járművet.
     *
     * @return Bus objektum vagy null
     */
    public Bus getBus() {
        for (Vehicle v : vehicles) {
            if (v instanceof Bus) {
                return (Bus) v;
            }
        }
        return null;
    }

    /**
     * Visszaadja az első játékost.
     *
     * @return Player objektum vagy null
     */
    public Player getPlayer() {
        if (players.isEmpty()) {
            return null;
        }

        return players.get(0);
    }

    /**
     * Visszaadja az aktuális kör számát.
     *
     * @return aktuális kör
     */
    public int getRound() {
        return currentRound;
    }
    
}