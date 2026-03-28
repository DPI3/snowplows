package skeleton.src;

/**
 * Represents the icebreaker head for the snowplow.
 */
public class IcebreakerHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("IcebreakerHead", "clean(lane, snowplow)");
        // Tisztítás logikája...
        Skeleton.printReturn("");
    }

    @Override
    public int getPrice() {
        Skeleton.printCall("IcebreakerHead", "getPrice()");
        
        int price = 800; // Vagy amennyibe kerül
        
        Skeleton.printReturn(String.valueOf(price));
        return price; 
    }
}