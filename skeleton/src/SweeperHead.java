package skeleton.src;

/**
 * Represents the sweeper head for the snowplow.
 */
public class SweeperHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("SweeperHead", "clean(lane, snowplow)");
        // Takarítás logikája...
        Skeleton.printReturn("");
    }

    @Override
    public int getPrice() {
        Skeleton.printCall("SweeperHead", "getPrice()");
        
        int price = 300; // Vagy bármilyen összeg
        
        Skeleton.printReturn(String.valueOf(price));
        return price; 
    }
}