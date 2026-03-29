package skeleton.src;

/**
 * A SaltSpreaderHead a hókotró sószóró fejének megvalósítása.
 *
 * A sószóró fej só kijuttatásával segít a havas vagy jeges út
 * megtisztításában.
 */
public class SaltSpreaderHead extends Head {

    /**
     * Kezeli a megadott sávot sószórással.
     *
     * @param lane a kezelendő sáv
     * @param snowplow a műveletet végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("SaltSpreaderHead", "clean(lane, snowplow)");

        lane.setState(new Clear());
        lane.change(10);

        Skeleton.printState("Recognize cleaning, add money (modify attribute)");

        Skeleton.printReturn("");
    }

    /**
     * Visszaadja a sószóró fej árát.
     *
     * @return a fej ára
     */
    @Override
    public int getPrice() {
        Skeleton.printCall("SaltSpreaderHead", "getPrice()");
        int price = 400;
        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}