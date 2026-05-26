package controller;

import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.*;

public class SoundManager {
    private Clip backgroundMusic;
    private final Map<String, Clip> soundEffects = new HashMap<>();

    public SoundManager() {
        loadSound("background", "sounds/background.wav");
        loadSound("click", "sounds/click.wav");
    }

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

    public void stopMusic() {
        if (backgroundMusic != null) {
            backgroundMusic.stop();
        }
    }

    public void playEffect(String id) {
        Clip effect = soundEffects.get(id);

        if (effect != null) {
            effect.setFramePosition(0);
            effect.start();
        }
    }
}