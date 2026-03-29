package skeleton.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt alaposztály minden úttípushoz.
 * Kezeli a sávok listáját és az időjárási hatásokat.
 */
public abstract class Road {
    protected List<Lane> lanes = new ArrayList<>();

    public void applyWeatherEffects(Weather weather) {
        Skeleton.printCall("RoadNetwork", "applyWeatherEffects()");
        Skeleton.printReturn("");
    }
}
