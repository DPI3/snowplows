import controller.*;
import controller.AssetManager;
import controller.ScreenController;
import controller.SoundManager;
import controller.StoreController;
import view.GameScreen;
import view.StoreScreen;
import src.*;
import view.*;

/**
 * A Main osztály az alkalmazás belépési pontja.
 * Inicializálja és összehangoztassa az összes szükséges komponenst:
 * - ScreenController: képernyők kezelése
 * - AssetManager: képek és betűtípusok kezelése
 * - SoundManager: hangok kezelése
 * - StoreController: az üzlet logikája
 */
public class Main {
    private final ScreenController screenController;
    private final AssetManager assetManager;
    private final SoundManager soundManager;
    private final StoreController storeController;

    /**
     * Az alkalmazás inicializálása és indítása.
     */
    public Main() {
        // AssetManager inicializálása
        assetManager = AssetManager.getInstance();
        assetManager.preloadAssets();

        // SoundManager inicializálása
        soundManager = new SoundManager();

        // StoreController inicializálása
        storeController = new StoreController();

        Role role = storeController.getRole();
        Store store = storeController.getStore();
        GameScreen gameScreen = new GameScreen(role, store);
        StoreScreen storeScreen = new StoreScreen(storeController);

        java.util.List<Vehicle> vehicles = new java.util.ArrayList<>();
        java.util.List<Player> players = new java.util.ArrayList<>();

        if (role instanceof CleanerRole) {
            vehicles.add(((CleanerRole) role).getSnowplow());
        }
        players.add(new Player(1, "Player", role));

        Game game = new Game(0, 30, vehicles, players);
        GameController gameController = new GameController(game, gameScreen);

        screenController = new ScreenController(gameScreen, gameController, storeScreen);
        gameController.setScreenController(screenController);
        gameScreen.setGameController(gameController);

        storeController.setStoreScreen(storeScreen);
        storeScreen.setScreenController(screenController);

        // Kezdeti képernyő megjelenítése
        screenController.showMenu();
        
        // SoundManager inicializálása (háttérzene lejátszása, ha szükséges)
        soundManager.playMusic("background");
    }

    /**
     * Az alkalmazás főmetódusa.
     * 
     * @param args parancssori argumentumok
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> new Main());
    }
}
