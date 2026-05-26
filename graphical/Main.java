import controller.AssetManager;
import controller.GameController;
import controller.ScreenController;
import controller.SoundManager;
import controller.StoreController;
import java.util.ArrayList;
import java.util.List;
import src.*;
import view.GameScreen;
import view.StoreScreen;

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
        CleanerRole cleanerRole1 = new CleanerRole("Cleaner1", 2000, new Snowplow("snowplow1",null,0, new ThrowerHead()));
        CleanerRole cleanerRole2 = new CleanerRole("Cleaner2", 2000, new Snowplow("snowplow2",null,0, new ThrowerHead()));
        BusdriverRole busdriverRole1= new BusdriverRole("Busdriver1", new Bus("bus1", null, 0, null, null), 1000,0);
        BusdriverRole busdriverRole2= new BusdriverRole("Busdriver2", new Bus("bus2", null, 0, null, null), 1000,0);
       
        Player player1 = new Player(1, "Player1", cleanerRole1, busdriverRole1);
        Player player2 = new Player(2, "Player2", cleanerRole2, busdriverRole2);
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);
        java.util.List<Vehicle> vehicles = new java.util.ArrayList<>();
        vehicles.add(cleanerRole1.getSnowplow());
        vehicles.add(cleanerRole2.getSnowplow());
        Game game = new Game(0, 30, vehicles, players);
        // StoreController inicializálása
        storeController = new StoreController();

        Store store = storeController.getStore();
        GameScreen gameScreen = new GameScreen(cleanerRole1, store);
        StoreScreen storeScreen = new StoreScreen(storeController, cleanerRole1);
        
        GameController gameController = new GameController(game, gameScreen);

        screenController = new ScreenController(gameScreen, gameController, storeScreen);
        gameController.setScreenController(screenController);
        gameScreen.setGameController(gameController);

        storeController.setStoreScreen(storeScreen);
        storeController.setGameController(gameController);
        storeScreen.setScreenController(screenController);

        // Kezdeti képernyő megjelenítése
        screenController.showMenu();
        
        // SoundManager inicializálása (háttérzene lejátszása, ha szükséges)
        soundManager.playMusic("background");

        soundManager.playEffect("click");
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
