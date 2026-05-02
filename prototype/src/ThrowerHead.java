package src;

/**
 * Vastag hó eltávolítására alkalmas fej.
 */
public class ThrowerHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5);
            lane.change(5);
        }
    }

    @Override
    public int getPrice() {
        return 80;
    }
}