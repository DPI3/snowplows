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

    /** A busz letiltott tick-jeinek száma. */
    private int disabledTicks;

    /**
     * Új Bus objektum létrehozása a megadott paraméterekkel.
     *
     * @param id a busz egyedi azonosítója
     * @param lane a busz induló sávja
     * @param speed a busz sebessége
     * @param a a busz első végállomása
     * @param b a busz második végállomása
     */
    public Bus(String id, Lane lane, double speed, Terminal a, Terminal b) {
        super(id, lane, speed);
        this.terminalA = a;
        this.terminalB = b;
    }

    /**
     * Visszaadja a busz első végállomását.
     *
     * @return a busz első végállomása
     */
    public Terminal getTerminal_A() { return terminalA; }

    /**
     * Visszaadja a busz második végállomását.
     *
     * @return a busz második végállomása
     */
    public Terminal getTerminal_B() { return terminalB; }

    /**
     * Visszaadja, hogy a busz mennyi ideig nem tud haladni.
     *
     * @return a busz mozgásképtelenségének ideje tick-ekben
     */
    public int getImmobileTime()    { return immobileTime; }

    /**
     * Visszaadja a busz jelenlegi állapotát szöveges formában.
     *
     * @return a busz állapota
     */
    public String getLocation()     { return location; }

    /**
     * Beállítja a busz állapotát.
     *
     * @param location a busz új állapota
     */
    public void setLocation(String location) { this.location = location; }

    /**
     * A szimuláció egy időlépését hajtja végre.
     * Ha a busz le van tiltva, a letiltott tick-ek száma csökken és az immobileTime nő.
     * Ha a busz sávja járható, a busz továbbhalad és az immobileTime nullázódik.
     * Ha nem járható, az immobileTime növekszik.
     */
    @Override
    public void tick() {
        if (disabledTicks > 0) {
            disabledTicks--;
            immobileTime++;

            if (disabledTicks == 0) {
                location = "úton";
            }

            return;
        }

        if (currentLane == null) return;

        if (currentLane.isPassable()) {
            immobileTime = 0;
            move();
        } else {
            immobileTime++;
        }
    }

    /**
     * Letiltja a buszt a megadott számú tick-re.
     * Ha a busz már le van tiltva, a nagyobb értéket tartja meg.
     *
     * @param ticks a letiltás időtartama tick-ekben
     */
    public void disableForTicks(int ticks) {
        this.disabledTicks = Math.max(this.disabledTicks, ticks);
        this.location = "mozgásképtelen";
    }
}
