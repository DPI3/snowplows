package skeleton.src;

/**
 * A DragonHead a hókotró dragon fejének megvalósítása.
 * 
 * A Dragon fej nagyterjedelmű hó elmozdálásra képes, és hatékonyan működik
 * az utakat takarító hókotrók fejeiként. Különféle útállapotban működhet,
 * és alapvető hótakarítási feladatokra alkalmas.
 */
public class DragonHead extends Head {

    /**
     * Megtisztítja a megadott sávot a hó eltávolításával.
     *
     * @param lane a takarítandó sáv
     * @param snowplow a takarítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow){
        Skeleton.printCall("DragonHead", "clean(lane, snowplow)");
        
        // Itt végezzük el a takarítást...
        
        Skeleton.printReturn(""); 
        // Mivel ez 'void', itt nem kell return érték, DE 
        // ha véletlenül átírtad a fejlécet valami másra, akkor kell!
    }
    
    // KERESD EZT A RÉSZT: Ha van olyan metódusod, ami nem void, 
    // például egy típuslekérdezés:
    public String getHeadType() {
        Skeleton.printCall("DragonHead", "getHeadType()");
        Skeleton.printReturn("DragonHead");
        return "DragonHead"; 

    }

    /**
     * Visszaadja a Dragon fejnek az ára.
     *
     * @return a fej ára
     */
    @Override
    public int getPrice() {
        Skeleton.printCall("DragonHead", "getPrice()");
        
        // Adjunk vissza egy tetszőleges árat (pl. 500 arany/pont/dollár)
        int price = 500;
        
        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}