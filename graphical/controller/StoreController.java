package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import src.*;
import view.StoreScreen;

/**
 * A bolt vezérlője, amely a vásárlási logikát kezeli.
 * Kezeli a különböző tárgyak (fejek, anyagok, hókotró) megvásárlását,
 * és frissíti a bolt képernyőt a tranzakciók után.
 */
public class StoreController {
    private Store store;
    private StoreScreen storeScreen;
    private java.util.Set<String> boughtHeads = new java.util.HashSet<>();
    private GameController gameController;

    /**
     * Visszaadja a játékos aktuális pénzmennyiségét a szerepe alapján.
     *
     * @return a játékos pénze, vagy 0 ha nincs megfelelő szerep
     */
    public int getMoney(){
        if(gameController.getRole() instanceof CleanerRole){
            return ((CleanerRole) gameController.getRole()).getMoney();
        }
        if(gameController.getRole() instanceof BusdriverRole){
            return ((BusdriverRole) gameController.getRole()).getMoney();
        }
        return 0;
    }

    /**
     * Beállítja a bolt képernyő referenciáját.
     *
     * @param s a bolt képernyő
     */
    public void setStoreScreen(StoreScreen s){
        storeScreen=s;
    }

    /**
     * Visszaadja a bolt modell objektumot.
     *
     * @return a bolt példány
     */
    public Store getStore() {
        return store;
    }

    /**
     * Ellenőrzi, hogy a játékos meg tudja-e engedni az adott árat.
     *
     * @param price az ellenőrizendő ár
     * @return {@code true}, ha a játékosnak van elég pénze
     */
    public boolean canAfford(int price) {
        return getMoney() >= price;
    }

    /**
     * Beállítja a játékvezérlő referenciáját.
     *
     * @param gameController a játékvezérlő
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * Létrehozza a bolt vezérlőt egy új bolt példánnyal.
     */
    public StoreController() {
        store= new Store(null);
    }

    /**
     * Megvásárolja a megadott tárgyat. Kezeli a biokerozin, só, kavics, hókotró és fejek vásárlását.
     * Fejekből minden típusból csak egyet lehet vásárolni.
     *
     * @param item a megvásárolni kívánt tárgy azonosítója
     * @return {@code true}, ha a vásárlás sikeres volt
     */
    public boolean buyItem(String item){
        if(gameController.getRole() == null || !(gameController.getRole() instanceof CleanerRole)) return false;
        CleanerRole role = (CleanerRole) gameController.getRole();
        if(item.equals("BIOKEROZIN")){
            if(store.buyBiokerosene(role)){
                storeScreen.updateMoney(getMoney());
                storeScreen.updateStock(role.getSnowplow());
                return true;
            }
            return false;
        }

        if(item.equals("SALT")){
            if(store.buySalt(role)){
                storeScreen.updateMoney(getMoney());
                storeScreen.updateStock(role.getSnowplow());
                return true;
            }
            return false;
        }

        if(item.equals("GRAVEL")){
            if(store.buyGravel(role)){
                storeScreen.updateMoney(getMoney());
                storeScreen.updateStock(role.getSnowplow());
                return true;
            }
            return false;
        }
        if(item.equals("SNOWPLOW")){
            if(store.buySnowplow(role)){
                storeScreen.updateMoney(getMoney());
                return true;
            }
        }
        Buyable buyable = ConvertToBuyable(item);

        if (buyable instanceof Head) {
            String headName = buyable.getClass().getSimpleName();

            if (boughtHeads.contains(headName)) {
                JOptionPane.showMessageDialog(storeScreen, "Ebből a fejből már vettél egyet.");
                return false;
            }

            if (store.buy(role, buyable)) {
                boughtHeads.add(headName);
                storeScreen.updateMoney(getMoney());
                storeScreen.updateStock(role.getSnowplow());
                return true;
            }

            return false;
        }
        return false;
    }

    /**
     * A tárgy szöveges azonosítóját a megfelelő {@link Buyable} objektummá alakítja.
     *
     * @param itemId a tárgy szöveges azonosítója
     * @return a megfelelő {@link Buyable} példány, vagy {@code null} ha ismeretlen azonosító
     */
    private Buyable ConvertToBuyable(String itemId){
        if (itemId.equals("GRAVELSPREAD")) return new GravelSpreaderHead();
        if (itemId.equals("SALTSPREAD")) return new SaltSpreaderHead();
        if (itemId.equals("ICEBREAKER")) return new IcebreakerHead();
        if (itemId.equals("THROWER")) return new ThrowerHead();
        if (itemId.equals("SWEEPER")) return new SweeperHead();
        if (itemId.equals("DRAGON")) return new DragonHead();
        return null;
    }

    /**
     * Visszaadja a játékos takarító szerepét.
     *
     * @return a {@link CleanerRole} példány
     */
    public CleanerRole getCleanerRole() {
        return (CleanerRole) gameController.getRole();
    }

    /**
     * Visszaadja a megadott tárgy árát.
     *
     * @param item a tárgy szöveges azonosítója
     * @return a tárgy ára, vagy 0 ha ismeretlen tárgy
     */
    public int getItemPrice(String item) {
        if (item.equals("BIOKEROZIN")) return 10;
        if (item.equals("SALT")) return 10;
        if (item.equals("GRAVEL")) return 10;
        if (item.equals("SNOWPLOW")) return 150;

        Buyable buyable = ConvertToBuyable(item);
        return buyable != null ? buyable.getPrice() : 0;
    }
}
