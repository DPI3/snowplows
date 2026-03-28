package skeleton.src;

/**
 * Az IcebreakerHead a hókotró jégtörő fejének megvalósítása.
 * 
 * A jégtörő fej kifejezetten a tört jég és jégkéreg összetörésére képes.
 * Más hókotrófejekkel ellentétben, ez a fej speciálisan hatékony a kemény felületeken,
 * és az utakat veszélyes jégállapotból visszaállítja használható állapotba.
 */
public class IcebreakerHead extends Head {

    /**
     * Megtisztítja a megadott sávot a jég eltávolításával.
     *
     * @param lane a takarítandó sáv
     * @param snowplow a takarítást végző hókotró
     */
    @Override
    public void clean(Lane lane, Snowplow snowplow) {
        Skeleton.printCall("IcebreakerHead", "clean(lane, snowplow)");
        // Tisztítás logikája...
        Skeleton.printReturn("");
    }

    /**
     * Visszaadja a jégtörő fejnek az ára.
     *
     * @return a fej ára
     */
    @Override
    public int getPrice() {
        Skeleton.printCall("IcebreakerHead", "getPrice()");
        
        int price = 800; // Vagy amennyibe kerül
        
        Skeleton.printReturn(String.valueOf(price));
        return price; 
    }
}