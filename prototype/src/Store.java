package prototype.src;
import java.util.List;
/**
 * A Store osztaly a jatek boltjat reprezentalja.
 *
 * A bolt tarolja a megvasarolhato elemeket (pl. fejek, jarmuvek, anyagok),
 * es kezeli a vasarlasi muveleteket a szerepkorok szamara.
 */
public class Store{
    /** A bolt aktualis keszlete. */
    private List<Buyable> inventory;

    /**
     * Store peldany letrehozasa megadott keszlettel.
     *
     * @param inventory a bolt indulokeszlete
     */
    public Store(List<Buyable> inventory) {
        this.inventory = inventory;
    }

    /**
     * Megprobalja megvasarolni a kivant elemet a boltbol.
     *
     * @param cleanerRole a vasarlast vegzo takarito szerepkor
     * @param item a megvasarolni kivant elem
     * @return true, ha a vasarlas sikeres volt
     */
    public boolean buy(CleanerRole cleanerRole, Buyable item) {
        if (!inventory.contains(item)) {
            return false; 
        }

        int price = item.getPrice();

        if (cleanerRole.getMoney() >= price) {
            
            cleanerRole.decreaseMoney(price);
            
            if (item instanceof Head) {
                cleanerRole.addHead((Head) item);
            } 
            /* * Ide jöhet a nyersanyagok (só, zuzalék, biokerozin) átadási logikája is, 
             * amennyiben azok különálló Buyable osztályként lettek implementálva a rendszerben:
             * else if (item instanceof SaltItem) { cleanerRole.addSalt(10); } 
             */

            inventory.remove(item);
            
            return true; 
        }

        return false;
    }
}