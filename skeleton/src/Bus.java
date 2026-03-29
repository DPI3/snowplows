package skeleton.src;

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
            move();
        }

        Skeleton.printReturn("");
    }

    /**
     * A busz mozgását hajtja végre.
     */
    @Override
    public void move() {
        Skeleton.printCall("Bus", "move()");
        Skeleton.printState("A busz a currentRoute alapján megpróbál továbbhaladni.");
        Skeleton.printReturn("");
    }
}