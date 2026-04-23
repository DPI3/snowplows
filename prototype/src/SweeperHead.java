package prototype.src;

/**
 * A SweeperHead vékony hó eltávolítására szolgál.
 */
public class SweeperHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        // közepes tisztítás
        lane.change(3);
    }

    @Override
    public int getPrice() {
        return 60;
    }
}