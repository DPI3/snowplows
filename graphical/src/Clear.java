package src;

/**
 * A Clear osztály a tiszta (hótól mentes) út állapotát reprezentálja.
 * 
 * A tiszta út az ideális útállapot, ahol a járművek normál sebességgel haladhatnak
 * és nincs szükség hó eltávolítására. Ez az értékelt állapot a közlekedés szempontjából.
 * A Clear állapot akkor áll fenn, amikor az útról sikeresen eltávolították az összes havat és jeget.
 */
public class Clear implements LaneState {
    
    /**
     * Meghatározza, hogy a tiszta út járható-e járművek számára.
     *
     * @return true, mivel a tiszta út teljesen járható
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Visszaadja a tiszta út dinamikus súlyát.
     *
     * @return a dinamikus súly értéke
     */
    @Override
    public double getDynamicWeight() {
        return 1.0;
    }

    /**
     * Kezeli az időjárás változásait a tiszta út állapotában.
     *
     * @param snowAmount a hó mennyisége tick-ekben mérve
     * @return az új lane state az időjárás változása után
     */
    @Override
    public LaneState handleWeatherChange(int snowAmount) {
       if (snowAmount >= 10) {
            return new DeepSnow();
        } else if (snowAmount > 0) {
            return new ThinSnow(); 
        }
                
        return this;
    }
}