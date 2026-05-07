package src;

/**
 * A Snowplow osztály egy hókotrót reprezentál.
 * Különböző fejekkel képes tisztítani az utakat.
 */
public class Snowplow extends Vehicle {
    public enum PlowState {
        AT_TERMINAL, READY_TO_CLEAN, OUT_OF_FUEL
    }

    /** Aktuális fej */
    private Head currentHead;

    /** Készletek */
    private int saltStock;
    private int biokeroseneStock;
    private int gravelStock;
    private int fuel;
    private PlowState state;

    /**
     * Fuel getter
     */
    public int getFuel() { return fuel; }
    public void consumeFuel(int amount) { 
        this.fuel = Math.max(0, this.fuel - amount); 
    }

    public PlowState getState() { return state; }
    public void setState(PlowState state) { this.state = state; }

    public Snowplow(String id, Lane lane, double speed, Head head) {
        super(id, lane, speed);
        this.currentHead = head;

        this.fuel = 100; 
        this.state = PlowState.AT_TERMINAL;
    }

    @Override
    public void setCurrentLane(Lane lane) {
        super.setCurrentLane(lane);
        if (lane != null && !lane.getName().startsWith("Terminal")) {
            this.state = PlowState.READY_TO_CLEAN;
        }
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
    public Head getCurrentHead() { return currentHead; }
    public int getSaltStock() { return saltStock; }
    public int getBiokeroseneStock() { return biokeroseneStock; }
    public int getGravelStock() { return gravelStock; }

    /** Készlet közvetlen beállítása (teszteléshez). */
    public void setSaltStock(int stock) { this.saltStock = stock; }
    public void setBiokeroseneStock(int stock) { this.biokeroseneStock = stock; }
    public void setGravelStock(int stock) { this.gravelStock = stock; }
}