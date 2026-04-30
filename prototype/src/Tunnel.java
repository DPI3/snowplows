package src;

/**
 * A Tunnel osztály egy alagút típusú útszakaszt reprezentál.
 */
public class Tunnel extends Road {

    public Tunnel() {
        super();
    }

    @Override
    public void applyWeatherEffects(Weather weather) {
        reduceSnowLevel(); // védett
    }
}