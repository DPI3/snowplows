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

    /**
     * Üres konstruktor a skeleton célokra.
     */
    public Bus() {
        super();
        Skeleton.printCall("Bus", "Bus()");
        Skeleton.printReturn("");
    }
    /**
     * Beállítja a busz aktuális útvonalát.
     * @param route a beállítandó útvonal
     */
    public void setCurrentRoute(Route route) {
        this.currentRoute = route;
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

        // 1. Következő sáv lekérése [cite: 3090-3091]
        Skeleton.printCall("Route", "getNextLane(currentLane)");
        Skeleton.printReturn("nextLane");

        // 2. Járhatóság ellenőrzése döntési ponttal [cite: 3092-3093]
        int passableInput = Skeleton.requestInput("A sáv járható? (1: Igen, 2: Nem)");
        
        if (passableInput == 1) {
            Skeleton.printCall("Lane", "isPassable()");
            Skeleton.printReturn("true");
            Skeleton.printState("update position"); // [cite: 3099]
        } else {
            Skeleton.printCall("Lane", "isPassable()");
            Skeleton.printReturn("false");

            // Sávváltási kísérlet akadály esetén [cite: 3101-3104]
            Skeleton.printCall("Road", "getAdjacentLane(currentLane, index)");
            Skeleton.printReturn("adjLane");

            int adjPassableInput = Skeleton.requestInput("A szomszédos sáv járható? (1: Igen, 2: Nem)");
            if (adjPassableInput == 1) {
                Skeleton.printCall("Lane", "isPassable()");
                Skeleton.printReturn("true");
                Skeleton.printState("change lanes and move forward"); // [cite: 3107]
            } else {
                Skeleton.printCall("Lane", "isPassable()");
                Skeleton.printReturn("false");
                Skeleton.printState("wait (speed = 0)"); // [cite: 3109]
            }
        }

        // 3. Baleset ellenőrzése [cite: 3111-3113]
        int accidentInput = Skeleton.requestInput("Baleset történt? (1: Igen, 2: Nem)");
        if (accidentInput == 1) {
            Skeleton.printCall("Lane", "hasAccident()");
            Skeleton.printReturn("true");
            Skeleton.printState("activate immobileTime"); // [cite: 3114]
        } else {
            Skeleton.printCall("Lane", "hasAccident()");
            Skeleton.printReturn("false");
        }

        Skeleton.printReturn("");
    }

    public int getImmobileTime() {
        return immobileTime;
    }
}
