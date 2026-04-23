package prototype.src;

/**
 * A DragonHead bio-kerozinnal égeti le a havat/jéget.
 */
public class DragonHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getBiokeroseneStock() <= 0) return;

        // nem működik kavicsos úton
        if (lane.getLaneState() instanceof Gravel) return;

        snowplow.consumeBiokerosene(1);

        // nagy mértékű tisztítás
        lane.change(9999);
    }

    @Override
    public int getPrice() {
        return 120;
    }
}