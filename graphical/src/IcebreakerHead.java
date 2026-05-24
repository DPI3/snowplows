package src;

/**
 * Az IcebreakerHead feladata a kemény, letapadt jégréteg mechanikus feltörése. 
 * Ez a fej elsősorban nem a teljes tisztítást végzi el, hanem a jég állapotát alakítja át kezelhetőbbé, például
 * jégpáncélból feltört jéggé. Ezzel előkészíti a felületet más fejek, például a SweeperHead vagy ThrowerHead 
 * számára.
 */
public class IcebreakerHead extends Head {

     /**
     * Az útszakasz tisztítása, feltöri a jégpáncélt, hogy az út később teljesen megtisztítható legyen.
     * 
     * @param lane a tisztítandó szakasz
     * @param snowplow a tisztítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (lane.getLaneState() instanceof Gravel) return;

        if (snowplow.getFuel() < 5) return;

        if (lane.getLaneState() instanceof IceSheet) {
            snowplow.consumeFuel(5);
            lane.setState(new BrokenIce());
        }
    }

    /**
     * Visszadja a jégtörő fej árát.
     * 
     * @return az ára
     */
    @Override
    public int getPrice() {
        return 90;
    }
}