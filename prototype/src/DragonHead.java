package src;

/**
 * A DragonHead bio-kerozinnal égeti le a havat/jéget.
 */
public class DragonHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getBiokeroseneStock() <= 0) return;

        if (lane.getLaneState() instanceof Gravel) return;

        snowplow.consumeBiokerosene(1);
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5);
            lane.change(1);
        }

        lane.change(9999);
    }

    @Override
    public int getPrice() {
        return 120;
    }
}