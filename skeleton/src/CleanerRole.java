package skeleton.src;

/**
 * A CleanerRole a takarító szerepkört reprezentálja.
 * Felelős a hó eltávolításáért, a hókotrók irányításáért
 * és a takarításhoz kapcsolódó gazdasági döntésekért.
 * Emellett kezeli a játékos pénzét, vásárolhat, és működteti a hókotrók fejeit.
 */
public class CleanerRole extends Role {

    /** A takarító jelenlegi pénzmennyisége. */
    private int money;

    /** A takarító birtokolt feje. */
    private Head currentHead;

    public CleanerRole() {
        Skeleton.printCall("CleanerRole", "CleanerRole()");
        this.money = 1000;
        Snowplow snowplow= new Snowplow();
        Skeleton.printReturn("");
    }

    /**
     * Vásárlási művelet a Store-ban. A takarító megvásárolhat hókotrót, kotrófejet vagy nyersanyagot.
     *
     * @param role a vásárlást végző szerepkör
     * @param item a megvásárolni kívánt elem
     * @return true, ha a vásárlás sikeres volt
     */
    public boolean buy(Role role, Buyable item) {
        Skeleton.printCall("CleanerRole", "buy(role, item)");

        Skeleton.printReturn("");
        return false;
    }

    public int getMoney() {
        Skeleton.printCall("CleanerRole", "getMoney()");
        Skeleton.printReturn("");
        return money;
    }

    public void changeMoney(int amount) {
        Skeleton.printCall("CleanerRole", "changeMoney(amount)");
        money += amount;
        Skeleton.printReturn("");
    }

    public void addHead(Head newHead) {
        Skeleton.printCall("CleanerRole", "addHead(newHead)");
        currentHead = newHead;
        Skeleton.printState("Új fej hozzáadva a felszereléshez.");
        Skeleton.printReturn("");
    }

    /**
     * A takarító irányítja a megadott hókotrót.
     *
     * @param snowplow a vezérelni kívánt hókotró
     */
    public void controlSnowplow(Snowplow sp, Lane lane) {
        Skeleton.printCall("CleanerRole", "controlSnowplow(sp, lane)");

        sp.clean(lane);

        Skeleton.printReturn("");
    }

    /**
     * A metódus megadja a takarító által szerzett pontszámot.
     *
     * @return a szerepkör pontszáma
     */
    @Override
    public int getScore() {
        Skeleton.printCall("CleanerRole", "getScore()");
        
        
        Skeleton.printReturn("cleanerScore");
        return money; // Ideiglenes
    }
}
