package controller;

import java.awt.*;
import java.io.File;
import java.util.Map;

/**
 * Erőforrás-kezelő osztály, amely a játékban használt képeket, betűtípusokat és színeket kezeli.
 * Singleton mintát alkalmaz, így egyetlen példányban létezik az alkalmazás futása során.
 */
public class AssetManager {
    private static AssetManager instance;
    private Map<String, Image> imageCache;
    private Map<String, Font> fontCache;
    private Map<String, Color> colorCache;

    /**
     * Privát konstruktor a Singleton minta biztosításához.
     * Inicializálja a kép-, betűtípus- és színgyorsítótárakat.
     */
    private AssetManager() {
        imageCache = new java.util.HashMap<>();
        fontCache = new java.util.HashMap<>();
        colorCache = new java.util.HashMap<>();
    }

    /**
     * Visszaadja az AssetManager egyetlen példányát. Ha még nem létezik, létrehozza.
     *
     * @return az AssetManager singleton példánya
     */
    public static synchronized AssetManager getInstance() {
        if (instance == null) instance = new AssetManager();
        return instance;
    }

    /**
     * Visszaad egy korábban betöltött képet az elérési útja alapján.
     *
     * @param path a kép elérési útja
     * @return a gyorsítótárban tárolt kép, vagy {@code null} ha nem található
     */
    public Image getImage(String path) {
        return imageCache.get(path);
    }

    /**
     * Visszaad egy korábban betöltött betűtípust az elérési útja alapján.
     *
     * @param path a betűtípus elérési útja
     * @return a gyorsítótárban tárolt betűtípus, vagy {@code null} ha nem található
     */
    public Font getFont(String path){
        return fontCache.get(path);
    }

    /**
     * Visszaad egy korábban regisztrált színt a neve alapján.
     *
     * @param name a szín neve
     * @return a gyorsítótárban tárolt szín, vagy {@code null} ha nem található
     */
    public Color getColor(String name){
        return colorCache.get(name);
    }

    /**
     * Előre betölti az összes szükséges erőforrást (betűtípusok, színek).
     */
    public void preloadAssets() {
        loadCustomFont();
        putColors();
    }

    /**
     * Betölti a Silkscreen egyedi betűtípust fájlból, és különböző méretekben
     * regisztrálja a betűtípus-gyorsítótárban. Hiba esetén alapértelmezett betűtípust használ.
     */
    private void loadCustomFont() {
        try {
            File fontFile = new File("Silkscreen-Regular.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            fontCache.put("silkscreenTitle", customFont.deriveFont(Font.PLAIN, 28f));
            fontCache.put("silkscreenHeader", customFont.deriveFont(Font.PLAIN, 24f));
            fontCache.put("silkscreenNormal", customFont.deriveFont(Font.PLAIN, 20f));
            fontCache.put("silkscreenSmall", customFont.deriveFont(Font.PLAIN, 14f));
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus! Alapértelmezett lesz használva.");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            fontCache.put("silkscreenTitle", fallback.deriveFont(Font.PLAIN, 28f));
            fontCache.put("silkscreenHeader", fallback.deriveFont(Font.PLAIN, 24f));
            fontCache.put("silkscreenNormal", fallback.deriveFont(Font.PLAIN, 20f));
            fontCache.put("silkscreenSmall", fallback.deriveFont(Font.PLAIN, 14f));
        }
    }

    /**
     * Regisztrálja az alkalmazásban használt előre definiált színeket a szín-gyorsítótárban.
     */
    private void putColors(){
        colorCache.put("TEXT_COLOR", Color.decode("#E2E874"));
        colorCache.put("PINK_COLOR", Color.decode("#EE8695"));
        colorCache.put("DARK_SHADOW",  new Color(25, 25, 30));
    }
}
