package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;
import src.Game;

/**
 * Billentyűzet-vezérlő osztály, amely a {@link KeyListener} interfészt implementálja.
 * A lenyomott billentyűket az {@link InputMapper} segítségével műveletekké alakítja,
 * majd továbbítja a {@link GameController}-nek.
 */
public class KeyboardController implements KeyListener {
    private final GameController controller;
    private final Game game;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final InputMapper inputMapper = new InputMapper();

    private boolean enabled = true;

    /**
     * Létrehozza a billentyűzet-vezérlőt a megadott játékvezérlővel és játék példánnyal.
     *
     * @param controller a játékvezérlő, amelynek a bemeneti eseményeket továbbítja
     * @param game a játék példány
     */
    public KeyboardController(GameController controller, Game game) {
        this.controller = controller;
        this.game = game;
    }

    /**
     * Billentyű lenyomásának kezelése. Ha a vezérlő engedélyezett és a billentyű
     * még nincs lenyomva, a megfelelő műveletet továbbítja.
     *
     * @param e a billentyűesemény
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (!enabled) {
            return;
        }

        int keyCode = e.getKeyCode();

        if (pressedKeys.contains(keyCode)) {
            return;
        }

        pressedKeys.add(keyCode);

        InputAction action = inputMapper.getAction(keyCode);

        if (isMovementAction(action)) {
            handleMovementKey(action);
        } else {
            handleActionKey(action);
        }
    }

    /**
     * Billentyű felengedésének kezelése. Eltávolítja a billentyűt a lenyomott billentyűk halmazából.
     *
     * @param e a billentyűesemény
     */
    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    /**
     * Karakter begépelésének kezelése. Nem használt, mert a vezérlés billentyűkód alapján történik.
     *
     * @param e a billentyűesemény
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Megvizsgálja, hogy egy adott billentyű jelenleg le van-e nyomva.
     *
     * @param keyCode a vizsgálandó billentyű kódja
     * @return {@code true}, ha a billentyű le van nyomva
     */
    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    /**
     * Engedélyezi vagy letiltja a billentyűzet-vezérlőt.
     * Letiltáskor a lenyomott billentyűk halmaza kiürül.
     *
     * @param enabled {@code true} az engedélyezéshez, {@code false} a letiltáshoz
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            pressedKeys.clear();
        }
    }

    /**
     * Meghatározza, hogy a megadott művelet mozgási művelet-e.
     *
     * @param action a vizsgálandó művelet
     * @return {@code true}, ha a művelet mozgás vagy megállás
     */
    private boolean isMovementAction(InputAction action) {
        return action == InputAction.MOVE_UP
                || action == InputAction.MOVE_DOWN
                || action == InputAction.MOVE_LEFT
                || action == InputAction.MOVE_RIGHT
                || action == InputAction.STOP;
    }

    /**
     * Mozgási billentyű kezelése: továbbítja a műveletet a játékvezérlőnek.
     *
     * @param action a mozgási művelet
     */
    private void handleMovementKey(InputAction action) {
        controller.handleInputAction(action);
    }

    /**
     * Egyéb (nem mozgási) billentyű kezelése: továbbítja a műveletet a játékvezérlőnek.
     *
     * @param action az egyéb művelet
     */
    private void handleActionKey(InputAction action) {
        controller.handleInputAction(action);
    }
}
