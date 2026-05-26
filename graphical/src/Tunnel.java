package src;

/**
 * A Tunnel osztály egy alagút típusú útszakaszt reprezentál.
 * Az alagútban az időjárás hatása nem érvényesül közvetlenül, helyette a hószint csökken.
 */
public class Tunnel extends Road {

    /**
     * Új Tunnel objektum létrehozása alapértelmezett beállításokkal.
     */
    public Tunnel() {
        super();
    }

    /**
     * Az időjárás hatásának alkalmazása az alagútra.
     * Az alagútban a hószint automatikusan csökken, függetlenül az aktuális időjárástól.
     *
     * @param weather az aktuális időjárás objektum
     */
    @Override
    public void applyWeatherEffects(Weather weather) {
        reduceSnowLevel();
    }
}
