package skeleton.src;
/**
 * A Brokenice osztály a tört jég útállapotát reprezentálja.
 * 
 * A tört jég egy kritikus állapot, ahol az út nem járható járművek számára.
 * Ez az állapot a legveszélyesebb útállapot, amely azonnali beavatkozást igényel.
 * A tört jégnek speciális jégtörő hókotrók szükségesek a helyreállításához.
 */
public class Brokenice implements LaneState{
        
    /**
     * Meghatározza, hogy a tört jéggel borított sáv járható-e járművek számára.
     *
     * @return false, mivel a tört jég nem járható
     */
    @Override
    public boolean isPassable() {
        Skeleton.printCall("Brokenice", "isPassable()");
        // Itt kell egy return! Tegyük fel, hogy a tört jég alapból járható.
        Skeleton.printReturn("true");
        return true; 
    }

    /**
     * Visszaadja a tört jég dinamikus súlyát.
     *
     * @return a dinamikus súly értéke
     */
    @Override
    public double getDynamicWeight() {
        Skeleton.printCall("Brokenice", "getDynamicWeight()");
        // Itt is kell egy return! A tört jég nehezíti a haladást (pl. 2.0 súly).
        Skeleton.printReturn("2.0");
        return 2.0;
    }

    /**
     * Kezeli az időjárás változásait a tört jég állapotában.
     *
     * @param snowAmount a hó mennyisége tick-ekben mérve
     * @return az új lane state az időjárás változása után
     */
    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        Skeleton.printCall("Brokenice", "handleWeatherChange(snowAmount)");
        // Itt is kell egy return! Visszaadjuk saját magát vagy egy új állapotot.
        Skeleton.printReturn("this");
        return this;
    }
}