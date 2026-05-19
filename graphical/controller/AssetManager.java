package controller;

import java.awt.*;
import java.io.File;
import java.util.Map;

public class AssetManager {
    private static AssetManager instance;
    private Map<String, Image> imageCache;
    private Map<String, Font> fontCache;
    private Map<String, Color> colorCache;

    private AssetManager() {
        imageCache = new java.util.HashMap<>();
        fontCache = new java.util.HashMap<>();
        colorCache = new java.util.HashMap<>();
    }

    public static synchronized AssetManager getInstance() {
        if (instance == null) instance = new AssetManager();
        return instance;
    }

    public Image getImage(String path) {
        return imageCache.get(path);
    }

    public Font getFont(String path){
        return fontCache.get(path);
    }

    public Color getColor(String name){
        return colorCache.get(name);
    }

    public void preloadAssets() {
        loadCustomFont();
        putColors();
    }

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

    private void putColors(){
        colorCache.put("TEXT_COLOR", Color.decode("#E2E874"));
        colorCache.put("PINK_COLOR", Color.decode("#EE8695"));
        colorCache.put("DARK_SHADOW",  new Color(25, 25, 30));
    }
}
