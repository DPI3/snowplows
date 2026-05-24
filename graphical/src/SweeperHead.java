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
     * Visszadja a SweeperHead árát.
     * 
     * @return az ára
     */
    @Override
    public int getPrice() {
        return 60;
    }
}