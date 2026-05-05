package src;

/**
 * A Bridge osztály egy híd típusú útszakaszt reprezentál.
 */
public class Bridge extends Road {

    public Bridge() {
        super();
    }

    /**
     * Az időjárás hatásának alkalmazása.
     */
    @Override
    public void applyWeatherEffects(Weather weather) {
        increaseSnowLevel();
    }
}
