/**
 * A SaltSpreaderHead a hokotro soszoro fejenek megvalositasa.
 *
 * A soszoro fej so kijuttatasaval segit a jegesedes csokkenteseben,
 * illetve a felulet biztonsagosabba teteleben.
 */
public class SaltSpreaderHead extends Head{

    /**
     * Kezeli a megadott savot soszorassal.
     *
     * @param lane a kezelendo sav
     * @param snowplow a muveletet vegzo hokotro
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow){

    }

    /**
     * Visszaadja a soszoro fej arat.
     *
     * @return a fej ara
     */
    @Override
    public int getPrice(){
        return 0;
    }
}