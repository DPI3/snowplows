package controller;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;

/**
 * Hangkezelő osztály, amely a háttérzene és a hangeffektusok betöltését és lejátszását végzi.
 */
public class SoundManager {
    private Clip backgroundMusic;
    private final Map<String, Clip> soundEffects = new HashMap<>();

    /**
     * Létrehozza a hangkezelőt és betölti az alapértelmezett hangfájlokat
     * (háttérzene és kattintás hangeffektus).
     */
    public SoundManager() {
        loadSound("background", "sounds/background.wav");
        loadSound("click", "sounds/click.wav");
    }

    /**
     * Betölt egy hangfájlt a megadott elérési útról és eltárolja az azonosítójával.
     *
     * @param id a hang egyedi azonosítója
     * @param path a hangfájl elérési útja
     */
    private void loadSound(String id, String path) {
        try {
            AudioInputStream audioStream =
                AudioSystem.getAudioInputStream(new java.io.File(path));

            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);

            soundEffects.put(id, clip);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Elindítja a háttérzenét a megadott azonosítóval. Ha már szól háttérzene, előbb leállítja.
     * A zene folyamatosan ismétlődik.
     *
     * @param id a lejátszandó háttérzene azonosítója
     */
    public void playMusic(String id) {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }

        backgroundMusic = soundEffects.get(id);

        if (backgroundMusic != null) {
            backgroundMusic.setFramePosition(0);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    /**
     * Leállítja az aktuálisan lejátszott háttérzenét.
     */
    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    /**
     * Lejátszik egy hangeffektust a megadott azonosítóval. Az effektus az elejétől indul.
     *
     * @param id a lejátszandó hangeffektus azonosítója
     */
    public void playEffect(String id) {
        Clip effect = soundEffects.get(id);

        if (effect != null) {
            effect.setFramePosition(0);
            effect.start();
        }
    }
}
