/**
 * A Head absztrakt osztaly a hokotrok kulonbozo fejtipusainak kozos alapja.
 *
 * A leszarmozott osztalyok hatarozzak meg, hogy a fej hogyan tisztitja
 * az utat kulonbozo allapotok eseten.
 */
public abstract class Head implements Buyable{
    /**
     * Vegrehajtja a sav tisztitasat a fej tipusanak megfelelo modon.
     *
     * @param lane a tisztitando sav
     * @param snowplow a muveletet vegzo hokotro
     */
    public abstract void clean(Lane lane, Snowplow snowplow);
}