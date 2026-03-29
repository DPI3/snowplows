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
    public void clean(Lane lane, Snowplow snowplow) {
        // A tesztelő által elvárt hívás naplózása
        Skeleton.printCall("DragonHead", "clean(l, sp)");
        
        // Meghívjuk a Lane-en a change metódust (az UML alapján paramétere int)
        lane.change(10); // A pontos szám a belső logikádtól függ
        
        // A szekvenciadiagramon lévő állapotváltozás kiírása
        Skeleton.printState("Recognize cleaning, add money (modify attribute)");
        
        // Visszatérés naplózása
        Skeleton.printReturn("");
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
        
        int price = 500; // Példa érték
        
        // A visszatérési értéket is jelezzük a logban
        Skeleton.printReturn(String.valueOf(price)); 
        return price;
    }
}