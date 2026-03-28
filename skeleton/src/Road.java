package skeleton.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for all road types.
 * Manages a list of lanes and handles weather effects.
 */
public abstract class Road {
    protected List<Lane> lanes = new ArrayList<>();

    /**
     * Applies weather effects to the road. 
     * Note: I used 'applyWeatherEffect' to match your subclasses!
     */
    public abstract void applyWeatherEffect(Weather weather);
}