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

    public CleanerRole() {}

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
     * A takarító irányítja a megadott hókotrót.
     *
     * @param snowplow a vezérelni kívánt hókotró
     */
    public void controlSnowplow(Snowplow sp, Lane l) {
        Skeleton.printCall("CleanerRole", "controlSnowplow(sp, l)");
        
        sp.clean(l); // Továbbhívás a hókotróra
        
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
        
        // (A korábbi assert fájlod alapján ide be kell tenni a Scoreboard hívást a teszthez)
        // scoreboard.getScore();
        
        Skeleton.printReturn("currentScore");
        return money; // Vagy amiből a pontszám számítódik a valóságban
    }
}
