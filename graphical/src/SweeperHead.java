package src;

/**
 * A SweeperHead olyan mechanikus tisztítófej, amely a havat vagy a fellazított jeget eltakarítja. 
 * A zúzalékot ugyanúgy eltakarítja, mint a havat. Vastag, de még nem teljesen lefagyott hó eltávolítására 
 * alkalmas, valamint képes a korábban kiszórt zúzott kő eltávolítására is, ha arra  már nincs tovább szükség az 
 * adott sávon.
 */
public class SweeperHead extends Head {

    /**
     * Az útszakasz tisztítása, eltakarítja a hó-, feltört jég-, illetve a zúzottkő réteget.
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
                target = road.getLaneAfter(snowplow.getFacingLane(), 1);
            }
        }

        if (target != null) {
            target.placeMaterial(material);
        }
    }

    /**
     * Visszadja a SweeperHead árát.
     * 
     * @return az ára
     */
    @Override
    public int getPrice() {
        return 60;
    }
}