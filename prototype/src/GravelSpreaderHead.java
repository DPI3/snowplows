package src;

/**
 * Zúzalékot szór a jégre, így járhatóvá teszi az utat.
 */
public class GravelSpreaderHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getGravelStock() <= 0) return;

        snowplow.consumeGravel(1);

        // új állapot: Gravel
        lane.setState(new Gravel());
        lane.setGravelThickness(1.0);
    }

    @Override
    public int getPrice() {
        return 70;
    }
}