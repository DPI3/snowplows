package prototype.src;

/**
 * Só szórásával csökkenti a jegesedést.
 */
public class SaltSpreaderHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getSaltStock() <= 0) return;

        snowplow.consumeSalt(1);
        lane.change(5);
    }

    @Override
    public int getPrice() {
        return 50;
    }
}