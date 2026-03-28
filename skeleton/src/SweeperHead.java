package skeleton.src;
/**
 * A SweeperHead a hokotro sepro fejenek megvalositasa.
 *
 * A sepro fej elsosorban a konnyebb horetgek eltavolitasara alkalmas,
 * es segit az utfelulet gyors megtisztitasaban.
 */
public class SweeperHead extends Head{

    /**
     * Megtisztitja a megadott savot sepro fejjel.
     *
     * @param lane a takaritando sav
     * @param snowplow a muveletet vegzo hokotro
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow){
        Skeleton.printCall("SweeperHead", "clean(lane, snowplow)");
        // Takarítás logikája...
        Skeleton.printReturn("");
    }

    /**
     * Visszaadja a sepro fej arat.
     *
     * @return a fej ara
     */
    @Override
    public int getPrice() {
        Skeleton.printCall("SweeperHead", "getPrice()");
        
        int price = 300; // Vagy bármilyen összeg
        
        Skeleton.printReturn(String.valueOf(price));
        return price; 
    }
}