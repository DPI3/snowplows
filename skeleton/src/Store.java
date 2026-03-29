package skeleton.src;
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
    public Store(List<Buyable> inventory){
        Skeleton.printCall("Store", "Store(inventory)");
        this.inventory = inventory;
        Skeleton.printReturn("");
    }

    /**
     * Megprobalja megvasarolni a kivant elemet a boltbol.
     *
     * @param cleanerRole a vasarlast vegzo takarito szerepkor
     * @param item a megvasarolni kivant elem
     * @return true, ha a vasarlas sikeres volt
     */
    public boolean buy(CleanerRole cleanerRole, Buyable item){
        Skeleton.printCall("Store", "buy(cleanerRole, item)");

        int price = item.getPrice();
        int money = cleanerRole.getMoney();
        int decision = Skeleton.requestInput("Elegendő a pénz a vásárláshoz? (1: Igen, 2: Nem)");

        if (decision == 1 && money >= price && inventory.contains(item)) {
            cleanerRole.decreaseMoney(price);
            if (item instanceof Head) {
                cleanerRole.addHead((Head) item);
            } else {
                int biokeroseneAmount = 10;
                cleanerRole.addBiokerosene(biokeroseneAmount);
            }
            Skeleton.printState("Sikeres vásárlás.");
            Skeleton.printReturn("true");
            return true;
        }

        Skeleton.printState("Sikertelen vásárlás.");
        Skeleton.printReturn("false");
        return false;
    }
}