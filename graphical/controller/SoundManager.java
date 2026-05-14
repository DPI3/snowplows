package controller;

import java.util.Map;
import javax.sound.sampled.Clip;

public class SoundManager {
    private Clip backgroundMusic;
    private Map<String, Clip> soundEffects;

    public void playMusic(String id) {
        if (backgroundMusic != null && backgroundMusic.isRunning()) {
            backgroundMusic.stop();
        }
        backgroundMusic = soundEffects.get(id);
        if (backgroundMusic != null) {
            backgroundMusic.setFramePosition(0);
            backgroundMusic.start();
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
