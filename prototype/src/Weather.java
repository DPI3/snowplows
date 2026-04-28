package prototype.src;

/**
 * Represents the weather conditions in the simulation.
 */
public class Weather {

    public void tick() {
        // később ide jöhet random időjárás változás
    }

    /**
     * Applies snowfall effect to a road.
     */
    public void snowfallTick(Road road) {
        if (road != null) {
            road.increaseSnowLevel();
        }
    }
}