package src;

/**
 * A NormalRoad osztály egy normál típusú útszakaszt reprezentál.
 */
public class NormalRoad extends Road {

    public NormalRoad() {
        super();
    }

    /**
     * Az időjárás hatásának alkalmazása.
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