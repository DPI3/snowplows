package controller;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameLoop implements ActionListener {
    private final Timer timer;
    private final GameController controller;
    private final int delay;

    public GameLoop(GameController controller, int delay) {
        this.controller = controller;
        this.delay = delay;
        this.timer = new Timer(delay, this);
    }

    public void start() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    public void stop() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    public boolean isRunning() {
        return timer.isRunning();
    }

    public int getDelay() {
        return delay;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        controller.tick();
    }
}