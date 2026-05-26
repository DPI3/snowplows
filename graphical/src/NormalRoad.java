package src;

/**
 * A NormalRoad egy hagyományos útszakaszt modellez. Az időjárási hatások közvetlenül érvényesülnek rajta, így a
 * sávok állapota a hó vagy jég mennyiségének megfelelően változhat.
 */
public class NormalRoad extends Road {

    /**
     * Új NormalRoad objektum létrehozása alapértelmezett beállításokkal.
     */
    public NormalRoad() {
        super();
    }

    /**
     * Az időjárás hatásának alkalmazása a normál útszakaszra.
     * Minden sávon végrehajtja a havazási tick-et és alkalmazza az aktuális hószintet.
     *
     * @param weather az aktuális időjárás objektum
     */
    @Override
    public void applyWeatherEffects(Weather weather) {
        if (weather == null) {
            return;
        }

        for (Lane lane : lanes) {
            if (lane != null) {
                weather.snowfallTick(this);
                lane.applyWeather(snowLevel);
            }
        }
    }
}
