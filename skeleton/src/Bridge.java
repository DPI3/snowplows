package skeleton.src;
/**
 * A Bridge osztaly egy hid tipusu utszakaszt reprezental.
 *
 * A hidakra kulon idojarasi hatasok ervenyesulhetnek,
 * amelyek befolyasolhatjak a jarhatosagot es az allapotot.
 */
public class Bridge extends Road{

    /**
     * Alkalmazza az idojaras hatasat a hid utallapotara.
     *
     * @param weather az aktualis idojarasi allapot
     */
    @Override
    public void applyWeatherEffects(Weather weather){
        Skeleton.printCall("Bridge", "applyWeatherEffects(Weather)");
        weather.snowfallTick(this);
        Skeleton.printReturn("");
    }
}