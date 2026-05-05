package src;
/**
 * A Role absztrakt osztály felel a járművek irányításáért.
 * A szerepkörök határozzák meg, hogy a játékos milyen műveleteket végezhet
 * és hogyan szerez pontokat a játék során.
 * Egy játékos több szerepkört is felvehet egyszerre.
 */
public abstract class Role {

    /**
     * Visszaadja a szerepkör által szerzett pontszámot.
     *
     * @return a szerepkör pontszáma
     */
    public abstract int getScore();
}
