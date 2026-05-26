package controller;

import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A játék fő ciklusa, amely időzítő segítségével periodikusan meghívja a {@link GameController#tick()} metódust.
 * Az {@link ActionListener} interfészt implementálja a Swing {@link Timer} eseményeinek kezeléséhez.
 */
public class GameLoop implements ActionListener {
    private final Timer timer;
    private final GameController controller;
    private final int delay;

    /**
     * Létrehozza a játékciklust a megadott vezérlővel és késleltetéssel.
     *
     * @param controller a játékvezérlő, amelynek a {@code tick()} metódusa meghívásra kerül
     * @param delay az időzítő késleltetése milliszekundumban
     */
    public GameLoop(GameController controller, int delay) {
        this.controller = controller;
        this.delay = delay;
        this.timer = new Timer(delay, this);
    }

    /**
     * Elindítja a játékciklust, ha még nem fut.
     */
    public void start() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * Megállítja a játékciklust, ha éppen fut.
     */
    public void stop() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    /**
     * Visszaadja, hogy a játékciklus jelenleg fut-e.
     *
     * @return {@code true}, ha a játékciklus fut
     */
    public boolean isRunning() {
        return timer.isRunning();
    }

    /**
     * Visszaadja az időzítő késleltetését milliszekundumban.
     *
     * @return a késleltetés milliszekundumban
     */
    public int getDelay() {
        return delay;
    }

    /**
     * Az időzítő által kiváltott esemény kezelése. Meghívja a vezérlő {@code tick()} metódusát.
     *
     * @param e az esemény objektum
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        controller.tick();
    }
}
