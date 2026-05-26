package src;

/**
 * A SaltSpreaderHead a hókotró sókészletét használva csökkenti az utak jegesedését és javítja a tapadást.
 * Működéséhez szükség van a hókotró sókészletére.
 */
public class SaltSpreaderHead extends Head {

    /**
     * Takarítás végrehajtása: sót szór a sávra. Ha van elegendő sókészlet és üzemanyag,
     * csökkenti a jegesedés hatását és javítja az út járhatóságát.
     *
     * @param lane a takarítani kívánt útszakasz
     * @param snowplow a takarítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        if (lane == null || snowplow == null) return;
        if (lane.getLaneState() instanceof Gravel) return;
        if (snowplow.getSaltStock() <= 0) return;
        if (snowplow.getFuel() < 5) return;

        snowplow.consumeSalt(1);
        snowplow.consumeFuel(5);

        lane.applySalt(5);
    }

    /**
     * Visszaadja a SaltSpreaderHead árát.
     *
     * @return az ára
     */
    @Override
    public int getPrice() {
        return 110;
    }
}
