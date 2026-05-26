package src;
import java.util.List;

/**
 * A Store osztály a játék boltját reprezentálja.
 * A bolt tárolja a megvásárolható elemeket (pl. fejek, járművek, anyagok),
 * és kezeli a vásárlási műveleteket a szerepkörök számára.
 */
public class Store{
    /** A bolt aktuális készlete. */
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
     * @return a kiválasztott elem neve
     */
    public String getSelectedItem()      { return selectedItem; }

    /**
     * Új kiválasztott elem beállítása.
     *
     * @param s az új kiválasztott elem neve
     */
    public void setSelectedItem(String s){ this.selectedItem = s; }

    /**
     * Store példány létrehozása megadott készlettel.
     *
     * @param inventory a bolt indulókészlete
     */
    public Store(List<Buyable> inventory) {
        this.inventory = inventory;
    }

    /**
     * Megpróbálja megvásárolni a kívánt elemet a boltból.
     * Ha a takarító szerepkörnek elegendő pénze van, az elem megvásárlásra kerül.
     *
     * @param cleanerRole a vásárlást végző takarító szerepkör
     * @param item a megvásárolni kívánt elem
     * @return true, ha a vásárlás sikeres volt; false egyébként
     */
    public boolean buy(CleanerRole cleanerRole, Buyable item) {
        int price = item.getPrice();

        if (cleanerRole.getMoney() >= price) {

            cleanerRole.decreaseMoney(price);

            if (item instanceof Head) {
                cleanerRole.addOwnedHead((Head) item);
                cleanerRole.getSnowplow().changeHead((Head) item);
            }

            return true;
        }

        return false;
    }

    /**
     * Biokerozin vásárlása a boltból a megadott takarító szerepkör számára.
     *
     * @param cleanerRole a vásárlást végző takarító szerepkör
     * @return true, ha a vásárlás sikeres volt; false egyébként
     */
    public boolean buyBiokerosene(CleanerRole cleanerRole){
        int price = 10;

        if(cleanerRole.getMoney() >= price){
            cleanerRole.decreaseMoney(price);
            cleanerRole.addBiokerosene(10);
            return true;
        }

        return false;
    }

    /**
     * Só vásárlása a boltból a megadott takarító szerepkör számára.
     *
     * @param cleanerRole a vásárlást végző takarító szerepkör
     * @return true, ha a vásárlás sikeres volt; false egyébként
     */
    public boolean buySalt(CleanerRole cleanerRole){
        int price = 10;

        if(cleanerRole.getMoney() >= price){
            cleanerRole.decreaseMoney(price);
            cleanerRole.addSalt(10);
            return true;
        }

        return false;
    }

    /**
     * Zúzottkő vásárlása a boltból a megadott takarító szerepkör számára.
     *
     * @param cleanerRole a vásárlást végző takarító szerepkör
     * @return true, ha a vásárlás sikeres volt; false egyébként
     */
    public boolean buyGravel(CleanerRole cleanerRole){
        int price = 10;

        if(cleanerRole.getMoney() >= price){
            cleanerRole.decreaseMoney(price);
            cleanerRole.addGravel(10);
            return true;
        }

        return false;
    }

    /**
     * Hókotró vásárlása a boltból a megadott takarító szerepkör számára.
     *
     * @param cleanerRole a vásárlást végző takarító szerepkör
     * @return true, ha a vásárlás sikeres volt; false egyébként
     */
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
