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
    private Node terminalA;

    /**
     * A másik végállomás.
     */
    private Node terminalB;

    /**
     * A mozgásképtelenség időtartama (tick-ekben).
     */
    private int immobileTime;

    /**
     * A busz aktuális útvonala.
     */
    private Route currentRoute;

    public Bus() {
        super(); // Ez hívja a Vehicle() üres konstruktorát
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
               Node terminalA, Node terminalB, int immobileTime, Route currentRoute) {
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
     * egyébként mozog.
     */
    @Override
    public void tick(Scanner scanner) {
        // 1. Logoljuk a tick hívást
        Skeleton.printCall("Bus", "tick()");

        // 2. Ellenőrizzük, hogy a busz mozgásképtelen-e (immobileTime > 0) [cite: 1324]
        if (immobileTime > 0) {
            // Ha várakozik, csökkentjük az időt [cite: 1334-1336]
            immobileTime--;
            Skeleton.printState("A busz nem mozdul, immobileTime csökken eggyel. Maradt: " + immobileTime);
        } else {
            // 3. Ha már nem várakozik, meghívjuk a move-ot [cite: 1342]
            this.move(scanner);
        }

        // 4. Logoljuk a visszatérést
        Skeleton.printReturn("");
    }

    @Override
    public void move(Scanner scanner) {
        Skeleton.printCall("Bus", "move()");

        // 1. Következő sáv lekérése [cite: 3090-3091]
        Skeleton.printCall("Route", "getNextLane(currentLane)");
        Skeleton.printReturn("nextLane");

        // 2. Járhatóság ellenőrzése döntési ponttal [cite: 3092-3093]
        int passableInput = Skeleton.requestInput(scanner, "A sáv járható? (1: Igen, 2: Nem)");
        
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

            int adjPassableInput = Skeleton.requestInput(scanner, "A szomszédos sáv járható? (1: Igen, 2: Nem)");
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
        int accidentInput = Skeleton.requestInput(scanner, "Baleset történt? (1: Igen, 2: Nem)");
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
}