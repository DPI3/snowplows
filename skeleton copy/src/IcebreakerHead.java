package skeleton.src;

/**
 * Az IcebreakerHead a hókotró jégtörő fejének megvalósítása.
 */
public class IcebreakerHead extends Head {

    public IcebreakerHead() {
        Skeleton.printCall("IcebreakerHead", "IcebreakerHead()");
        Skeleton.printReturn("");
    }

    /**
     * Megtisztítja a megadott sávot a jég feltörésével.
     *
     * @param lane a takarítandó sáv
     * @param snowplow a takarítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("IcebreakerHead", "clean(lane, snowplow)");

        lane.setState(new Brokenice());

        Skeleton.printReturn("");
    }

    /**
     * Visszaadja a jégtörő fej árát.
     *
     * @return a fej ára
     */
    @Override
    public int getPrice() {
        Skeleton.printCall("IcebreakerHead", "getPrice()");

        int price = 800;

        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}