package controller;

import src.Game;
import src.Role;
import view.GameScreen;

public class GameController {
    private final Game game;
    private final GameScreen gameScreen;
    private final GameLoop gameLoop;
    private final KeyboardController keyboardController;
    private final VehicleController vehicleController;

    private ScreenController screenController;
    private boolean running;

    public GameController(Game game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.vehicleController = new VehicleController(game);
        this.keyboardController = new KeyboardController(this, game);
        this.gameLoop = new GameLoop(this, 1000);
        this.running = false;

        gameScreen.addKeyListener(keyboardController);
        gameScreen.setFocusable(true);
        gameScreen.requestFocusInWindow();
    }

    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    public void startGame() {
        running = true;
        keyboardController.setEnabled(true);
        gameLoop.start();
        gameScreen.requestFocusInWindow();
    }

    public void pauseGame() {
        running = false;
        gameLoop.stop();
    }

    public void resumeGame() {
        running = true;
        gameLoop.start();
        gameScreen.requestFocusInWindow();
    }

    public void stopGame() {
        running = false;
        keyboardController.setEnabled(false);
        gameLoop.stop();
    }

    public void togglePause() {
        if (running) {
            pauseGame();
        } else {
            resumeGame();
        }
    }

    public void tick() {
        game.tick();
        vehicleController.updateControlledVehicle();
        refreshView();
    }

    public void handleInputAction(InputAction action) {
        if (action == null) {
            return;
        }

        switch (action) {
            case MOVE_UP -> vehicleController.moveUp();
            case MOVE_DOWN -> vehicleController.moveDown();
            case MOVE_LEFT -> vehicleController.moveLeft();
            case MOVE_RIGHT -> vehicleController.moveRight();
            case STOP -> vehicleController.stopMovement();
            case OPEN_STORE -> openStore();
            case OPEN_SETTINGS -> openSettings();
            case OPEN_MENU, CANCEL -> openMenu();
            case PAUSE -> togglePause();
            case CHANGE_HEAD -> changeHead();
            case CONFIRM -> confirm();
        }

        refreshView();
    }

    public void openStore() {
        if (screenController != null) {
            screenController.showStore();
        }
    }

    public void openSettings() {
        if (screenController != null) {
            screenController.showSettings();
        }
    }

    public void openMenu() {
        pauseGame();
        if (screenController != null) {
            screenController.showMenu();
        }
    }

    public void confirm() {
        gameScreen.requestFocusInWindow();
    }

    public void changeHead() {
        try {
            Object snowplow = game.getSnowplow();
            snowplow.getClass().getMethod("changeHead").invoke(snowplow);
        } catch (Exception ignored) {
            // Ha a modellben más néven van a fejcserélő metódus, itt kell hozzáigazítani.
        }
    }

    private void refreshView() {
        try {
            gameScreen.roundChanged((int) game.getClass().getMethod("getRound").invoke(game));
        } catch (Exception ignored) {}

        try {
            gameScreen.moneyChanged();
        } catch (Exception ignored) {}

        try {
            Role role = game.getPlayer().getCurrentRole();
            gameScreen.roleChanged(role);
        } catch (Exception ignored) {}

        try {
            gameScreen.headChanged();
        } catch (Exception ignored) {}

        gameScreen.repaint();
    }

    public VehicleController getVehicleController() {
        return vehicleController;
    }

    public KeyboardController getKeyboardController() {
        return keyboardController;
    }

    public boolean isRunning() {
        return running;
    }

    public void setPlayerCount(int count) {
        // később ide jön a logika
    }

    public void setMaxRound(int maxRound) {
        game.setMaxRound(maxRound);
    }

    public void setCarCount(int count) {
        // később ide jön az autók létrehozása
    }
}