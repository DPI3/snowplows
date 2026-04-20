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
        Skeleton.printCall("Road", "applyWeatherEffects()");
                // Döntési pont bekérése a Skeleton segítségével
        int snowamount = Skeleton.requestInput("Mennyi hó esett? (1: Kevés, 2: Sok)");

        for(int i=0; i<lanes.size(); i++){
            lanes.get(i).applyWeather(snowamount);
        }
        
        Skeleton.printReturn("");
    }
}