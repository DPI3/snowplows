package src;

/**
 * A SaltSpreaderHead a hókotró sókészletét használva csökkenti az utak jegesedését és javítja a tapadást. 
 * Működéséhez szükség van a hókotró sókészletére.
 */
public class SaltSpreaderHead extends Head {

    /**
     * Takarítás, sót szór a sávra. Ha van elegendő sókészlet, csökkenti a jegesedés hatását vagy javítja az út
     * járhatóságát.
     * 
     * @param lane a takarítani kívánt útszakasz
     * @param snowplow a takarítást végző hókotró
     */
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

    /**
     * Visszaadja a SaltSpreaderHead árát.
     * 
     * @return az ára
     */
    @Override
    public int getPrice() {
        return 50;
    }
}