package controller;

import controller.GameController.TrafficCar;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    private static final int ROWS = 40;
    private static final int COLS = 60;

    private static final int FIELD = 0;
    private static final int ROAD = 1;
    private static final int SNOW = 2;
    private static final int ICE = 3;
    private static final int DEPOT = 4;
    private static final int TUNNEL = 5;
    private static final int BRIDGE = 6;
    private static final int GRAVEL = 7;
    private static final int BROKEN_ICE = 8;

    private static final int CRASHED_LANE = 9;
    private static final int DEEP_SNOW = 10;

    private static final int SNOW_TO_ICE_PASSES = 3;
    private static final int CAR_STUCK_TICKS = 3;
    private static final int SLIDE_LIMIT = 4;

    private final int[][] roadMap = new int[ROWS][COLS];
    private final int[][] snowPressure = new int[ROWS][COLS];
    private final List<TrafficCar> trafficCars = new ArrayList<>();

    private boolean busMode = false;
    private final Random random = new Random();

    private int roundDurationSeconds = 300;
    private int remainingSeconds = 300;
    private long lastSecondUpdate = System.currentTimeMillis();

    private int weatherCounter = 0;

    private static final int START_ROW = 5;
    private static final int START_COL = 1;

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

        long now = System.currentTimeMillis();

        if (now - lastSecondUpdate >= 1000) {
            lastSecondUpdate = now;
            remainingSeconds--;

            if (remainingSeconds <= 0) {
                game.setCurrentRound(game.getRound() + 1);
                remainingSeconds = roundDurationSeconds;
            }
        }

        weatherCounter++;

        if (weatherCounter >= 8) {
            weatherCounter = 0;
            randomWeatherChange();
        }

        moveTrafficCars();
        refreshView();
    }

    public void handleInputAction(InputAction action) {
        if (action == null) return;

        switch (action) {
            case MOVE_UP: movePlayer(-1, 0); break;
            case MOVE_DOWN: movePlayer(1, 0); break;
            case MOVE_LEFT: movePlayer(0, -1); break;
            case MOVE_RIGHT: movePlayer(0, 1); break;
            case STOP: vehicleController.stopMovement(); break;
            case OPEN_STORE: openStore(); break;
            case OPEN_SETTINGS: openSettings(); break;
            case OPEN_MENU: case CANCEL: openMenu(); break;
            case PAUSE: togglePause(); break;
            case CHANGE_HEAD: cleanAroundPlayer(); break;
            case CONFIRM: confirm(); break;
            case RESTART: restartGame(); break;
        }

        refreshView();
    }

    private void randomWeatherChange() {
        for (int i = 0; i < 3; i++) {
            int r = (int) (Math.random() * ROWS);
            int c = (int) (Math.random() * COLS);

            if (roadMap[r][c] == ROAD) {
                roadMap[r][c] = Math.random() < 0.65 ? SNOW : ICE;
                totalDirtyTiles++;
            }
        }
        message = "Időjárás: új hó vagy jég jelent meg a pályán.";
    }

    private void restartGameState() {
        running = false;

        game.setCurrentRound(0);
        busMode = false;
        remainingSeconds = roundDurationSeconds;
        lastSecondUpdate = System.currentTimeMillis();

        playerRow = START_ROW;
        playerCol = START_COL;

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
        totalDirtyTiles = 0;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                roadMap[r][c] = FIELD;
            }
        }

        // vízszintes 2x2-es utak
        markHorizontalRoad(6, 2, 55);
        markHorizontalRoad(15, 4, 52);
        markHorizontalRoad(25, 2, 57);
        markHorizontalRoad(34, 5, 55);

        // függőleges 2x2-es utak
        markVerticalRoad(8, 4, 35);
        markVerticalRoad(22, 4, 35);
        markVerticalRoad(38, 4, 35);
        markVerticalRoad(52, 6, 35);

        // összekötő rövid szakaszok
        markHorizontalRoad(10, 8, 22);
        markHorizontalRoad(20, 22, 38);
        markHorizontalRoad(30, 38, 52);

        playerRow = 6;
        playerCol = 2;

        targetRow = 34;
        targetCol = 55;

        roadMap[playerRow][playerCol] = DEPOT;
        roadMap[targetRow][targetCol] = DEPOT;

        placeRandomTunnelsAndBridges();
        addInitialSnowAndIce();
    }

    private void markRoad(int r, int c) {
        if (r >= 0 && r < ROWS && c >= 0 && c < COLS) {
            roadMap[r][c] = ROAD;
        }
    }

    private void markHorizontalRoad(int row, int fromCol, int toCol) {
        for (int r = row - 1; r <= row + 2; r++) {
            for (int c = fromCol; c <= toCol; c++) {
                markRoad(r, c);
            }
        }
    }

    private void markVerticalRoad(int col, int fromRow, int toRow) {
        for (int c = col - 1; c <= col + 2; c++) {
            for (int r = fromRow; r <= toRow; r++) {
                markRoad(r, c);
            }
        }
    }

    private void addDirtyTile(int r, int c, int type) {
        if (roadMap[r][c] == ROAD) {
            roadMap[r][c] = type;
            totalDirtyTiles++;
        }
    }

    private int[] getRandomRoadCell() {
        for (int i = 0; i < 500; i++) {
            int r = random.nextInt(ROWS);
            int c = random.nextInt(COLS);

            if (roadMap[r][c] == ROAD) {
                return new int[]{r, c};
            }
        }

        return null;
    }

    private void placeRandomTunnelsAndBridges() {
        for (int i = 0; i < 6; i++) {
            placeRoadSegment(TUNNEL, 3 + random.nextInt(4));
        }

        for (int i = 0; i < 6; i++) {
            placeRoadSegment(BRIDGE, 3 + random.nextInt(4));
        }
    }

    private void addInitialSnowAndIce() {
        for (int i = 0; i < 35; i++) {
            int[] p = getRandomRoadCell();

            if (p != null) {
                addDirtyTile(p[0], p[1], random.nextBoolean() ? SNOW : ICE);
            }
        }
    }

    private void placeRoadSegment(int type, int length) {
        int[] start = getRandomRoadCell();
        if (start == null) return;

        int r = start[0];
        int c = start[1];

        boolean horizontal = random.nextBoolean();

        for (int i = 0; i < length; i++) {
            int rr = horizontal ? r : r + i;
            int cc = horizontal ? c + i : c;

            if (rr < 0 || rr >= ROWS || cc < 0 || cc >= COLS) {
                return;
            }

            if (roadMap[rr][cc] != ROAD) {
                return;
            }
        }

        for (int i = 0; i < length; i++) {
            int rr = horizontal ? r : r + i;
            int cc = horizontal ? c + i : c;
            roadMap[rr][cc] = type;
        }
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
            if (car.stuckTicks > 0) {
                car.stuckTicks--;
                continue;
            }

            int next = (car.index + 1) % car.route.size();
            int[] current = car.route.get(car.index);
            int[] planned = car.route.get(next);

            int dr = Integer.compare(planned[0], current[0]);
            int dc = Integer.compare(planned[1], current[1]);

            int[] target = chooseCarTarget(car, planned[0], planned[1], dr, dc);
            if (target == null) {
                car.stuckTicks = 1;
                continue;
            }

            int rr = target[0];
            int cc = target[1];

            if (roadMap[rr][cc] == ICE) {
                int[] slideEnd = slideOnIce(car, rr, cc, dr, dc);
                if (slideEnd == null) continue;
                rr = slideEnd[0];
                cc = slideEnd[1];
            }

            TrafficCar other = getTrafficCarAt(car, rr, cc);
            if (other != null) {
                crashCars(car, other, rr, cc);
                continue;
            }

            int matchingIndex = findRouteIndex(car, rr, cc);
            if (matchingIndex >= 0) {
                car.index = matchingIndex;
            } else {
                car.route.add(car.index + 1, new int[]{rr, cc});
                car.index++;
            }

            handleCarTileEffect(rr, cc);
        }
    }

    private int[] chooseCarTarget(TrafficCar car, int targetRow, int targetCol, int dr, int dc) {
        if (canCarEnter(car, targetRow, targetCol)) {
            return new int[]{targetRow, targetCol};
        }

        return findAlternativeStep(car);
    }

    private int[] findAlternativeStep(TrafficCar car) {
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        int currentRow = car.getRow();
        int currentCol = car.getCol();
        int[] destination = car.route.get(car.route.size() - 1);

        int[] best = null;
        int bestDistance = Integer.MAX_VALUE;

        for (int[] dir : dirs) {
            int nr = currentRow + dir[0];
            int nc = currentCol + dir[1];

            if (!canCarEnter(car, nr, nc)) continue;

            int distance = Math.abs(destination[0] - nr) + Math.abs(destination[1] - nc);
            if (distance < bestDistance) {
                bestDistance = distance;
                best = new int[]{nr, nc};
            }
        }

        return best;
    }

    private boolean canCarEnter(TrafficCar self, int r, int c) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) return false;

        int tile = roadMap[r][c];

        if (tile == FIELD || tile == DEEP_SNOW || tile == CRASHED_LANE || tile == BROKEN_ICE) {
            return false;
        }

        return getTrafficCarAt(self, r, c) == null || tile == ICE;
    }

    private int[] slideOnIce(TrafficCar car, int startRow, int startCol, int dr, int dc) {
        int r = startRow;
        int c = startCol;

        for (int i = 0; i < SLIDE_LIMIT; i++) {
            TrafficCar other = getTrafficCarAt(car, r, c);
            if (other != null) {
                crashCars(car, other, r, c);
                return null;
            }

            int nr = r + dr;
            int nc = c + dc;

            if (nr < 0 || nr >= ROWS || nc < 0 || nc >= COLS) {
                return new int[]{r, c};
            }

            int nextTile = roadMap[nr][nc];

            if (nextTile == FIELD || nextTile == DEEP_SNOW || nextTile == CRASHED_LANE || nextTile == BROKEN_ICE) {
                return new int[]{r, c};
            }

            r = nr;
            c = nc;

            if (nextTile != ICE) {
                return new int[]{r, c};
            }
        }

        return new int[]{r, c};
    }

    private void handleCarTileEffect(int r, int c) {
        if (roadMap[r][c] == SNOW) {
            snowPressure[r][c]++;

            if (snowPressure[r][c] >= SNOW_TO_ICE_PASSES) {
                roadMap[r][c] = ICE;
                snowPressure[r][c] = 0;
                message = "Az autók letaposták a havat, ezért jégpáncél alakult ki.";
            }
        }
    }

    private TrafficCar getTrafficCarAt(TrafficCar self, int r, int c) {
        for (TrafficCar car : trafficCars) {
            if (car != self && car.getRow() == r && car.getCol() == c) {
                return car;
            }
        }

        return null;
    }

    private void crashCars(TrafficCar first, TrafficCar second, int r, int c) {
        collisions++;
        first.stuckTicks = CAR_STUCK_TICKS;
        second.stuckTicks = CAR_STUCK_TICKS;

        roadMap[r][c] = CRASHED_LANE;
        snowPressure[r][c] = 0;

        message = "Autóbaleset történt, a sáv járhatatlanná vált.";
    }

    private int findRouteIndex(TrafficCar car, int r, int c) {
        for (int i = 0; i < car.route.size(); i++) {
            int[] p = car.route.get(i);
            if (p[0] == r && p[1] == c) return i;
        }

        return -1;
    }

    private boolean occupiedByOtherCar(TrafficCar self, int r, int c) {
        for (TrafficCar car : trafficCars) {
            if (car != self && car.getRow() == r && car.getCol() == c) {
                return true;
            }
        }

        return false;
    }

    private boolean isDriveable(int r, int c) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) return false;

        int tile = roadMap[r][c];

        return tile == ROAD
                || tile == DEPOT
                || tile == TUNNEL
                || tile == BRIDGE
                || tile == SNOW
                || tile == ICE;
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

        if (!isDriveable(nr, nc)) {
            message = "Csak az úton, hídon vagy alagútban haladhatsz.";
            return;
        }

        if (roadMap[nr][nc] == SNOW || roadMap[nr][nc] == ICE) {
            message = "Akadályos útszakasz. Takarítsd le C/CLEAN gombbal.";
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

        int tile = roadMap[r][c];

        if (tile == FIELD || tile == ROAD || tile == DEPOT || tile == TUNNEL || tile == BRIDGE) {
            return 0;
        }

        Role role = gameScreen.getRole();
        if (!(role instanceof CleanerRole)) return 0;

        CleanerRole cleaner = (CleanerRole) role;
        Snowplow snowplow = cleaner.getSnowplow();

        if (snowplow == null || snowplow.getCurrentHead() == null) return 0;

        Head head = snowplow.getCurrentHead();

        if (head instanceof DragonHead) {
            if (snowplow.getBiokeroseneStock() < 10) return 0;

            if (tile == SNOW || tile == DEEP_SNOW || tile == ICE || tile == BROKEN_ICE) {
                snowplow.consumeBiokerosene(10);
                roadMap[r][c] = ROAD;
                cleanedTiles++;
                return 1;
            }

            return 0;
        }

        if (head instanceof IcebreakerHead) {
            if (tile == ICE) {
                roadMap[r][c] = BROKEN_ICE;
                return 1;
            }

            return 0;
        }

        if (head instanceof SaltSpreaderHead) {
            if (snowplow.getSaltStock() < 10) return 0;

            if (tile == SNOW || tile == DEEP_SNOW || tile == ICE || tile == BROKEN_ICE) {
                snowplow.consumeSalt(10);
                roadMap[r][c] = ROAD;
                cleanedTiles++;
                return 1;
            }

            return 0;
        }

        if (head instanceof GravelSpreaderHead) {
            if (snowplow.getGravelStock() < 10) return 0;

            if (tile == ICE) {
                snowplow.consumeGravel(10);
                roadMap[r][c] = GRAVEL;
                return 1;
            }

            return 0;
        }

        if (head instanceof SweeperHead || head instanceof ThrowerHead) {
            if (tile == SNOW || tile == BROKEN_ICE || tile == GRAVEL) {
                roadMap[r][c] = ROAD;
                cleanedTiles++;
                return 1;
            }

            return 0;
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
        if (busMode) {
            if (playerRow == targetRow && playerCol == targetCol) {
                completedJobs++;

                int[] newPlayer = getRandomRoadCell();
                int[] newTarget = getRandomRoadCell();

                if (newPlayer != null && newTarget != null) {
                    playerRow = newPlayer[0];
                    playerCol = newPlayer[1];

                    if (roadMap[targetRow][targetCol] == DEPOT) {
                        roadMap[targetRow][targetCol] = ROAD;
                    }

                    targetRow = newTarget[0];
                    targetCol = newTarget[1];
                    roadMap[targetRow][targetCol] = DEPOT;
                }

                message = "Busz cél teljesítve. Új indulási hely és új célállomás kijelölve.";
            }

            return;
        }

        if (getCleanPercent() >= 70) {
            completedJobs++;
            message = "Snowplow mód teljesítve. Váltás Bus módra.";
            toggleGameMode();
        }
    }

    private void rewardCleaner(int amount) {
        Role role = gameScreen.getRole();

        if (role instanceof CleanerRole) {
            ((CleanerRole) role).changeMoney(amount);
        }
    }

    private void chargeCleaner(int amount) {
        Role role = gameScreen.getRole();

        if (role instanceof CleanerRole) {
            ((CleanerRole) role).changeMoney(-amount);
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

        if (role instanceof CleanerRole) {
            CleanerRole cleanerRole = (CleanerRole) role;
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
        gameScreen.timeChanged(remainingSeconds);
        gameScreen.moneyChanged();

        if (game.getPlayer() != null) {
            gameScreen.roleChanged(game.getPlayer().getCurrentRole());
        }

        gameScreen.headChanged();
        gameScreen.updateHud(
                getCleanPercent(),
                collisions,
                completedJobs
        );
        gameScreen.updateStockHud();
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
        configuredCarCount = Math.max(0, Math.min(30, count));
        resetTrafficCars();
        message = "Autók száma beállítva: " + configuredCarCount + ".";
        refreshView();
    }

    public static class TrafficCar {
        private final List<int[]> route;
        private int index;
        private int stuckTicks = 0;
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

    public void showHeadSelector() {
        Role role = gameScreen.getRole();

        if (!(role instanceof CleanerRole)) {
            message = "Fejcseréhez Snowplow mód kell.";
            refreshView();
            return;
        }

        CleanerRole cleaner = (CleanerRole) role;

        java.util.List<Head> heads = cleaner.getOwnedHeads();

        if (heads.isEmpty()) {
            message = "Nincs megvásárolt fej.";
            refreshView();
            return;
        }

        String[] names = heads.stream()
                .map(h -> h.getClass().getSimpleName())
                .toArray(String[]::new);

        String selected = (String) javax.swing.JOptionPane.showInputDialog(
                gameScreen,
                "Válaszd ki az aktív fejet:",
                "Fejcsere",
                javax.swing.JOptionPane.PLAIN_MESSAGE,
                null,
                names,
                names[0]
        );

        if (selected == null) return;

        for (Head h : heads) {
            if (h.getClass().getSimpleName().equals(selected)) {
                cleaner.getSnowplow().changeHead(h);
                message = "Aktív fej: " + selected;
                refreshView();
                return;
            }
        }
    }

    public int getRemainingSeconds() {
        return remainingSeconds;
    }

    public boolean isBusMode() {
        return busMode;
    }

    public void toggleGameMode() {
        busMode = !busMode;

        game.setCurrentRound(game.getRound() + 1);

        remainingSeconds = roundDurationSeconds;
        lastSecondUpdate = System.currentTimeMillis();

        playerRow = START_ROW;
        playerCol = START_COL;

        if (busMode) {
            message = "BUS MODE: juss el a depóba.";
        } else {
            message = "SNOWPLOW MODE: takarítsd az utakat.";
        }

        refreshView();
    }


    public void setRoundDurationSeconds(int seconds) {
        roundDurationSeconds = Math.max(30, seconds);
        remainingSeconds = roundDurationSeconds;
        message = "Kör ideje beállítva: " + (roundDurationSeconds / 60) + " perc.";
        refreshView();
    }


    public int getCurrentRoundForDisplay() {
        return game.getRound();
    }
}