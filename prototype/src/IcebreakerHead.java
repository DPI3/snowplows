package src;

/**
 * Jég feltörésére szolgáló fej.
 */
public class IcebreakerHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {

        if (lane.getLaneState() instanceof IceSheet) {
            lane.setState(new BrokenIce());
            return;
        }
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5);
            lane.change(1);
        }
    }

    @Override
    public int getPrice() {
        return 90;
    }
}