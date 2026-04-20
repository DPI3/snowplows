package skeleton.src;

/**
 * A ThrowerHead a hókotró hóhajító fejének megvalósítása.
 *
 * A hóhajító fej a havat az út mellé, messzebbre szórja.
 */
public class ThrowerHead extends Head {

    public ThrowerHead() {
        Skeleton.printCall("ThrowerHead", "ThrowerHead()");
        Skeleton.printReturn("");
    }

    /**
     * Megtisztítja a megadott sávot a hó eltávolításával.
     *
     * @param lane a takarítandó sáv
     * @param snowplow a műveletet végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("ThrowerHead", "clean(lane, snowplow)");

        lane.setState(new Clear());
        lane.change(10);

        Skeleton.printState("Recognize cleaning, add money (modify attribute)");

        Skeleton.printReturn("");
    }

    /**
     * Visszaadja a hóhajító fej árát.
     *
     * @return a fej ára
     */
    @Override
    public int getPrice() {
        Skeleton.printCall("ThrowerHead", "getPrice()");

        int price = 600;

        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}