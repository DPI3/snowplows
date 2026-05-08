package src;
/**
 * A LaneState interfesz egy sav aktualis allapotat irja le.
 *
 * Az allapot meghatarozza, hogy a sav jarhato-e,
 * mekkora dinamikus terheles tartozik hozza,
 * es hogyan valtozik idojarasi hatasra.
 */
public interface LaneState{
    /**
     * Megadja, hogy a sav jarhato-e jarmuvek szamara.
     *
     * @return true, ha jarhato; kulonben false
     */
    boolean isPassable();

    /**
     * Visszaadja a sav dinamikus sulyat.
     *
     * @return a dinamikus suly erteke
     */
    double getDynamicWeight();

    /**
     * Kezeli az idojarasi valtozasokat es visszaadja az uj allapotot.
     *
     * @param snowAmount a ho mennyisege
     * @return az uj savallapot
     */
    LaneState handleWeatherChange(int snowAmount); 
}