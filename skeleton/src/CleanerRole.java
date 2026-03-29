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

    public CleanerRole() {
        Skeleton.printCall("CleanerRole", "CleanerRole()");

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
        
        return false;
    }

    /**
     * A takarító irányítja a megadott hókotrót.
     *
     * @param snowplow a vezérelni kívánt hókotró
     */
    public void controlSnowplow(Snowplow sp) {
        Skeleton.printCall("CleanerRole", "controlSnowplow(sp)");
        
        sp.clean(sp.currentLane); // Továbbhívás a hókotróra
        
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
