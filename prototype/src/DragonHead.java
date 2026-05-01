package src;

/**
 * A DragonHead bio-kerozinnal égeti le a havat/jéget.
 */
public class DragonHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getBiokeroseneStock() <= 0) return;

        // nem működik kavicsos úton
        if (lane.getLaneState() instanceof Gravel) return;

        //ha elfogy az üzemanyag csak siman nem takaritja tovabb
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5); // Üzemanyag levonása
            lane.change(1);          // Hó csökkentése
        }

        // nagy mértékű tisztítás
        lane.change(9999);
    }

    @Override
    public int getPrice() {
        return 120;
    }
}