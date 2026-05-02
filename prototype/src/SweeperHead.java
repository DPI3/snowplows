package src;

/**
 * A SweeperHead vékony hó eltávolítására szolgál.
 */
public class SweeperHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5);
            lane.change(3);
        }
    }

    @Override
    public int getPrice() {
        return 60;
    }
}