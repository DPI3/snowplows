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

        // nem működik kavicsos úton
        if (lane.getLaneState() instanceof Gravel) return;

        snowplow.consumeBiokerosene(1);
        //ha elfogy az üzemanyag csak siman nem takaritja tovabb
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5); // Üzemanyag levonása
            lane.change(1);          // Hó csökkentése
        }

        // nagy mértékű tisztítás
        lane.change(9999);
    }

    /**
     * Visszaadja a DragonHead árát.
     * 
     * @return az ár
     */
    @Override
    public int getPrice() {
        return 120;
    }
}