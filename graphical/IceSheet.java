package src;
/**
 * Az IceSheet osztaly a jegkereg utallapotat reprezentalja.
 *
 * Jegkereg eseten az ut nem jarhato a jarmuvek szamara,
 * ezert a forgalomhoz a feluletet elobb kezelni kell.
 */
public class IceSheet implements LaneState{
    /**
     * Meghatarozza, hogy a jegkerges sav jarhato-e jarmuvek szamara.
     *
     * @return true, azért, mert a jég nem akadályozza meg teljesen a haladást, de nagyon csúszós
     */
    @Override
    public boolean isPassable() {
        return true; 
    }

    /**
     * Visszaadja a jegkereg dinamikus sulyat.
     *
     * @return a dinamikus suly erteke
     */
    @Override
    public double getDynamicWeight() {
        return 3.0;
    }

    /**
     * Kezeli az idojaras valtozasait a jegkereg allapotaban.
     *
     * @param snowAmount a ho mennyisege tick-ekben merve
     * @return az uj lane state az idojaras valtozasa utan
     */
    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        if (snowAmount > 0) {
            return new ThinSnow();
         }
        
         return this;
    }
}