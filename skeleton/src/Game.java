package skeleton.src;

import java.util.List;

/**
 * A Game osztály a szimuláció központi vezérlője.
 *
 * Feladata a teljes játékmenet koordinálása, a körök számának kezelése,
 * valamint a játékban szereplő járművek és játékosok nyilvántartása.
 * A Game felelős a játéklogika léptetéséért és a befejezési feltételek
 * ellenőrzéséért.
 */
public class Game {

    /**
     * Az aktuális szimulációs kör sorszáma.
     */
    private int currentRound;

    /**
     * A játék maximális időtartama körökben.
     */
    private int maxRound;

    /**
     * A rendszerben lévő járművek listája.
     */
    private List<Vehicle> vehicles;

    /**
     * A játékban résztvevő játékosok listája.
     */
    private List<Player> players;


    /**
     * Létrehoz egy Game objektumot a szükséges kapcsolatokkal és kezdőértékekkel.
     *
     * @param currentRound az aktuális kör száma
     * @param maxRound a maximális körszám
     * @param vehicles a járművek listája
     * @param players a játékosok listája
     */
    public Game(int currentRound, int maxRound, List<Vehicle> vehicles, List<Player> players) {
        this.currentRound = currentRound;
        this.maxRound = maxRound;
        this.vehicles = vehicles;
        this.players = players;
    }

    /**
     * Egy egységgel előre lépteti a játék állapotát és frissíti a belső logikát.
     *
     * A szkeleton implementációban csak a metódushívás kerül naplózásra.
     */
    public void tick() {
    }

    /**
     * Megvizsgálja, hogy a szimuláció elérte-e a maximális körszámot
     * vagy véget ért-e.
     */
    public boolean isOver() {
        return false; // ideiglenes
    }
}