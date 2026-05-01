package src;

/**
 * A SweeperHead vékony hó eltávolítására szolgál.
 */
public class SweeperHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        //ha elfogy az üzemanyag csak siman nem takaritja tovabb
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5); // Üzemanyag levonása
            // közepes tisztítás
            lane.change(3);          // Hó csökkentése
        }
    }

    @Override
    public int getPrice() {
        return 60;
    }
}