package skeleton.src;
/**
 * A ThrowerHead a hokotro hohajito fejenek megvalositasa.
 *
 * A hohajito fej a felgyult hot az utrol eltavolitja,
 * es segit az ut gyors felszabaditasaban.
 */
public class ThrowerHead extends Head{

    /**
     * Megtisztitja a megadott savot a ho eltavolitasaval.
     *
     * @param lane a takaritando sav
     * @param snowplow a muveletet vegzo hokotro
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("ThrowerHead", "clean(lane, snowplow)");
        
        // A hófúvó fej eltávolítja a havat az útról
        
        Skeleton.printReturn("");
    }

    /**
     * Visszaadja a hohajito fej arat.
     *
     * @return a fej ara
     */
    @Override
    public int getPrice() {
        Skeleton.printCall("ThrowerHead", "getPrice()");
        
        // Adjunk neki egy egyedi árat, pl. 600
        int price = 600;
        
        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}