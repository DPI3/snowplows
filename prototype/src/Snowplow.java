package prototype.src;

/**
 * A Snowplow osztály egy hókotrót reprezentál.
 * Különböző fejekkel képes tisztítani az utakat.
 */
public class Snowplow extends Vehicle {

    /** Aktuális fej */
    private Head currentHead;

    /** Készletek */
    private int saltStock;
    private int biokeroseneStock;
    private int gravelStock;

    public Snowplow(String id, Lane lane, double speed, Head head) {
        super(id, lane, speed);
        this.currentHead = head;
    }

    /**
     * Fej cseréje
     */
    public void changeHead(Head newHead) {
        this.currentHead = newHead;
    }

    /**
     * Takarítás végrehajtása
     */
    public void clean(Lane lane) {
        if (currentHead == null || lane == null) return;

        currentHead.clean(lane, this);
    }

    /** Készletkezelés */
    public void addSalt(int amount) { saltStock += amount; }
    public void consumeSalt(int amount) { saltStock = Math.max(0, saltStock - amount); }

    public void addBiokerosene(int amount) { biokeroseneStock += amount; }
    public void consumeBiokerosene(int amount) { biokeroseneStock = Math.max(0, biokeroseneStock - amount); }

    public void addGravel(int amount) { gravelStock += amount; }
    public void consumeGravel(int amount) { gravelStock = Math.max(0, gravelStock - amount); }

    /** Getterek */
    public int getSaltStock() { return saltStock; }
    public int getBiokeroseneStock() { return biokeroseneStock; }
    public int getGravelStock() { return gravelStock; }
}