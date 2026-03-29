package skeleton.src;
/**
 * A NormalRoad osztaly egy normal tipusú utszakaszt reprezental.
 *
 * Ez az alap utszakasz, amelyre az idojaras hatasa
 * az altalanos szabalyok szerint alkalmazhato.
 */
public class NormalRoad extends Road{

    /**
     * Alkalmazza az idojaras hatasat a normal utszakaszra.
     *
     * @param weather az aktualis idojarasi allapot
     */
    @Override
    public void applyWeatherEffects(Weather weather){
        Skeleton.printCall("NormalRoad", "applyWeatherEffects(Weather)");
        weather.snowfallTick(this);
        Skeleton.printReturn("");
    }
}