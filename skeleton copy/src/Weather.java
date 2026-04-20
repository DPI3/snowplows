package skeleton.src;

/**
 * Represents the weather conditions in the simulation.
 */
public class Weather {
    /**
     * Simulation tick for weather changes.
     */
    public void tick() {
        Skeleton.printCall("Weather", "tick()");
        Skeleton.printReturn("");
    }

    /**
    * Simulation tick for snowfall events.
    * Applies the effects of snowfall to the road network.
    *
    * @param roadNetwork the road network to apply the snowfall effects on
    */
    public void snowfallTick(Road road) {
        Skeleton.printCall("Weather", "snowfallTick(road)");
        road.applyWeatherEffects(this);
        Skeleton.printReturn("");
    }
}