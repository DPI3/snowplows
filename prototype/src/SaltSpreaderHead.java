package src;

/**
 * Só szórásával csökkenti a jegesedést.
 */
public class SaltSpreaderHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getSaltStock() <= 0) return;

        snowplow.consumeSalt(1);
        //ha elfogy az üzemanyag csak siman nem takaritja tovabb
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5); // Üzemanyag levonása
            lane.change(5);          // Hó csökkentése
        }
    }

    @Override
    public int getPrice() {
        return 50;
    }
}