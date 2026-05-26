package src;

/**
 * A ThinSnow osztály a vékony hó útállapotot reprezentálja.
 *
 * Vékony hó esetén az út alapvetően még járható, de a felület
 * kezelésével javítható a közlekedés biztonsága és hatékonysága.
 */
public class ThinSnow implements LaneState{

    /**
     * Meghatározza, hogy a vékony hóval borított sáv járható-e.
     *
     * @return true, mert ez az állapot még járható
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Visszaadja a vékony hó dinamikus súlyát.
     *
     * @return a dinamikus súly értéke
     */
    @Override
    public double getDynamicWeight() {
        return 1.5;
    }

    /**
     * Kezeli az időjárás változásait vékony hó állapotban.
     *
     * @param snowAmount a hó mennyisége tick-ekben mérve
     * @return az új sávállapot az időjárás változása után
     */
    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        if (snowAmount >= 10) {
            return new DeepSnow();
        }
        else if (snowAmount <= 0) {
            return new Clear();
        }

        return this;
    }
}
