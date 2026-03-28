package skeleton.src;

/**
 * Represents a snow thrower head for the snowplow.
 */
public class ThrowerHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("ThrowerHead", "clean(lane, snowplow)");
        
        // A hófúvó fej eltávolítja a havat az útról
        
        Skeleton.printReturn("");
    }

    @Override
    public int getPrice() {
        Skeleton.printCall("ThrowerHead", "getPrice()");
        
        // Adjunk neki egy egyedi árat, pl. 600
        int price = 600;
        
        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}