package src;

/**
 * A Bridge osztály egy híd típusú útszakaszt reprezentál.
 */
public class Bridge extends Road {

    /**
     * Új Bridge objektum létrehozása alapértelmezett értékekkel.
     */
    public Bridge() {
        super();
    }

    /**
     * Az időjárás hatásának alkalmazása a hídra.
     *
     * @param weather az aktuális időjárás
     */
    @Override
    public void applyWeatherEffects(Weather weather) {
        increaseSnowLevel();
    }
}