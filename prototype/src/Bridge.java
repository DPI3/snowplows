package prototype.src;

/**
 * A Bridge osztály egy híd típusú útszakaszt reprezentál.
 */
public class Bridge extends Road {

    public Bridge() {
        super();
    }

    @Override
    public void applyWeatherEffects(Weather weather) {
        if (weather != null) {
            weather.snowfallTick(this);
        }
    }
}