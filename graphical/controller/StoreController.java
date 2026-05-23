package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import src.*;
import view.StoreScreen;


public class StoreController {
    private Store store;
    private Player player;
    private StoreScreen storeScreen;
    private CleanerRole c;
    private java.util.Set<String> boughtHeads = new java.util.HashSet<>();

    public int getMoney(){
        return c.getMoney();
    }

    public void setStoreScreen(StoreScreen s){
        storeScreen=s;
    }

    public Role getRole() {
        return c;
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

    public StoreController(){
        store= new Store(null);
        List<Role> roles = new ArrayList<>();
        
        c= new CleanerRole("Cleaner", 300, new Snowplow("snowplow",null,0, new SweeperHead()));
        roles.add(c);
        player= new Player(1, "Player", roles);
    }

    public boolean buyItem(String item){
        if(item.equals("BIOKEROZIN")){
            if(store.buyBiokerosene(c)){
                storeScreen.updateMoney(getMoney());
                storeScreen.updateStock(c.getSnowplow());
                return true;
            }
            return false;
        }

        if(item.equals("SALT")){
            if(store.buySalt(c)){
                storeScreen.updateMoney(getMoney());
                storeScreen.updateStock(c.getSnowplow());
                return true;
            }
            return false;
        }

        if(item.equals("GRAVEL")){
            if(store.buyGravel(c)){
                storeScreen.updateMoney(getMoney());
                storeScreen.updateStock(c.getSnowplow());
                return true;
            }
            return false;
        }
        if(item.equals("SNOWPLOW")){
            if(store.buySnowplow(c)){
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

            if (store.buy(c, buyable)) {
                boughtHeads.add(headName);
                storeScreen.updateMoney(getMoney());
                storeScreen.updateStock(c.getSnowplow());
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
        return c;
    }
}
