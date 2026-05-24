package src;

/**
 * A CleanerRole a takarító szerepkört reprezentálja.
 * Felelős a hó eltávolításáért, a hókotrók irányításáért
 * és a takarításhoz kapcsolódó gazdasági döntésekért.
 * Emellett kezeli a játékos pénzét, vásárolhat, és működteti a hókotrók fejeit.
 */
public class CleanerRole extends Role {

    /** A takarító jelenlegi pénzmennyisége. */
    private int money;

    /** A takarítóhoz tartozó hókotró. */
    private Snowplow snowplow;

    /** A takarító neve.  */
    private String name;

    private java.util.List<Head> ownedHeads = new java.util.ArrayList<>();

     /** A  CleanerRole objektum konstruktora
     * 
     * @param name a takarító neve
     * @param money a takarító kezdeti pénze
     * @param snowplow a buszvezető által mozgatandó hókotró
    */
    public CleanerRole(String name, int money, Snowplow snowplow) {
        this.name=name;
        this.money = money;
        this.snowplow=snowplow;

        if (snowplow != null && snowplow.getCurrentHead() != null) {
            ownedHeads.add(snowplow.getCurrentHead());
        }
    }

    public boolean buy(Role role, Buyable item) {
        return false;
    }

    /**
     * Vásárlási művelet a Store-ban. A takarító megvásárolhat hókotrót, kotrófejet vagy nyersanyagot.
     *
     * @param store a bolt
     * @param item a megvásárolni kívánt elem
     * @return true, ha a vásárlás sikeres
     */
    public boolean buy(Store store, Buyable item) {
        boolean result = store.buy(this, item);
        return result;
    }

    /**
     * A metódus megadja a takarító aktuális pénzét.
     *
     * @return a szerepkör pénze
     */
    public int getMoney() {
        return money;
    }

    /**
     * A takarító neve.
     */
    public String getName() {
        return name;
    }

    /**
     * A metódus növeli a takarító pénzét.
     *
     * @param amount a növelés mennyisége
     */
    public void changeMoney(int amount) {
        money += amount;
    }

    /**
     * A metódus csökkenti a takarító pénzét.
     *
     * @param amount a csökkentés mennyisége
     */
    public void decreaseMoney(int price) {
        money -= price;
    }

    /**
     * A metódus növeli a takarító által irányított hókotró só készletét.
     *
     * @param amount a növelés mennyisége
     */
    public void addSalt(int amount) {
        snowplow.addSalt(amount);
    }

    /**
     * A metódus növeli a takarító által irányított hókotró zúzottkő készletét.
     *
     * @param amount a növelés mennyisége
     */
    public void addGravel(int amount) {
        snowplow.addGravel(amount);
    }

    
    /**
     * A metódus növeli a takarító által irányított hókotró biokerozin készletét.
     *
     * @param amount a növelés mennyisége
     */
    public void addBiokerosene(int amount) {
        snowplow.addBiokerosene(amount);
    }

    /**
     * A metódus megadja a takarító által irányított hókotrót.
     *
     * @return a hókotró
     */
    public Snowplow getSnowplow() {
        return snowplow;
    }

    /**
     * A metódus új fejet ad a takarító által irányított hókotrónak.
     *
     * @param newHead a takarító által irányított hókotró új feje
     */
    public void addHead(Head newHead) {
        addOwnedHead(newHead);
        snowplow.changeHead(newHead);
    }

    /**
     * A takarító irányítja a megadott hókotró
     *
     * @param sp a vezérelni kívánt hókotró
     */
    public void controlSnowplow(Snowplow sp) {
        if (sp == null) return;

        Lane lane = sp.getCurrentLane();
        if (lane == null) return;

        LaneState before = lane.getLaneState();

        boolean wasDirty =
                before instanceof ThinSnow ||
                before instanceof DeepSnow ||
                before instanceof IceSheet ||
                before instanceof BrokenIce ||
                before instanceof Gravel;

        sp.clean(lane);

        LaneState after = lane.getLaneState();

        boolean becameClean =
                after instanceof Clear ||
                after instanceof Gravel;

        if (wasDirty && becameClean && before.getClass() != after.getClass()) {
            money += 50;
        }
    }

    /**
     * A metódus megadja a takarító által szerzett pontszámot.
     *
     * @return a szerepkör pontszáma
     */
    @Override
    public int getScore() {
        return money;
    }

    public java.util.List<Head> getOwnedHeads() {
        return ownedHeads;
    }

    public void addOwnedHead(Head head) {
        if (head != null) {
            ownedHeads.add(head);
        }
    }
}
