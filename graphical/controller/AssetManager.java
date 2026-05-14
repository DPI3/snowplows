package controller;

import java.awt.Font;
import java.awt.Image;
import java.util.Map;

public class AssetManager {
    private Map<String, Image> imageCache;
    private Map<String, Font> fontCache;

    public Image getImage(String path) {
        // Kép betöltése és cache-elése
        return imageCache.get(path);
    }

    public Font getFont(String path){
        // Betűtípus betöltése és cache-elése
        return fontCache.get(path);
    }

    public void preloadAssets() {
        // Előre betöltheti a szükséges képeket és betűtípusokat
    }
}
