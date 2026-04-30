package prototype.src;

import skeleton.src.Bridge;
import skeleton.src.Lane;
import skeleton.src.Road;
import skeleton.src.RoadNetwork;
import skeleton.src.Tunnel;

/**
 * Represents the weather conditions in the simulation.
 */
public class Weather {
    
    /** * Through this, the weather accesses the road network to query 
     * the roads/lanes where snow falls.
     */
    private RoadNetwork roadNetwork;

    /** * Stores the intensity of the snowfall. This determines how much 
     * snow falls on the lanes in a single round.
     */
    private int snowIntensity;
    
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
        int appliedSnow = snowIntensity;

        // Determine the actual intensity of snowfall/icing based on the road type
        if (road instanceof Bridge) {
            // Increased icing occurs on bridges (based on documentation 8.1.2)
            appliedSnow += 2; 
        } else if (road instanceof Tunnel) {
            // Tunnels are protected by a cover, the effect of weather is limited
            appliedSnow = 0;
        }

        // If there is snowfall, apply it to all lanes of the road
        if (appliedSnow > 0) {
            for (Lane lane : road.getLanes()) {
                lane.applyWeather(appliedSnow);
            }
        }
    }

    /**
     * Sets the new snowfall intensity.
     * @param intensity the new value
     */
    public void setSnowIntensity(int intensity) {
        this.snowIntensity = Math.max(0, intensity);
    }

    /**
     * Returns the current snow intensity.
     * @return the current value
     */
    public int getSnowIntensity() {
        return snowIntensity;
    }
    
}