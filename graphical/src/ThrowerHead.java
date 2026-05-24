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
        if (lane == null || snowplow == null) return;
        if (snowplow.getFuel() < 5) return;

        LaneState material = lane.removeMovableMaterial();

        if (material == null) return;

        snowplow.consumeFuel(5);

        Lane target = null;

        if (snowplow.getFacingLane() != null) {
            Road road = snowplow.getFacingLane().getParentRoad();
            if (road != null) {
                target = road.getLaneAfter(snowplow.getFacingLane(), 3);
            }
        }

        if (target != null) {
            target.placeMaterial(material);
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