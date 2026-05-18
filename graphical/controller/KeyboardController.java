package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.Set;

public class KeyboardController implements KeyListener {
    private final GameController controller;
    private final Game game;
    private final Set<Integer> pressedKeys = new HashSet<>();
    private final InputMapper inputMapper = new InputMapper();

    private boolean enabled = true;

    public KeyboardController(GameController controller, Game game) {
        this.controller = controller;
        this.game = game;
    }

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

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Nem szükséges, mert a vezérlés keyCode alapján történik.
    }

    public boolean isKeyPressed(int keyCode) {
        return pressedKeys.contains(keyCode);
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        if (!enabled) {
            pressedKeys.clear();
        }
    }

    private boolean isMovementAction(InputAction action) {
        return action == InputAction.MOVE_UP
                || action == InputAction.MOVE_DOWN
                || action == InputAction.MOVE_LEFT
                || action == InputAction.MOVE_RIGHT
                || action == InputAction.STOP;
    }

    private void handleMovementKey(InputAction action) {
        controller.handleInputAction(action);
    }

    private void handleActionKey(InputAction action) {
        controller.handleInputAction(action);
    }
}