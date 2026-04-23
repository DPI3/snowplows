package skeleton.src;

/**
 * A DeepSnow osztály a vastag hó útállapotát reprezentálja.
 * 
 * A vastag hó alatt az út nem járható járművek számára, csak a megfelelő hókotrók
 * tudják azt kezelni. A vastag hó jelentősen nehezíti a közlekedést,
 * és megköveteli az aktív hótakarítást az út újrahasználhatóságához.
 */
public class DeepSnow implements LaneState {

    
    /**
     * Meghatározza, hogy a vastag hóval borított sáv járható-e járművek számára.
     *
     * @return false, mivel a vastag hó nem járható
     */
    @Override
    public boolean isPassable() {
        Skeleton.printCall("DeepSnow", "isPassable()");
        // A mély hóban elakadnak az autók, tehát ez false
        Skeleton.printReturn("false");
        return false; 
    }

    /**
     * Visszaadja a vastag hó dinamikus súlyát.
     *
     * @return a dinamikus súly értéke
     */
    @Override
    public double getDynamicWeight() {
        Skeleton.printCall("DeepSnow", "getDynamicWeight()");
        // Nagyon nehéz (vagy lehetetlen) haladás, magas súly
        Skeleton.printReturn("10.0");
        return 10.0;
    }

    /**
     * Kezeli az időjárás változásait a vastag hó állapotában.
     *
     * @param snowAmount a hó mennyisége tick-ekben mérve
     * @return az új lane state az időjárás változása után
     */
    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        Skeleton.printCall("DeepSnow", "handleWeatherChange(snowAmount)");
        // Marad mély hó, vagy ha sokat esik, még mélyebb
        Skeleton.printReturn("this");
        return this;
    }
}