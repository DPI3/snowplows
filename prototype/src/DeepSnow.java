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
        return false;
    }

    /**
     * Visszaadja a vastag hó dinamikus súlyát.
     *
     * @return a dinamikus súly értéke
     */
    @Override
    public double getDynamicWeight() {
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
        if (snowAmount < 0) {
            return new Clear();
        }
        return this;
    }
}