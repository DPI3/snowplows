package controller;

import src.*;
import view.GameScreen;

public class GameController {
    private final Game game;
    private final GameScreen gameScreen;
    private final GameLoop gameLoop;
    private final KeyboardController keyboardController;
    private final VehicleController vehicleController;

    private ScreenController screenController;
    private boolean running;

    private static final int ROWS = 7;
    private static final int COLS = 10;
    private final int[][] roadMap = new int[ROWS][COLS];
    private int playerRow = 3;
    private int playerCol = 0;
    private int targetRow = 3;
    private int targetCol = 9;
    private String message = "Juss el a jobb oldali terminálig. Nyilak/WASD: mozgás, C: takarítás, B: bolt.";

    public GameController(Game game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.vehicleController = new VehicleController(game);
        this.keyboardController = new KeyboardController(this, game);
        this.gameLoop = new GameLoop(this, 1000);
        this.running = false;

        buildPlayableMap();

        gameScreen.addKeyListener(keyboardController);
        gameScreen.setFocusable(true);
    }

    private void buildPlayableMap() {
        // 0 = mező, 1 = tiszta út, 2 = havas út, 3 = jég, 4 = terminál/cél
        for (int c = 0; c < COLS; c++) roadMap[3][c] = 1;
        for (int r = 1; r <= 5; r++) roadMap[r][2] = 1;
        for (int c = 2; c <= 7; c++) roadMap[1][c] = 1;
        for (int r = 1; r <= 5; r++) roadMap[r][7] = 1;
        roadMap[3][0] = 4;
        roadMap[targetRow][targetCol] = 4;
        roadMap[3][4] = 2;
        roadMap[3][5] = 2;
        roadMap[1][5] = 2;
        roadMap[5][7] = 3;
        roadMap[2][2] = 2;
    }

    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    public void startGame() {
        running = true;
        keyboardController.setEnabled(true);
        gameLoop.start();
        refreshView();
        gameScreen.requestFocusInWindow();
    }

    public void pauseGame() {
        running = false;
        gameLoop.stop();
        refreshView();
    }

    public void resumeGame() {
        running = true;
        keyboardController.setEnabled(true);
        gameLoop.start();
        refreshView();
        gameScreen.requestFocusInWindow();
    }

    public void stopGame() {
        running = false;
        keyboardController.setEnabled(false);
        gameLoop.stop();
        refreshView();
    }

    public void togglePause() {
        if (running) pauseGame(); else resumeGame();
    }

    public void tick() {
        if (!running) return;
        game.tick();
        if (game.isOver()) {
            message = "Lejárt a játékidő. MENU -> Starttal újra próbálhatod.";
            stopGame();
            return;
        }
        refreshView();
    }

    public void handleInputAction(InputAction action) {
        if (action == null) return;

        switch (action) {
            case MOVE_UP -> movePlayer(-1, 0);
            case MOVE_DOWN -> movePlayer(1, 0);
            case MOVE_LEFT -> movePlayer(0, -1);
            case MOVE_RIGHT -> movePlayer(0, 1);
            case STOP -> vehicleController.stopMovement();
            case OPEN_STORE -> openStore();
            case OPEN_SETTINGS -> openSettings();
            case OPEN_MENU, CANCEL -> openMenu();
            case PAUSE -> togglePause();
            case CHANGE_HEAD -> cleanCurrentTile();
            case CONFIRM -> confirm();
        }

        refreshView();
    }

    private void movePlayer(int dr, int dc) {
        if (!running) {
            message = "A játék szünetel. Start vagy P gombbal indítható.";
            return;
        }

        int nr = playerRow + dr;
        int nc = playerCol + dc;
        if (nr < 0 || nr >= ROWS || nc < 0 || nc >= COLS) {
            message = "Nem lehet lemenni a pályáról.";
            return;
        }
        if (roadMap[nr][nc] == 0) {
            message = "Csak az úton haladhatsz.";
            return;
        }
        if (roadMap[nr][nc] == 2) {
            message = "Havas út: előbb takarítsd le C gombbal vagy a CLEAN gombbal.";
            return;
        }

        playerRow = nr;
        playerCol = nc;
        message = "Mozgás sikeres.";

        if (playerRow == targetRow && playerCol == targetCol) {
            rewardCleaner(100);
            message = "Siker! Elérted a terminált. +100 pénz.";
            stopGame();
        }
    }

    public void cleanCurrentTile() {
        int cleaned = 0;
        cleaned += cleanTile(playerRow, playerCol);
        cleaned += cleanTile(playerRow - 1, playerCol);
        cleaned += cleanTile(playerRow + 1, playerCol);
        cleaned += cleanTile(playerRow, playerCol - 1);
        cleaned += cleanTile(playerRow, playerCol + 1);

        if (cleaned > 0) {
            int reward = cleaned * 25;
            rewardCleaner(reward);
            message = "Takarítás kész. +" + reward + " pénz.";
        } else {
            message = "Itt nincs közvetlenül takarítható havas mező.";
        }
        refreshView();
    }

    private int cleanTile(int r, int c) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) return 0;
        if (roadMap[r][c] == 2 || roadMap[r][c] == 3) {
            roadMap[r][c] = 1;
            return 1;
        }
        return 0;
    }

    private void rewardCleaner(int amount) {
        Role role = gameScreen.getRole();
        if (role instanceof CleanerRole cleanerRole) {
            cleanerRole.changeMoney(amount);
        }
    }

    public void openStore() {
        pauseGame();
        if (screenController != null) screenController.showStore();
    }

    public void openSettings() {
        pauseGame();
        if (screenController != null) screenController.showSettings();
    }

    public void openMenu() {
        pauseGame();
        if (screenController != null) screenController.showMenu();
    }

    public void confirm() {
        gameScreen.requestFocusInWindow();
    }

    public void changeHead() {
        cleanCurrentTile();
    }

    private void refreshView() {
        gameScreen.roundChanged(game.getRound());
        gameScreen.moneyChanged();
        if (game.getPlayer() != null) gameScreen.roleChanged(game.getPlayer().getCurrentRole());
        gameScreen.headChanged();
        gameScreen.repaint();
    }

    public VehicleController getVehicleController() { return vehicleController; }
    public KeyboardController getKeyboardController() { return keyboardController; }
    public boolean isRunning() { return running; }

    public int[][] getRoadMap() { return roadMap; }
    public int getPlayerRow() { return playerRow; }
    public int getPlayerCol() { return playerCol; }
    public int getTargetRow() { return targetRow; }
    public int getTargetCol() { return targetCol; }
    public String getMessage() { return message; }
    public int getMaxRound() { return game.getMaxRound(); }

    public void setPlayerCount(int count) {
        message = "Játékosszám beállítva: " + Math.max(1, count);
    }

    public void setMaxRound(int maxRound) {
        game.setMaxRound(Math.max(1, maxRound));
        message = "Játék hossza beállítva: " + game.getMaxRound() + " kör.";
        refreshView();
    }

    public void setCarCount(int count) {
        message = "Autók száma beállítva: " + Math.max(0, count) + ".";
    }
}
