package src;

/**
 * Jég feltörésére szolgáló fej.
 */
public class IcebreakerHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {

        // ha jégpáncél van → feltört jég lesz
        if (lane.getLaneState() instanceof IceSheet) {
            lane.setState(new BrokenIce());
            return;
        }

        // egyébként kis mértékű változtatás
        lane.change(1);
    }

    @Override
    public int getPrice() {
        return 90;
    }
}