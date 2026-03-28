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
    public void tick() {
    }
}