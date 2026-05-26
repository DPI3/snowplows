package controller;

import javax.swing.JFrame;
import view.*;
import src.CleanerRole;
import src.BusdriverRole;

/**
 * Képernyővezérlő osztály, amely a különböző képernyők (menü, játék, beállítások, bolt)
 * közötti navigációt kezeli.
 */
public class ScreenController {
    private final SnowplowMenu menuScreen;
    private final GameScreen gameScreen;
    private final SettingsScreen settingsScreen;
    @SuppressWarnings("unused")
    private final StoreScreen storeScreen;
    private JFrame currentScreen;
    private final GameController gameController;

    /**
     * Létrehozza a képernyővezérlőt a megadott képernyőkkel és játékvezérlővel.
     * Automatikusan létrehozza a menü és beállítások képernyőket.
     *
     * @param gameScreen a játék képernyő
     * @param gameController a játékvezérlő
     * @param storeScreen a bolt képernyő
     */
    public ScreenController(GameScreen gameScreen, GameController gameController, StoreScreen storeScreen) {
        this.menuScreen = new SnowplowMenu(this);
        this.gameScreen = gameScreen;
        this.gameController = gameController;
        this.settingsScreen = new SettingsScreen(this);
        this.storeScreen = storeScreen;
        this.currentScreen = menuScreen;
    }

    /**
     * Visszaadja a játékvezérlőt.
     *
     * @return a játékvezérlő példány
     */
    public GameController getGameController(){
        return gameController;
    }

    /**
     * Megjeleníti a főmenü képernyőt, elrejtve az aktuális képernyőt.
     */
    public void showMenu(){
        currentScreen.setVisible(false);
        menuScreen.setVisible(true);
        currentScreen=menuScreen;
    }

    /**
     * Megjeleníti a játék képernyőt, elrejtve az aktuális képernyőt.
     * Ha a játék nem fut, automatikusan folytatja.
     */
    public void showGame(){
        currentScreen.setVisible(false);
        gameScreen.setVisible(true);
        currentScreen=gameScreen;
        gameScreen.requestFocusInWindow();
        if (gameController != null && !gameController.isRunning()) {
            gameController.resumeGame();
        }
    }

    /**
     * Megjeleníti a beállítások képernyőt, elrejtve az aktuális képernyőt.
     */
    public void showSettings(){
        currentScreen.setVisible(false);
        settingsScreen.setVisible(true);
        currentScreen=settingsScreen;
    }

    /**
     * Megjeleníti a bolt képernyőt, elrejtve az aktuális képernyőt.
     * Frissíti a bolt képernyőn megjelenített pénzösszeget a játékos aktuális szerepe alapján.
     */
    public void showStore(){
        currentScreen.setVisible(false);
        storeScreen.setVisible(true);
        int money=0;
        if(gameController.getRole() instanceof CleanerRole){
            money = ((CleanerRole) gameController.getRole()).getMoney();
        }
        if(gameController.getRole() instanceof BusdriverRole){
            money = ((BusdriverRole) gameController.getRole()).getMoney();
        }
        storeScreen.updateMoney(money);
        currentScreen=storeScreen;
    }

    /**
     * Kilép az alkalmazásból.
     */
    public void exitApplication(){
        System.exit(0);
    }

}
