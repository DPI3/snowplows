/**
 * A DragonHead a hókotró dragon fejének megvalósítása.
 * 
 * A Dragon fej nagyterjedelmű hó elmozdálásra képes, és hatékonyan működik
 * az utakat takarító hókotrók fejeiként. Különféle útállapotban működhet,
 * és alapvető hótakarítási feladatokra alkalmas.
 */
public class DragonHead extends Head{

    /**
     * Megtisztítja a megadott sávot a hó eltávolításával.
     *
     * @param lane a takarítandó sáv
     * @param snowplow a takarítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow){

    }

    /**
     * Visszaadja a Dragon fejnek az ára.
     *
     * @return a fej ára
     */
    @Override
    public int getPrice(){
        return 0;
    }
}