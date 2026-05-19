package controller;

import java.util.ArrayList;
import java.util.List;
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

    private static final int ROWS = 11;
    private static final int COLS = 15;
    private final int[][] roadMap = new int[ROWS][COLS];
    private final List<TrafficCar> trafficCars = new ArrayList<>();
    private int playerRow = 5;
    private int playerCol = 1;
    private int targetRow = 5;
    private int targetCol = 13;
    private int configuredCarCount = 4;
    private String message = "Cél: juss el a jobb oldali depóig. Nyilak/WASD: mozgás, C: takarítás, B: bolt.";

    public GameController(Game game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.vehicleController = new VehicleController(game);
        this.keyboardController = new KeyboardController(this, game);
        this.gameLoop = new GameLoop(this, 650);

        buildPlayableMap();
        resetTrafficCars();

        gameScreen.addKeyListener(keyboardController);
        gameScreen.setFocusable(true);
    }

    private void buildPlayableMap() {
        for (int c = 1; c <= 13; c++) roadMap[5][c] = 1;
        for (int r = 2; r <= 8; r++) roadMap[r][3] = 1;
        for (int r = 2; r <= 8; r++) roadMap[r][11] = 1;
        for (int c = 3; c <= 11; c++) roadMap[2][c] = 1;
        for (int c = 3; c <= 11; c++) roadMap[8][c] = 1;
        for (int c = 6; c <= 9; c++) roadMap[4][c] = 1;
        for (int c = 6; c <= 9; c++) roadMap[6][c] = 1;

        roadMap[playerRow][playerCol] = 4;
        roadMap[targetRow][targetCol] = 4;

        roadMap[5][5] = 2;
        roadMap[5][6] = 2;
        roadMap[4][6] = 2;
        roadMap[2][7] = 2;
        roadMap[8][9] = 2;
        roadMap[3][11] = 2;
        roadMap[6][9] = 3;
        roadMap[8][5] = 3;
        roadMap[5][10] = 3;
    }

    private void resetTrafficCars() {
        trafficCars.clear();

        List<int[]> routeA = route(new int[][]{{5, 2}, {5, 12}, {2, 11}, {2, 3}, {5, 3}});
        List<int[]> routeB = route(new int[][]{{8, 3}, {8, 11}, {5, 11}, {2, 11}, {2, 3}, {5, 3}});
        List<int[]> routeC = route(new int[][]{{4, 6}, {4, 9}, {6, 9}, {6, 6}, {4, 6}});
        List<int[]> routeD = route(new int[][]{{5, 12}, {8, 11}, {8, 3}, {5, 3}, {5, 12}});

        if (configuredCarCount >= 1) trafficCars.add(new TrafficCar(routeA, 0, "A"));
        if (configuredCarCount >= 2) trafficCars.add(new TrafficCar(routeB, 4, "B"));
        if (configuredCarCount >= 3) trafficCars.add(new TrafficCar(routeC, 1, "C"));
        if (configuredCarCount >= 4) trafficCars.add(new TrafficCar(routeD, 2, "D"));
        if (configuredCarCount >= 5) trafficCars.add(new TrafficCar(routeA, 7, "E"));
        if (configuredCarCount >= 6) trafficCars.add(new TrafficCar(routeB, 10, "F"));
    }

    private List<int[]> route(int[][] points) {
        List<int[]> result = new ArrayList<>();

        for (int i = 0; i < points.length - 1; i++) {
            int r = points[i][0];
            int c = points[i][1];
            int er = points[i + 1][0];
            int ec = points[i + 1][1];

            int dr = Integer.compare(er, r);
            int dc = Integer.compare(ec, c);

            if (result.isEmpty()) {
                result.add(new int[]{r, c});
            }

            while (r != er || c != ec) {
                if (r != er) r += dr;
                else if (c != ec) c += dc;

                result.add(new int[]{r, c});
            }
        }

        return result;
    }

    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    public void startGame() {
        running = true;
        keyboardController.setEnabled(true);
        message = "Indulás! Takarítsd a havas/jeges útszakaszokat, és kerüld az autókat.";
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
        if (running) pauseGame();
        else resumeGame();
    }

    public void tick() {
        if (!running) return;

        game.tick();
        moveTrafficCars();

        if (isTrafficAt(playerRow, playerCol)) {
            message = "Ütközés történt egy autóval. A hókotró visszakerült a depóba.";
            playerRow = 5;
            playerCol = 1;
        }

        if (game.isOver()) {
            message = "Lejárt a játékidő. MENU -> Starttal újra próbálhatod.";
            stopGame();
            return;
        }

        refreshView();
    }

    private void moveTrafficCars() {
        for (TrafficCar car : trafficCars) {
            int next = (car.index + 1) % car.route.size();
            int[] p = car.route.get(next);

            if (roadMap[p[0]][p[1]] == 2 || roadMap[p[0]][p[1]] == 3) {
                continue;
            }

            if (occupiedByOtherCar(car, p[0], p[1])) {
                continue;
            }

            car.index = next;
        }
    }

    private boolean occupiedByOtherCar(TrafficCar self, int r, int c) {
        for (TrafficCar car : trafficCars) {
            if (car != self && car.getRow() == r && car.getCol() == c) {
                return true;
            }
        }

        return false;
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

        if (roadMap[nr][nc] == 2 || roadMap[nr][nc] == 3) {
            message = "Akadályos útszakasz: előbb takarítsd le C/CLEAN gombbal.";
            return;
        }

        if (isTrafficAt(nr, nc)) {
            message = "Ott éppen autó halad. Várj vagy válassz másik utat.";
            return;
        }

        playerRow = nr;
        playerCol = nc;
        message = "Mozgás sikeres.";

        if (playerRow == targetRow && playerCol == targetCol) {
            rewardCleaner(150);
            message = "Siker! Elérted a depót. +150 pénz.";
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
            message = "Itt nincs közvetlenül takarítható havas/jeges mező.";
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

        if (game.getPlayer() != null) {
            gameScreen.roleChanged(game.getPlayer().getCurrentRole());
        }

        gameScreen.headChanged();
        gameScreen.repaint();
    }

    private boolean isTrafficAt(int r, int c) {
        for (TrafficCar car : trafficCars) {
            if (car.getRow() == r && car.getCol() == c) {
                return true;
            }
        }

        return false;
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

    public int[][] getRoadMap() {
        return roadMap;
    }

    public int getPlayerRow() {
        return playerRow;
    }

    public int getPlayerCol() {
        return playerCol;
    }

    public int getTargetRow() {
        return targetRow;
    }

    public int getTargetCol() {
        return targetCol;
    }

    public String getMessage() {
        return message;
    }

    public int getMaxRound() {
        return game.getMaxRound();
    }

    public List<TrafficCar> getTrafficCars() {
        return trafficCars;
    }

    public void setPlayerCount(int count) {
        message = "Játékosszám beállítva: " + Math.max(1, count);
    }

    public void setMaxRound(int maxRound) {
        game.setMaxRound(Math.max(1, maxRound));
        message = "Játék hossza beállítva: " + game.getMaxRound() + " kör.";
        refreshView();
    }

    public void setCarCount(int count) {
        configuredCarCount = Math.max(0, Math.min(6, count));
        resetTrafficCars();
        message = "Autók száma beállítva: " + configuredCarCount + ".";
        refreshView();
    }

    public static class TrafficCar {
        private final List<int[]> route;
        private int index;
        private final String label;

        TrafficCar(List<int[]> route, int index, String label) {
            this.route = route;
            this.index = Math.min(index, route.size() - 1);
            this.label = label;
        }

        public int getRow() {
            return route.get(index)[0];
        }

        public int getCol() {
            return route.get(index)[1];
        }

        public String getLabel() {
            return label;
        }
    }
}