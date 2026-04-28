package prototype.src;

/**
 * A Tunnel osztály egy alagút típusú útszakaszt reprezentál.
 */
public class Tunnel extends Road {

    public Tunnel() {
        super();
    }

    @Override
    public void applyWeatherEffects(Weather weather) {
        // alagútban kisebb hatás
        if (weather != null) {
            this.reduceSnowLevel(); // pl. kevésbé gyűlik a hó
        }
    }
}