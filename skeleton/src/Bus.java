package skeleton.src;

import java.util.Scanner;

/**
 * A Bus osztály egy tömegközlekedési járművet reprezentál.
 *
 * A Bus meghatározott útvonalon közlekedik két végállomás között.
 * Bizonyos esetekben (pl. megállóban) ideiglenesen mozgásképtelenné válhat.
 *
 * A Bus a Vehicle osztályból származik.
 */
public class Bus extends Vehicle {

    /**
     * Az egyik végállomás.
     */
    private Terminal terminalA;

    /**
     * A másik végállomás.
     */
    private Terminal terminalB;

    /**
     * A mozgásképtelenség időtartama (tick-ekben).
     */
    private int immobileTime;

    /**
     * A busz aktuális útvonala.
     */
    private Route currentRoute;

    /** A buszhoz rendelt sofőr szerepkör. */
    private BusdriverRole driver;

    /**
     * Üres konstruktor a skeleton célokra.
     */
    public Bus() {
        super();
        Skeleton.printCall("Bus", "Bus()");
        Skeleton.printReturn("");
    }
    /** Beállítja a busz aktuális útvonalát és naplózza a hívást. */
    public void setCurrentRoute(Route route) {
        Skeleton.printCall("Bus", "setCurrentRoute(r)");
        this.currentRoute = route;
        Skeleton.printReturn("");
    }

    /** Beállítja a buszhoz a sofőrt. */
    public void setDriver(BusdriverRole driver) {
        this.driver = driver;
    }

    /** Ellenőrzi a végállomás elérését a szekvenciadiagram alapján. */
    public boolean checkTerminalReached() {
        Skeleton.printCall("Bus", "checkTerminalReached()");
        Skeleton.printReturn("true");
        return true;
    }

    /**
     * Konstruktor a Bus objektum létrehozásához.
     *
     * @param id azonosító
     * @param currentLane aktuális sáv
     * @param positionOnLane pozíció
     * @param speed sebesség
     * @param terminalA első végállomás
     * @param terminalB második végállomás
     * @param immobileTime várakozási idő
     * @param currentRoute aktuális útvonal
     */
    public Bus(String id, Lane currentLane, double positionOnLane, double speed,
               Terminal terminalA, Terminal terminalB, int immobileTime, Route currentRoute) {
        super(id, currentLane, positionOnLane, speed);
        this.terminalA = terminalA;
        this.terminalB = terminalB;
        this.immobileTime = immobileTime;
        this.currentRoute = currentRoute;
    }

    /**
     * Egy szimulációs lépést hajt végre.
     *
     * Ha a busz mozgásképtelen, csökkenti az időzítőt,
     * különben meghívja a move() metódust.
     */
    @Override
    public void tick() {
        Skeleton.printCall("Bus", "tick()");

        if (immobileTime > 0) {
            immobileTime--;
            Skeleton.printState("A busz várakozik, immobileTime csökkent: " + immobileTime);
        } else {
            this.move();
        }

        Skeleton.printReturn("");
    }

    /**
     * A busz mozgását hajtja végre.
     */
    
    @Override
    public void move() {
        Skeleton.printCall("Bus", "move()");

        if (this.currentRoute == null) {
            if (this.checkTerminalReached()) {
                if (this.driver != null) {
                    this.driver.incrementCompletedRounds(); //
                }
                
                RoadNetwork rn = new RoadNetwork();
                // Új útvonal kérése a hálózattól az UML paraméterekkel
                Route r = rn.getShortestPath(terminalB, terminalA);
                
                this.setCurrentRoute(r);
                this.move(); // Rekurzív hívás a szekvenciadiagram szerint
                
                Skeleton.printReturn("");
                return;
            }
        }

        Skeleton.printCall("Route", "getNextLane(currentLane)");
        Skeleton.printReturn("nextLane");

        // Járhatóság ellenőrzése (Input bekérése a teszthez)
        int passableInput = Skeleton.requestInput("A sáv járható? (1: Igen, 2: Nem)");
        
        if (passableInput == 1) {
            Skeleton.printCall("Lane", "isPassable()");
            Skeleton.printReturn("true");
            
            // Pozíció frissítése a haladáshoz
            Skeleton.printState("update position");

            // Baleset ellenőrzése (Csak ha a sáv járható volt)
            int accidentInput = Skeleton.requestInput("Baleset történt? (1: Igen, 2: Nem)");
            if (accidentInput == 1) {
                Skeleton.printCall("Lane", "hasAccident()");
                Skeleton.printReturn("true");
                Skeleton.printState("Bus waits due to accident");
            } else {
                Skeleton.printCall("Lane", "hasAccident()");
                Skeleton.printReturn("false");
            }
        } else {
            // Ha nem járható (pl. nagy hó), a busz megáll
            Skeleton.printCall("Lane", "isPassable()");
            Skeleton.printReturn("false");
            Skeleton.printState("Bus stopped (Lane impassable)");
        }

        Skeleton.printReturn("");
    }

    public int getImmobileTime() {
        return immobileTime;
    }
}
