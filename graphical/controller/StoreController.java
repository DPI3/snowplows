package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import src.*;
import view.StoreScreen;


public class StoreController {
    private Store store;
    private StoreScreen storeScreen;
    private java.util.Set<String> boughtHeads = new java.util.HashSet<>();
    private GameController gameController;

    public int getMoney(){
        if(gameController.getRole() instanceof CleanerRole){
            return ((CleanerRole) gameController.getRole()).getMoney();
        }
        if(gameController.getRole() instanceof BusdriverRole){
            return ((BusdriverRole) gameController.getRole()).getMoney();
        }
        return 0;
    }

    public void setStoreScreen(StoreScreen s){
        storeScreen=s;
    }

    public Store getStore() {
        return store;
    }

        /**
     * Ellenőrzi, hogy a játékos meg tudja-e engedni az adott árat.
     *
     * @param price az ellenőrizendő ár
     * @return igaz, ha a játékosnak van elég pénze
     */
    public boolean canAfford(int price) {
        return getMoney() >= price;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public StoreController() {
        store= new Store(null);
    }


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

    private Buyable ConvertToBuyable(String itemId){
        if (itemId.equals("GRAVELSPREAD")) return new GravelSpreaderHead();
        if (itemId.equals("SALTSPREAD")) return new SaltSpreaderHead();
        if (itemId.equals("ICEBREAKER")) return new IcebreakerHead();
        if (itemId.equals("THROWER")) return new ThrowerHead();
        if (itemId.equals("SWEEPER")) return new SweeperHead();
        if (itemId.equals("DRAGON")) return new DragonHead();
        return null;
    }

    public CleanerRole getCleanerRole() {
        return (CleanerRole) gameController.getRole();
    }

    public int getItemPrice(String item) {
        if (item.equals("BIOKEROZIN")) return 10;
        if (item.equals("SALT")) return 10;
        if (item.equals("GRAVEL")) return 10;
        if (item.equals("SNOWPLOW")) return 150;

        Buyable buyable = ConvertToBuyable(item);
        return buyable != null ? buyable.getPrice() : 0;
    }
}
