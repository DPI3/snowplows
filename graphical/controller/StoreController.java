package controller;

import java.lang.ref.Cleaner;
import java.util.ArrayList;
import java.util.List;

import src.*;
import view.StoreScreen;


public class StoreController {
    private Store store;
    private Player player;
    private StoreScreen storeScreen;
    private CleanerRole c;

    public int getMoney(){
        return c.getMoney();
    }

    public void setStoreScreen(StoreScreen s){
        storeScreen=s;
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
                return true;
            }
        }
        if(item.equals("SALT")){
            if(store.buySalt(c)){
                storeScreen.updateMoney(getMoney());
                return true;
            }
        }
        if(item.equals("GRAVEL")){
            if(store.buyGravel(c)){
                storeScreen.updateMoney(getMoney());
                return true;
            }
        }
        if(item.equals("SNOWPLOW")){
            if(store.buySnowplow(c)){
                storeScreen.updateMoney(getMoney());
                return true;
            }
        }
        if(store.buy(c, ConvertToBuyable(item))){
            storeScreen.updateMoney(getMoney());
            return true;
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
}
