package src;

/**
 * Zúzalékot szór a jégre, így járhatóvá teszi az utat.
 */
public class GravelSpreaderHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getGravelStock() <= 0) return;

        snowplow.consumeGravel(1);
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5);
            lane.change(1);
        }

        lane.setState(new Gravel());
        lane.setGravelThickness(1.0);
    }

    @Override
    public int getPrice() {
        return 70;
    }
}