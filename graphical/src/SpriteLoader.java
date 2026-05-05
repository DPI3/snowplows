package src;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class SpriteLoader {
    private static BufferedImage busSprite;
    private static BufferedImage snowplowSprite;
    private static BufferedImage carPinkSprite;
    private static BufferedImage carYellowSprite;
    private static BufferedImage bgCity;

    private static boolean loaded = false;

    public static void load() {
        if (loaded) return;
        loaded = true;
        String base = "res/";
        busSprite = loadImg(base + "bus.png", 40, 24);
        snowplowSprite = loadImg(base + "snowplow.png", 40, 28);
        carPinkSprite = loadImg(base + "car_pink.png", 28, 18);
        carYellowSprite = loadImg(base + "car_yellow.png", 28, 18);
        bgCity = loadImgRaw(base + "bg_city.png");
    }

    private static BufferedImage loadImg(String path, int w, int h) {
        BufferedImage raw = loadImgRaw(path);
        if (raw == null) return null;
        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g.drawImage(raw, 0, 0, w, h, null);
        g.dispose();
        return scaled;
    }

    private static BufferedImage loadImgRaw(String path) {
        try {
            File f = new File(path);
            if (f.exists()) return ImageIO.read(f);
        } catch (Exception e) {
            System.err.println("Sprite not found: " + path);
        }
        return null;
    }

    public static BufferedImage getBus() { return busSprite; }
    public static BufferedImage getSnowplow() { return snowplowSprite; }
    public static BufferedImage getCarPink() { return carPinkSprite; }
    public static BufferedImage getCarYellow() { return carYellowSprite; }
    public static BufferedImage getBgCity() { return bgCity; }
}
