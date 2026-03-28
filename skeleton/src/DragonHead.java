package skeleton.src;

/**
 * Represents a specialized dragon-fire cleaning head for the snowplow.
 */
public class DragonHead extends Head {

    @Override
    public void clean(Lane lane, Snowplow snowplow) {
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

    @Override
    public int getPrice() {
        Skeleton.printCall("DragonHead", "getPrice()");
        
        // Adjunk vissza egy tetszőleges árat (pl. 500 arany/pont/dollár)
        int price = 500;
        
        Skeleton.printReturn(String.valueOf(price));
        return price;
    }
}