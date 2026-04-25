package prototype.src;
/**
 * A ThinSnow osztaly a vekony ho utallapotat reprezentalja.
 *
 * Vekony ho eseten az ut alapvetoen meg jarhato, de a felulet
 * kezelesevel javithato a kozlekedes biztonsaga es hatekonysaga.
 */
public class ThinSnow implements LaneState{
    /**
     * Meghatarozza, hogy a vekony hovel boritott sav jarhato-e.
     *
     * @return true, mert ez az allapot meg jarhato
     */
    @Override
    public boolean isPassable() {
        return true; 
    }

    /**
     * Visszaadja a vekony ho dinamikus sulyat.
     *
     * @return a dinamikus suly erteke
     */
    @Override
    public double getDynamicWeight() {
        return 1.5;
    }

    /**
     * Kezeli az idojaras valtozasait vekony ho allapotban.
     *
     * @param snowAmount a ho mennyisege tick-ekben merve
     * @return az uj lane state az idojaras valtozasa utan
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