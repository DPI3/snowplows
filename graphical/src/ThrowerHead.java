package src;

/**
 * A ThrowerHead olyan mechanikus tisztítófej, amely a havat, zúzottkövet vagy a fellazított jeget oldalra dobja. 
 */
public class ThrowerHead extends Head {

    /**
     * Az útszakasz tisztítása, oldalra söpri a hó-, feltört jég-, illetve a zúzottkő réteget.
     * 
     * @param lane a tisztítandó szakasz
     * @param snowplow a tisztítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getFuel() < 5) return;

        if (lane.getLaneState() instanceof Gravel ||
            lane.getLaneState() instanceof BrokenIce ||
            lane.getLaneState() instanceof ThinSnow ||
            lane.getLaneState() instanceof DeepSnow) {

            snowplow.consumeFuel(5);
            lane.setState(new Clear());
        }
    }

     /**
     * Visszadja a ThrowerHead fej árát.
     * 
     * @return az ára
     */
    @Override
    public int getPrice() {
        return 80;
    }
}