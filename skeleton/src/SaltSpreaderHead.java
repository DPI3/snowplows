package skeleton.src;

public class SaltSpreaderHead extends Head { // Itt SweeperHead vagy ThrowerHead
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall(this.getClass().getSimpleName(), "clean(lane, snowplow)");
        Skeleton.printReturn("");
    }

    @Override
    public int getPrice() {
        Skeleton.printCall(this.getClass().getSimpleName(), "getPrice()");
        int price = 400; // Adj meg egy tetszőleges számot
        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}