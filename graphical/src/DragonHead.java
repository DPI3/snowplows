package src;

/**
 * A DragonHead egy speciális tisztítófej,
 * amely bio-kerozin felhasználásával magas hőmérséklettel olvasztja el a havat vagy jeget.
 */
public class DragonHead extends Head {

    /**
     * Bio-kerozint használva megtisztítja a sávot. Ha van elegendő készlet, a hó- vagy jégréteget megszünteti
     * vagy jelentősen csökkenti.
     * 
     * @param lane a tisztítandó szakasz
     * @param snowplow a tisztítást végző hókotró
     */
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

    /**
     * Visszaadja a DragonHead árát.
     * 
     * @return az ár
     */
    @Override
    public int getPrice() {
        return 130;
    }
}