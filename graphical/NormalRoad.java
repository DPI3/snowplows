package src;

/**
 * A NormalRoad egy hagyományos útszakaszt modellez. Az időjárási hatások közvetlenül érvényesülnek rajta, így a
 * sávok állapota a hó vagy jég mennyiségének megfelelően változhat.
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
