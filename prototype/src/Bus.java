package prototype.src;

/**
 * A Bus osztály egy városi buszt reprezentál.
 * Két végállomás között közlekedik egy útvonal mentén.
 */
public class Bus extends Vehicle {

    /** Végállomások */
    private final Terminal terminalA;
    private final Terminal terminalB;

    /** Mennyi ideje nem tud haladni */
    private int immobileTime;

    public Bus(String id, Lane lane, double speed, Terminal a, Terminal b) {
        super(id, lane, speed);
        this.terminalA = a;
        this.terminalB = b;
    }

    /**
     * Tick során:
     * - ha járható az út → halad
     * - ha nem → várakozik
     */
    @Override
    public void tick() {
        if (currentLane == null || currentRoute == null) return;

        if (currentLane.isPassable()) {
            immobileTime = 0;
            move();
        } else {
            immobileTime++;
        }
    }

    /** Mennyi ideje áll */
    public int getImmobileTime() {
        return immobileTime;
    }
}