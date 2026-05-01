package src;

/**
 * Vastag hó eltávolítására alkalmas fej.
 */
public class ThrowerHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        //ha elfogy az üzemanyag csak siman nem takaritja tovabb
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5); // Üzemanyag levonása
            lane.change(5);          // Hó csökkentése
        }
    }

    @Override
    public int getPrice() {
        return 80;
    }
}