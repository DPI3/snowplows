package src;
/**
 * A BrokenIce osztály a tört jég útállapotát reprezentálja.
 *
 * A tört jég kritikus, de járható útállapot, jellemzően jégtörő munkájának eredménye.
 * Tovább kezelhető (pl. eltakarítással), hogy újra Clear állapotba kerüljön.
 */
public class BrokenIce implements LaneState {
        
    /**
     * Meghatározza, hogy a tört jéggel borított sáv járható-e járművek számára.
     *
     * @return false, mivel a tört jég nem járható
     */
    @Override
    public boolean isPassable() {
        return true; 
    }

    /**
     * Visszaadja a tört jég dinamikus súlyát.
     *
     * @return a dinamikus súly értéke
     */
    @Override
    public double getDynamicWeight() {
        return 1.5;
    }

    /**
     * Kezeli az időjárás változásait a tört jég állapotában.
     *
     * @param snowAmount a hó mennyisége tick-ekben mérve
     * @return az új lane state az időjárás változása után
     */
    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        if (snowAmount > 0) {
            return new ThinSnow(); 
        } else if (snowAmount < 0) {
            return new Clear();
        }
        return this;
    }
}