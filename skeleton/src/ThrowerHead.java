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
    public void clean(Lane lane, Snowplow snowplow){

    }

    /**
     * Visszaadja a hohajito fej arat.
     *
     * @return a fej ara
     */
    @Override
    public int getPrice(){
        return 0;
    }
}