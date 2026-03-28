package skeleton.src;

/**
 * A Snowplow osztály egy hókotró járművet reprezentál.
 *
 * A hókotró képes különböző típusú fejekkel tisztítani az utakat,
 * valamint nyersanyagokat (só, biokerozin) használ a működéshez.
 *
 * A Snowplow a Vehicle osztályból származik,
 * és megvalósítja a Buyable interfészt.
 */
public class Snowplow extends Vehicle implements Buyable {

    /**
     * Az aktuálisan felszerelt kotrófej.
     */
    private Head currentHead;

    /**
     * A rendelkezésre álló só mennyisége.
     */
    private int saltStock;

    /**
     * A rendelkezésre álló biokerozin mennyisége.
     */
    private int biokeroseneStock;

    /**
     * A hókotró működési módja / szerepe.
     */
    private CleanerRole cleanerRole;

    /**
     * Konstruktor a Snowplow objektum létrehozásához.
     *
     * @param id azonosító
     * @param currentLane aktuális sáv
     * @param positionOnLane pozíció
     * @param speed sebesség
     * @param currentHead aktuális fej
     * @param saltStock só készlet
     * @param biokeroseneStock biokerozin készlet
     * @param cleanerRole működési mód
     */
    public Snowplow(String id, Lane currentLane, double positionOnLane, double speed,
                    Head currentHead, int saltStock, int biokeroseneStock,
                    CleanerRole cleanerRole) {
        super(id, currentLane, positionOnLane, speed);
        this.currentHead = currentHead;
        this.saltStock = saltStock;
        this.biokeroseneStock = biokeroseneStock;
        this.cleanerRole = cleanerRole;
    }

    /**
     * A kotrófej cseréje.
     *
     * @param newHead az új fej
     */
    public void changeHead(Head newHead) {
    }

    /**
     * Tisztítás végrehajtása egy adott sávon.
     *
     * @param lane a tisztítandó sáv
     */
    public void clean(Lane lane) {
    }

    /**
     * A hókotró ára.
     *
     * @return az ár (szkeleton esetben 0)
     */
    @Override
    public int getPrice() {
        return 0;
    }
}