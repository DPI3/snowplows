package src;

import src.Bridge;
import src.Lane;
import src.Road;
import src.RoadNetwork;
import src.Tunnel;

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
    }

    /**
     * Applies snowfall effect to a road.
     */
    public void snowfallTick(Road road) {
        if (road != null) {
            road.increaseSnowLevel();
        }
        int appliedSnow = snowIntensity;

        if (road instanceof Bridge) {
            appliedSnow += 2;
        } else if (road instanceof Tunnel) {
            appliedSnow = 0;
        }

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