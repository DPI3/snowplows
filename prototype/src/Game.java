package src;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
        this.maxRound = 10; // Tetszőleges alapértelmezett érték
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
     */
    public boolean isOver() {
        return currentRound >= maxRound; 
    }

    /**
     * Lezárja a játékot.
     */
    public void end() {
        // Ide kerülhet a játék végét kezelő logika
    }
}