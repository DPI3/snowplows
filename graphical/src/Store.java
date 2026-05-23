package src;
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

    /** Nyitva van-e a bolt. */
    private boolean open = false;

    /** Aktuálisan kiválasztott termék neve. */
    private String selectedItem = null;

    /**
     * A bolt kinyitására szolgáló metódus.
     */
    public void openStore()              { this.open = true; }

    /**
     * Megadja, hogy a bolt nyitva van-e.
     * 
     * @return true, ha a bolt nyitva van; false ha a bolt zárva van
     */
    public boolean isOpen()              { return open; }

    /**
     * Visszaadja a kiválasztott elemet.
     * 
     * @return a kiválasztott elem
     */
    public String getSelectedItem()      { return selectedItem; }

    /**
     * Új kiválasztott elem beállítása
     * 
     * @param s az új kiválasztott elem
     */
    public void setSelectedItem(String s){ this.selectedItem = s; }

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
        /*if (!inventory.contains(item)) {
            return false; 
        }*/

        int price = item.getPrice();

        if (cleanerRole.getMoney() >= price) {
            
            cleanerRole.decreaseMoney(price);
            
            if (item instanceof Head) {
                cleanerRole.addOwnedHead((Head) item);
                cleanerRole.getSnowplow().changeHead((Head) item);
            }

            //inventory.remove(item);
            
            return true; 
        }

        return false;
    }

    public boolean buyBiokerosene(CleanerRole cleanerRole){
        int price = 10;

        if(cleanerRole.getMoney() >= price){
            cleanerRole.decreaseMoney(price);
            cleanerRole.addBiokerosene(10);
            return true;
        }

        return false;
    }

    public boolean buySalt(CleanerRole cleanerRole){
        int price = 10;

        if(cleanerRole.getMoney() >= price){
            cleanerRole.decreaseMoney(price);
            cleanerRole.addSalt(10);
            return true;
        }

        return false;
    }

    public boolean buyGravel(CleanerRole cleanerRole){
        int price = 10;

        if(cleanerRole.getMoney() >= price){
            cleanerRole.decreaseMoney(price);
            cleanerRole.addGravel(10);
            return true;
        }

        return false;
    }

    public boolean buySnowplow(CleanerRole cleanerRole){
        int price=150;
        if(cleanerRole.getMoney() >= price){
             cleanerRole.decreaseMoney(price);
             cleanerRole.changeMoney(200);
             return true;
        }
        return false;
    }
}