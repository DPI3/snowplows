import controller.*;
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
        assetManager = new AssetManager();
        assetManager.preloadAssets();

        // SoundManager inicializálása
        soundManager = new SoundManager();

        // StoreController inicializálása
        storeController = new StoreController();
        
        // GameScreen inicializálása a StoreController játékosának szerepkörével és boltjával
        Role role = storeController.getRole();
        Store store = storeController.getStore();
        GameScreen gameScreen = new GameScreen(role, store);
        
        StoreScreen storeScreen= new StoreScreen(storeController);

        // ScreenController inicializálása a nézetek segítségével
        // Megjegyzés: GameController nem létezik az alkalmazásban, így null-t adunk át
        screenController = new ScreenController(gameScreen, null, storeScreen);

        // StoreScreen és StoreController összekapcsolása
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
