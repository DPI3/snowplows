package src;

/**
 * A GravelSpreaderHead speciális kotrófej, amely zúzott követ, azaz zúzalékot szór a jármű előtt az útfelületre.
 * Elsődleges célja nem a hó fizikai eltávolítása, hanem a csúszós, jeges útfelület biztonságosabbá tétele.
 */
public class GravelSpreaderHead extends Head {

    /**
     * Az útszakasz tisztítása, ellenőrzi a hókotró zúzottkő-készletét, majd annak felhasználásával a sávot
     * kezeli. Ha van elegendő zúzalék, a sáv állapotát Gravel állapotba helyezi vagy növeli a zúzalék
     * borítottságát.
     *
     * @param lane a tisztítandó szakasz
     * @param snowplow a tisztítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (snowplow.getGravelStock() <= 0) return;

        snowplow.consumeGravel(1);
        if (snowplow.getFuel() >= 5) {
            snowplow.consumeFuel(5);
            lane.change(1);
        }

        lane.setState(new Gravel());
        lane.setGravelThickness(1.0);
    }

    /**
     * Visszaadja a GravelSpreaderHead árát.
     *
     * @return az ár
     */
    @Override
    public int getPrice() {
        return 100;
    }
}
