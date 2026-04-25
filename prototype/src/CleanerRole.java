package prototype.src;

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

    private String name;

    public CleanerRole(String name, int money, Snowplow snowplow) {
        this.name=name;
        this.money = money;
        this.snowplow=snowplow;
    }

    /**
     * Vásárlási művelet a Store-ban. A takarító megvásárolhat hókotrót, kotrófejet vagy nyersanyagot.
     *
     * @param role a vásárlást végző szerepkör
     * @param item a megvásárolni kívánt elem
     * @return true, ha a vásárlás sikeres volt
     */
    public boolean buy(Role role, Buyable item) {
        return false;
    }

    /**
     * Vásárlás a boltban.
     *
     * @param store a bolt
     * @param item a megvásárolni kívánt elem
     * @return true, ha a vásárlás sikeres
     */
    public boolean buy(Store store, Buyable item) {
        boolean result = store.buy(this, item);
        return result;
    }

    public int getMoney() {
        return money;
    }

    public void changeMoney(int amount) {
        money += amount;
    }

    public void decreaseMoney(int price) {
        money -= price;
    }

    public void addSalt(int amount) {
        snowplow.addSalt(amount);
    }

    public void addGravel(int amount) {
        snowplow.addGravel(amount);
    }

    public void addBiokerosene(int amount) {
        snowplow.addBiokerosene(amount);
    }

    public Snowplow getSnowplow() {
        return snowplow;
    }

    public void addHead(Head newHead) {
        snowplow.changeHead(newHead);
    }

    /**
     * A takarító irányítja a megadott hókotrót.
     *
     * @param sp a vezérelni kívánt hókotró
     */
    public void controlSnowplow(Snowplow sp) {
        Lane lane = sp.getCurrentLane();
        if (lane == null) {
            lane = new Lane();
        }
        sp.clean(lane);
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
}
