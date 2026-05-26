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

    /** A takarító neve. */
    private String name;

    /** A takarító által birtokolt kotrófejeinek listája. */
    private java.util.List<Head> ownedHeads = new java.util.ArrayList<>();

    /**
     * A CleanerRole objektum konstruktora.
     *
     * @param name a takarító neve
     * @param money a takarító kezdeti pénze
     * @param snowplow a takarító által irányított hókotró
     */
    public CleanerRole(String name, int money, Snowplow snowplow) {
        this.name=name;
        this.money = money;
        this.snowplow=snowplow;

        if (snowplow != null && snowplow.getCurrentHead() != null) {
            ownedHeads.add(snowplow.getCurrentHead());
        }
    }

    /**
     * Vásárlási művelet egy szerepkörrel. Jelenleg nem támogatott.
     *
     * @param role a szerepkör
     * @param item a megvásárolni kívánt elem
     * @return false, mivel ez a vásárlási mód nem támogatott
     */
    public boolean buy(Role role, Buyable item) {
        return false;
    }

    /**
     * Vásárlási művelet a Store-ban. A takarító megvásárolhat hókotrót, kotrófejet vagy nyersanyagot.
     *
     * @param store a bolt
     * @param item a megvásárolni kívánt elem
     * @return true, ha a vásárlás sikeres; false egyébként
     */
    public boolean buy(Store store, Buyable item) {
        boolean result = store.buy(this, item);
        return result;
    }

    /**
     * Visszaadja a takarító aktuális pénzmennyiségét.
     *
     * @return a szerepkör pénze
     */
    public int getMoney() {
        return money;
    }

    /**
     * Visszaadja a takarító nevét.
     *
     * @return a takarító neve
     */
    public String getName() {
        return name;
    }

    /**
     * Növeli a takarító pénzmennyiségét a megadott összeggel.
     *
     * @param amount a növelés mennyisége
     */
    public void changeMoney(int amount) {
        money += amount;
    }

    /**
     * Csökkenti a takarító pénzmennyiségét a megadott összeggel.
     *
     * @param price a csökkentés mennyisége
     */
    public void decreaseMoney(int price) {
        money -= price;
    }

    /**
     * Növeli a takarító által irányított hókotró só készletét.
     *
     * @param amount a növelés mennyisége
     */
    public void addSalt(int amount) {
        snowplow.addSalt(amount);
    }

    /**
     * Növeli a takarító által irányított hókotró zúzottkő készletét.
     *
     * @param amount a növelés mennyisége
     */
    public void addGravel(int amount) {
        snowplow.addGravel(amount);
    }

    /**
     * Növeli a takarító által irányított hókotró biokerozin készletét.
     *
     * @param amount a növelés mennyisége
     */
    public void addBiokerosene(int amount) {
        snowplow.addBiokerosene(amount);
    }

    /**
     * Visszaadja a takarító által irányított hókotrót.
     *
     * @return a hókotró
     */
    public Snowplow getSnowplow() {
        return snowplow;
    }

    /**
     * Új fejet ad a takarító által irányított hókotrónak, és eltárolja a birtokolt fejek között.
     *
     * @param newHead a takarító által irányított hókotró új feje
     */
    public void addHead(Head newHead) {
        addOwnedHead(newHead);
        snowplow.changeHead(newHead);
    }

    /**
     * A takarító irányítja a megadott hókotrót a sávon, és a sikeres takarításért pénzt kap.
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
     * Visszaadja a takarító által szerzett pontszámot, ami megegyezik a pénzmennyiséggel.
     *
     * @return a szerepkör pontszáma
     */
    @Override
    public int getScore() {
        return money;
    }

    /**
     * Visszaadja a takarító által birtokolt kotrófejek listáját.
     *
     * @return a birtokolt kotrófejek listája
     */
    public java.util.List<Head> getOwnedHeads() {
        return ownedHeads;
    }

    /**
     * Hozzáad egy kotrófejet a birtokolt fejek listájához.
     *
     * @param head a hozzáadandó kotrófej
     */
    public void addOwnedHead(Head head) {
        if (head != null) {
            ownedHeads.add(head);
        }
    }
}
