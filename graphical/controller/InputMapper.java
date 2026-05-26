package controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Billentyűkódok és bemeneti műveletek közötti leképezést kezelő osztály.
 * Alapértelmezett billentyűkiosztást tölt be a konstruktorban, amely később módosítható.
 */
public class InputMapper {
    private final Map<Integer, InputAction> keyBindings = new HashMap<>();

    /**
     * Létrehozza az InputMapper-t és betölti az alapértelmezett billentyűkiosztást.
     */
    public InputMapper() {
        loadDefaultBindings();
    }

    /**
     * Visszaadja a megadott billentyűkódhoz tartozó műveletet.
     *
     * @param keyCode a billentyű kódja
     * @return a hozzárendelt {@link InputAction}, vagy {@code null} ha nincs hozzárendelés
     */
    public InputAction getAction(int keyCode) {
        return keyBindings.get(keyCode);
    }

    /**
     * Beállít egy billentyűkód-művelet hozzárendelést.
     *
     * @param keyCode a billentyű kódja
     * @param action a hozzárendelni kívánt művelet
     */
    public void setBinding(int keyCode, InputAction action) {
        keyBindings.put(keyCode, action);
    }

    /**
     * Eltávolít egy billentyűkód-hozzárendelést.
     *
     * @param keyCode az eltávolítandó billentyűkód
     */
    public void removeBinding(int keyCode) {
        keyBindings.remove(keyCode);
    }

    /**
     * Betölti az alapértelmezett billentyűkiosztást (WASD, nyilak és egyéb gyorsbillentyűk).
     */
    private void loadDefaultBindings() {
        keyBindings.put(KeyEvent.VK_W, InputAction.MOVE_UP);
        keyBindings.put(KeyEvent.VK_UP, InputAction.MOVE_UP);

        keyBindings.put(KeyEvent.VK_S, InputAction.MOVE_DOWN);
        keyBindings.put(KeyEvent.VK_DOWN, InputAction.MOVE_DOWN);

        keyBindings.put(KeyEvent.VK_A, InputAction.MOVE_LEFT);
        keyBindings.put(KeyEvent.VK_LEFT, InputAction.MOVE_LEFT);

        keyBindings.put(KeyEvent.VK_D, InputAction.MOVE_RIGHT);
        keyBindings.put(KeyEvent.VK_RIGHT, InputAction.MOVE_RIGHT);

        keyBindings.put(KeyEvent.VK_SPACE, InputAction.STOP);
        keyBindings.put(KeyEvent.VK_B, InputAction.OPEN_STORE);
        keyBindings.put(KeyEvent.VK_ESCAPE, InputAction.OPEN_MENU);
        keyBindings.put(KeyEvent.VK_P, InputAction.PAUSE);
        keyBindings.put(KeyEvent.VK_ENTER, InputAction.CONFIRM);
        keyBindings.put(KeyEvent.VK_C, InputAction.CHANGE_HEAD);
        keyBindings.put(KeyEvent.VK_R, InputAction.RESTART);
    }
}
