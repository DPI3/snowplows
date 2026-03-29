package skeleton.src;

/**
 * A DragonHead a hókotró sárkány fejének megvalósítása.
 *
 * A sárkány fej biokerozin felhasználásával képes elolvasztani
 * a havat vagy a jeget, és tiszta útszakaszt hagy maga után.
 */
public class DragonHead extends Head {

    /**
     * Létrehoz egy DragonHead objektumot.
     */
    public DragonHead() {
        Skeleton.printCall("DragonHead", "DragonHead()");
        Skeleton.printReturn("");
    }

    /**
     * Megtisztítja a megadott sávot a sárkány fej segítségével.
     *
     * @param lane a takarítandó sáv
     * @param snowplow a takarítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("DragonHead", "clean(lane, snowplow)");

        // A use-case alapján a sárkány fej elolvasztja a havat vagy jeget,
        // majd tiszta útszakaszt hagy maga után.
        lane.setState(new Clear());
        lane.change(10);

        Skeleton.printState("Recognize cleaning, add money (modify attribute)");

        Skeleton.printReturn("");
    }

    /**
     * Visszaadja a fej típusát.
     *
     * @return a fej típusa
     */
    public String getHeadType() {
        Skeleton.printCall("DragonHead", "getHeadType()");
        Skeleton.printReturn("DragonHead");
        return "DragonHead";
    }

    /**
     * Visszaadja a sárkány fej árát.
     *
     * @return a fej ára
     */
    @Override
    public int getPrice() {
        Skeleton.printCall("DragonHead", "getPrice()");

        int price = 500;

        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}