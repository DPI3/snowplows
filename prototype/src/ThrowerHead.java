package prototype.src;

/**
 * Vastag hó eltávolítására alkalmas fej.
 */
public class ThrowerHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        lane.change(5);
    }

    @Override
    public int getPrice() {
        return 80;
    }
}