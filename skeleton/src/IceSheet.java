package skeleton.src;
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
     * @return false, mivel a jegkereg nem jarhato
     */
    @Override
    public boolean isPassable() {
        Skeleton.printCall("IceSheet", "isPassable()");
        // A jégpáncél alapvetően járható, de veszélyes
        Skeleton.printReturn("true");
        return true; 
    }

    /**
     * Visszaadja a jegkereg dinamikus sulyat.
     *
     * @return a dinamikus suly erteke
     */
    @Override
    public double getDynamicWeight() {
        Skeleton.printCall("IceSheet", "getDynamicWeight()");
        // A jég növeli az út "súlyát" (lassabb haladás), pl. 3.0
        Skeleton.printReturn("3.0");
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
        Skeleton.printCall("IceSheet", "handleWeatherChange(snowAmount)");
        // Ha esik a hó a jégre, talán mély hó lesz belőle? 
        // Egyelőre maradjunk önmagánál:
        Skeleton.printReturn("this");
        return this;
    }
}