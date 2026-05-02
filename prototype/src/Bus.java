package src;

/**
 * A Bus osztály a városi buszjáratot reprezentálja.
 * A busz két végállomás között közlekedik, és egy előre kijelölt útvonal mentén halad.
 * Feladata a közlekedés lebonyolítása, a végállomások elérése, valamint a körök végrehajtása a
 * szimulációban. A busz működése összekapcsolódik a BusDriverRole szereppel, amely az útvonal
 * kijelöléséért és a teljesített fordulók nyilvántartásáért felel.
 */
public class Bus extends Vehicle {

    /** A busz első végállomása. */
    private Terminal terminalA;

    /** A busz második végállomása. */
    private Terminal terminalB;

    /** Azt méri, hogy a busz mennyi ideje nem tud haladni. */
    private int immobileTime;

    /** A busz jelenlegi állapota. */
    private String location = "úton";

    public Bus(String id, Lane lane, double speed, Terminal a, Terminal b) {
        super(id, lane, speed);
        this.terminalA = a;
        this.terminalB = b;
    }

    /**
     * Visszaadja a busz első állomását.
     * 
     * @return a busz első végállomása
     */
    public Terminal getTerminal_A() { return terminalA; }

    /**
     * Visszaadja a busz második állomását.
     * 
     * @return a busz második végállomása
     */
    public Terminal getTerminal_B() { return terminalB; }

    /**
     * Visszaadja, hogy a busz mennyi ideig nem tud haladni
     * 
     * @return a busz mozgásképtelenségének ideje
     */
    public int getImmobileTime()    { return immobileTime; }

    /**
     * A busz állapotával szöveges formában visszatérő metódus
     * 
     * @return a busz állapota
     */
    public String getLocation()     { return location; }

    /**
     * A busz állapotának beállítása
     * 
     * @param location a busz új állapota
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * A szimuláció egy időlépését hajtja végre.
     * Ha a busz sávja járható, a busz továbbhalad és az immobileTime nullázódik.
     * Ha nem járható, az immobileTime növekszik.
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
}
