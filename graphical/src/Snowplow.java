package src;

/**
 * A Snowplow osztály egy hókotrót reprezentál.
 * Különböző fejekkel képes tisztítani az utakat.
 */
public class Snowplow extends Vehicle {
    /**
     * A hókotró lehetséges állapotait definiáló felsorolás.
     */
    public enum PlowState {
        AT_TERMINAL, READY_TO_CLEAN, OUT_OF_FUEL
    }

    /** A hókotró aktuális tisztítófeje. */
    private Head currentHead;

    /** A hókotró sókészlete. */
    private int saltStock;

    /** A hókotró biokerozin-készlete. */
    private int biokeroseneStock;

    /** A hókotró zúzottkő-készlete. */
    private int gravelStock;

    /** A hókotró üzemanyagszintje. */
    private int fuel;

    /** A hókotró aktuális állapota. */
    private PlowState state;

    /** Az a sáv, amely felé a hókotró néz. */
    private Lane facingLane;

    /**
     * Visszaadja a hókotró aktuális üzemanyagszintjét.
     *
     * @return az üzemanyagszint
     */
    public int getFuel() { return fuel; }

    /**
     * Csökkenti a hókotró üzemanyagszintjét a megadott mennyiséggel.
     *
     * @param amount a felhasználandó üzemanyag mennyisége
     */
    public void consumeFuel(int amount) {
        this.fuel = Math.max(0, this.fuel - amount);
    }

    /**
     * Visszaadja a hókotró aktuális állapotát.
     *
     * @return az aktuális állapot
     */
    public PlowState getState() { return state; }

    /**
     * Beállítja a hókotró állapotát.
     *
     * @param state az új állapot
     */
    public void setState(PlowState state) { this.state = state; }

    /**
     * Snowplow példány létrehozása.
     *
     * @param id a hókotró egyedi azonosítója
     * @param lane a kezdő sáv
     * @param speed a hókotró sebessége
     * @param head a hókotró kezdő tisztítófeje
     */
    public Snowplow(String id, Lane lane, double speed, Head head) {
        super(id, lane, speed);
        this.currentHead = head;

        this.fuel = 100;
        this.state = PlowState.AT_TERMINAL;
    }

    /**
     * Beállítja a hókotró aktuális sávját. Ha a sáv nem terminál,
     * az állapotot READY_TO_CLEAN-re állítja.
     *
     * @param lane az új aktuális sáv
     */
    @Override
    public void setCurrentLane(Lane lane) {
        super.setCurrentLane(lane);
        if (lane != null && !lane.getName().startsWith("Terminal")) {
            this.state = PlowState.READY_TO_CLEAN;
        }
    }

    /**
     * A hókotró fejének cseréje egy új fejre.
     *
     * @param newHead az új tisztítófej
     */
    public void changeHead(Head newHead) {
        this.currentHead = newHead;
    }

    /**
     * Takarítás végrehajtása a megadott sávon az aktuális fejjel.
     *
     * @param lane a takarítandó sáv
     */
    public void clean(Lane lane) {
        if (currentHead == null || lane == null) return;

        currentHead.clean(lane, this);
    }

    /**
     * Visszaadja a hókotró által nézett sávot.
     *
     * @return a nézett sáv
     */
    public Lane getFacingLane() {
        return facingLane;
    }

    /**
     * Beállítja a hókotró által nézett sávot.
     *
     * @param facingLane a nézett sáv
     */
    public void setFacingLane(Lane facingLane) {
        this.facingLane = facingLane;
    }

    /**
     * Növeli a hókotró sókészletét a megadott mennyiséggel, maximum 100-ig.
     *
     * @param amount a hozzáadandó só mennyisége
     */
    public void addSalt(int amount) {
        saltStock = Math.min(100, saltStock + amount);
    }

    /**
     * Csökkenti a hókotró sókészletét a megadott mennyiséggel, minimum 0-ig.
     *
     * @param amount a felhasználandó só mennyisége
     */
    public void consumeSalt(int amount) { saltStock = Math.max(0, saltStock - amount); }

    /**
     * Növeli a hókotró biokerozin-készletét a megadott mennyiséggel, maximum 100-ig.
     *
     * @param amount a hozzáadandó biokerozin mennyisége
     */
    public void addBiokerosene(int amount) {
        biokeroseneStock = Math.min(100, biokeroseneStock + amount);
    }

    /**
     * Csökkenti a hókotró biokerozin-készletét a megadott mennyiséggel, minimum 0-ig.
     *
     * @param amount a felhasználandó biokerozin mennyisége
     */
    public void consumeBiokerosene(int amount) { biokeroseneStock = Math.max(0, biokeroseneStock - amount); }

    /**
     * Növeli a hókotró zúzottkő-készletét a megadott mennyiséggel, maximum 100-ig.
     *
     * @param amount a hozzáadandó zúzottkő mennyisége
     */
    public void addGravel(int amount) {
        gravelStock = Math.min(100, gravelStock + amount);
    }

    /**
     * Csökkenti a hókotró zúzottkő-készletét a megadott mennyiséggel, minimum 0-ig.
     *
     * @param amount a felhasználandó zúzottkő mennyisége
     */
    public void consumeGravel(int amount) { gravelStock = Math.max(0, gravelStock - amount); }

    /**
     * Visszaadja a hókotró aktuális tisztítófejét.
     *
     * @return az aktuális fej
     */
    public Head getCurrentHead() { return currentHead; }

    /**
     * Visszaadja a hókotró sókészletét.
     *
     * @return a sókészlet mennyisége
     */
    public int getSaltStock() { return saltStock; }

    /**
     * Visszaadja a hókotró biokerozin-készletét.
     *
     * @return a biokerozin-készlet mennyisége
     */
    public int getBiokeroseneStock() { return biokeroseneStock; }

    /**
     * Visszaadja a hókotró zúzottkő-készletét.
     *
     * @return a zúzottkő-készlet mennyisége
     */
    public int getGravelStock() { return gravelStock; }

    /**
     * Beállítja a hókotró sókészletét közvetlenül.
     *
     * @param stock az új sókészlet értéke
     */
    public void setSaltStock(int stock) { this.saltStock = stock; }

    /**
     * Beállítja a hókotró biokerozin-készletét közvetlenül.
     *
     * @param stock az új biokerozin-készlet értéke
     */
    public void setBiokeroseneStock(int stock) { this.biokeroseneStock = stock; }

    /**
     * Beállítja a hókotró zúzottkő-készletét közvetlenül.
     *
     * @param stock az új zúzottkő-készlet értéke
     */
    public void setGravelStock(int stock) { this.gravelStock = stock; }
}
