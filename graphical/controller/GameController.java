package controller;

import src.*;
import view.GameScreen;
import java.util.ArrayList;
import java.util.List;

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

    private static final int FIELD = 0;
    private static final int ROAD = 1;
    private static final int SNOW = 2;
    private static final int ICE = 3;
    private static final int DEPOT = 4;

    private final int[][] roadMap = new int[ROWS][COLS];
    private final List<TrafficCar> trafficCars = new ArrayList<>();

    private int playerRow = 5;
    private int playerCol = 1;
    private int targetRow = 5;
    private int targetCol = 13;

    private int configuredCarCount = 5;
    private int cleanedTiles = 0;
    private int totalDirtyTiles = 0;
    private int collisions = 0;
    private int plowLevel = 1;
    private int completedJobs = 0;

    private String message = "Cél: takarítsd le az utak 70%-át, majd menj a jobb oldali depóba.";

    public GameController(Game game, GameScreen gameScreen) {
        this.game = game;
        this.gameScreen = gameScreen;
        this.vehicleController = new VehicleController(game);
        this.keyboardController = new KeyboardController(this, game);
        this.gameLoop = new GameLoop(this, 600);

        restartGameState();

        gameScreen.addKeyListener(keyboardController);
        gameScreen.setFocusable(true);
    }

    public void setScreenController(ScreenController screenController) {
        this.screenController = screenController;
    }

    public void startGame() {
        running = true;
        keyboardController.setEnabled(true);
        message = "Indulás! WASD/nyilak: mozgás, C/CLEAN: takarítás, R/RESET: újrakezdés.";
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
        if (running) {
            pauseGame();
            message = "Szünet.";
        } else {
            resumeGame();
            message = "Folytatás.";
        }
    }

    public void tick() {
        if (!running) return;

        game.tick();
        moveTrafficCars();

        if (isTrafficAt(playerRow, playerCol)) {
            handleCollision();
        }

        if (game.isOver()) {
            message = "Lejárt a játékidő. Nem sikerült teljesíteni a küldetést.";
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
            case CHANGE_HEAD -> cleanAroundPlayer();
            case CONFIRM -> confirm();
            case RESTART -> restartGame();
        }

        refreshView();
    }

    private void restartGameState() {
        running = false;

        game.setCurrentRound(0);

        playerRow = 5;
        playerCol = 1;
        targetRow = 5;
        targetCol = 13;

        cleanedTiles = 0;
        totalDirtyTiles = 0;
        collisions = 0;

        buildPlayableMap();
        resetTrafficCars();

        message = "Új játék előkészítve. Start gombbal indul.";
        refreshView();
    }

    public void restartGame() {
        stopGame();
        restartGameState();
    }

    private void buildPlayableMap() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                roadMap[r][c] = FIELD;
            }
        }

        for (int c = 1; c <= 13; c++) roadMap[5][c] = ROAD;
        for (int r = 2; r <= 8; r++) roadMap[r][3] = ROAD;
        for (int r = 2; r <= 8; r++) roadMap[r][11] = ROAD;
        for (int c = 3; c <= 11; c++) roadMap[2][c] = ROAD;
        for (int c = 3; c <= 11; c++) roadMap[8][c] = ROAD;

        for (int c = 6; c <= 9; c++) roadMap[4][c] = ROAD;
        for (int c = 6; c <= 9; c++) roadMap[6][c] = ROAD;
        roadMap[3][6] = ROAD;
        roadMap[3][9] = ROAD;
        roadMap[7][6] = ROAD;
        roadMap[7][9] = ROAD;

        roadMap[playerRow][playerCol] = DEPOT;
        roadMap[targetRow][targetCol] = DEPOT;

        addDirtyTile(5, 5, SNOW);
        addDirtyTile(5, 6, SNOW);
        addDirtyTile(4, 6, SNOW);
        addDirtyTile(2, 7, SNOW);
        addDirtyTile(8, 9, SNOW);
        addDirtyTile(3, 11, SNOW);
        addDirtyTile(6, 6, SNOW);
        addDirtyTile(7, 9, SNOW);

        addDirtyTile(6, 9, ICE);
        addDirtyTile(8, 5, ICE);
        addDirtyTile(5, 10, ICE);
        addDirtyTile(2, 4, ICE);
        addDirtyTile(4, 8, ICE);
    }

    private void addDirtyTile(int r, int c, int type) {
        roadMap[r][c] = type;
        totalDirtyTiles++;
    }

    private void resetTrafficCars() {
        trafficCars.clear();

        List<int[]> routeA = route(new int[][]{
                {5, 2}, {5, 12}, {2, 11}, {2, 3}, {5, 3}
        });

        List<int[]> routeB = route(new int[][]{
                {8, 3}, {8, 11}, {5, 11}, {2, 11}, {2, 3}, {5, 3}
        });

        List<int[]> routeC = route(new int[][]{
                {4, 6}, {4, 9}, {6, 9}, {6, 6}, {4, 6}
        });

        List<int[]> routeD = route(new int[][]{
                {5, 12}, {8, 11}, {8, 3}, {5, 3}, {5, 12}
        });

        List<int[]> routeE = route(new int[][]{
                {2, 4}, {2, 11}, {5, 11}, {8, 11}, {8, 4}, {5, 3}
        });

        if (configuredCarCount >= 1) trafficCars.add(new TrafficCar(routeA, 0, "A", 0));
        if (configuredCarCount >= 2) trafficCars.add(new TrafficCar(routeB, 4, "B", 1));
        if (configuredCarCount >= 3) trafficCars.add(new TrafficCar(routeC, 1, "C", 2));
        if (configuredCarCount >= 4) trafficCars.add(new TrafficCar(routeD, 2, "D", 3));
        if (configuredCarCount >= 5) trafficCars.add(new TrafficCar(routeE, 7, "E", 4));
        if (configuredCarCount >= 6) trafficCars.add(new TrafficCar(routeA, 9, "F", 5));
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
                if (r != er) {
                    r += dr;
                } else if (c != ec) {
                    c += dc;
                }

                result.add(new int[]{r, c});
            }
        }

        return result;
    }

    private void moveTrafficCars() {
        for (TrafficCar car : trafficCars) {
            int next = (car.index + 1) % car.route.size();
            int[] p = car.route.get(next);

            if (roadMap[p[0]][p[1]] == SNOW || roadMap[p[0]][p[1]] == ICE) {
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

    private void movePlayer(int dr, int dc) {
        if (!running) {
            message = "A játék nem fut. Nyomd meg a START gombot.";
            return;
        }

        int nr = playerRow + dr;
        int nc = playerCol + dc;

        if (nr < 0 || nr >= ROWS || nc < 0 || nc >= COLS) {
            message = "Nem lehet lemenni a pályáról.";
            return;
        }

        if (roadMap[nr][nc] == FIELD) {
            message = "Csak az úton haladhatsz.";
            return;
        }

        if (roadMap[nr][nc] == SNOW || roadMap[nr][nc] == ICE) {
            message = "Akadályos útszakasz. Takarítsd le C/CLEAN gombbal.";
            return;
        }

        if (isTrafficAt(nr, nc)) {
            handleCollision();
            return;
        }

        playerRow = nr;
        playerCol = nc;

        if (playerRow == targetRow && playerCol == targetCol) {
            checkMissionEnd();
        } else {
            message = "Mozgás sikeres.";
        }
    }

    public void cleanCurrentTile() {
        cleanAroundPlayer();
    }

    public void cleanAroundPlayer() {
        if (!running) {
            message = "Takarításhoz előbb indítsd el a játékot.";
            return;
        }

        int cleaned = 0;

        cleaned += cleanTile(playerRow, playerCol);
        cleaned += cleanTile(playerRow - 1, playerCol);
        cleaned += cleanTile(playerRow + 1, playerCol);
        cleaned += cleanTile(playerRow, playerCol - 1);
        cleaned += cleanTile(playerRow, playerCol + 1);

        if (plowLevel >= 2) {
            cleaned += cleanTile(playerRow - 1, playerCol - 1);
            cleaned += cleanTile(playerRow - 1, playerCol + 1);
            cleaned += cleanTile(playerRow + 1, playerCol - 1);
            cleaned += cleanTile(playerRow + 1, playerCol + 1);
        }

        if (plowLevel >= 3) {
            cleaned += cleanTile(playerRow - 2, playerCol);
            cleaned += cleanTile(playerRow + 2, playerCol);
            cleaned += cleanTile(playerRow, playerCol - 2);
            cleaned += cleanTile(playerRow, playerCol + 2);
        }

        if (cleaned > 0) {
            int reward = cleaned * 25 * plowLevel;
            rewardCleaner(reward);
            message = "Takarítás kész: " + cleaned + " mező, +" + reward + " pénz.";
        } else {
            message = "Nincs a közeledben takarítható havas/jeges mező.";
        }

        checkMissionEnd();
        refreshView();
    }

    private int cleanTile(int r, int c) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) return 0;

        if (roadMap[r][c] == SNOW || roadMap[r][c] == ICE) {
            roadMap[r][c] = ROAD;
            cleanedTiles++;
            return 1;
        }

        return 0;
    }

    private void handleCollision() {
        collisions++;

        int penalty = 40;
        chargeCleaner(penalty);

        playerRow = 5;
        playerCol = 1;

        message = "Ütközés történt. -" + penalty + " pénz, visszakerültél a depóba.";
        refreshView();
    }

    private void checkMissionEnd() {
        if (getCleanPercent() >= 70 && playerRow == targetRow && playerCol == targetCol) {
            int reward = 200 + plowLevel * 50;
            rewardCleaner(reward);
            completedJobs++;

            message = "Küldetés teljesítve! +" + reward + " pénz. RESET vagy MENU.";
            stopGame();
        } else if (playerRow == targetRow && playerCol == targetCol) {
            message = "A cél depónál vagy, de még nincs meg a 70% takarítás.";
        }
    }

    private void rewardCleaner(int amount) {
        Role role = gameScreen.getRole();

        if (role instanceof CleanerRole cleanerRole) {
            cleanerRole.changeMoney(amount);
        }
    }

    private void chargeCleaner(int amount) {
        Role role = gameScreen.getRole();

        if (role instanceof CleanerRole cleanerRole) {
            cleanerRole.changeMoney(-amount);
        }
    }

    public void upgradePlow() {
        int price = getUpgradePrice();

        if (plowLevel >= 3) {
            message = "A hókotró már maximális szintű.";
            refreshView();
            return;
        }

        Role role = gameScreen.getRole();

        if (role instanceof CleanerRole cleanerRole) {
            if (cleanerRole.getMoney() < price) {
                message = "Nincs elég pénz a fejlesztéshez. Ár: " + price;
                refreshView();
                return;
            }

            cleanerRole.changeMoney(-price);
            plowLevel++;

            message = "Fejlesztés sikeres. Új hókotró szint: " + plowLevel;
        }

        refreshView();
    }

    public int getUpgradePrice() {
        return 150 * plowLevel;
    }

    public void openStore() {
        pauseGame();

        if (screenController != null) {
            screenController.showStore();
        }
    }

    public void openSettings() {
        pauseGame();

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
        cleanAroundPlayer();
    }

    private void refreshView() {
        gameScreen.roundChanged(game.getRound());
        gameScreen.moneyChanged();

        if (game.getPlayer() != null) {
            gameScreen.roleChanged(game.getPlayer().getCurrentRole());
        }

        gameScreen.headChanged();
        gameScreen.updateHud(
                getCleanPercent(),
                plowLevel,
                collisions,
                completedJobs,
                getUpgradePrice()
        );
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

    public int getCleanPercent() {
        if (totalDirtyTiles == 0) return 100;
        return (int) Math.round((cleanedTiles * 100.0) / totalDirtyTiles);
    }

    public int getPlowLevel() {
        return plowLevel;
    }

    public int getCollisions() {
        return collisions;
    }

    public int getCompletedJobs() {
        return completedJobs;
    }

    public void setPlayerCount(int count) {
        message = "Játékosszám beállítva: " + Math.max(1, count);
        refreshView();
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
        private final int colorIndex;

        TrafficCar(List<int[]> route, int index, String label, int colorIndex) {
            this.route = route;
            this.index = Math.min(index, route.size() - 1);
            this.label = label;
            this.colorIndex = colorIndex;
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

        public int getColorIndex() {
            return colorIndex;
        }
    }
}