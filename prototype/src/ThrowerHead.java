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
        //ha elfogy az üzemanyag csak siman nem takaritja tovabb
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5); // Üzemanyag levonása
            lane.change(5);          // Hó csökkentése
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