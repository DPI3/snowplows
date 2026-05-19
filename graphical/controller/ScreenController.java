package controller;

import javax.swing.JFrame;
import view.*;

public class ScreenController {
    private final SnowplowMenu menuScreen;
    private final GameScreen gameScreen;
    private final SettingsScreen settingsScreen;
    @SuppressWarnings("unused")
    private final StoreScreen storeScreen;
    private JFrame currentScreen;
    private final GameController gameController;
     
    /**
     * ScreenController konstruktor a szükséges képernyőkkel.
     * A GameController opcionális lehet, ha nem szükséges az alkalmazásban.
     *
     * @param menuScreen a menü képernyő
     * @param gameScreen a játék képernyő
     * @param gameController a játék kontroler (opcionális)
     */
    public ScreenController(GameScreen gameScreen, GameController gameController, StoreScreen storeScreen) {
        this.menuScreen = new SnowplowMenu(this);
        this.gameScreen = gameScreen;
        this.gameController = gameController;
        this.settingsScreen = new SettingsScreen(this);
        this.storeScreen = storeScreen;
        this.currentScreen = menuScreen;
    }

    public GameController getGameController(){
        return gameController;
    }

    public void showMenu(){
        currentScreen.setVisible(false);
        menuScreen.setVisible(true);
        currentScreen=menuScreen;
    }

    public void showGame(){
        currentScreen.setVisible(false);
        gameScreen.setVisible(true);
        currentScreen=gameScreen;
    }

    public void showSettings(){
        currentScreen.setVisible(false);
        settingsScreen.setVisible(true);
        currentScreen=settingsScreen;
    }

    public void showStore(){
        currentScreen.setVisible(false);
        storeScreen.setVisible(true);
        currentScreen=storeScreen;
    }

    public void exitApplication(){
        System.exit(0);
    }

}
