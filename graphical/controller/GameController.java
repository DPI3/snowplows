package controller;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
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
    private static final int SALTED = 11;

    private static final int SNOW_TO_ICE_PASSES = 3;
    private static final int CAR_STUCK_TICKS = 3;
    private static final int SLIDE_LIMIT = 4;

    private final int[][] roadMap = new int[ROWS][COLS];
    private final int[][] snowPressure = new int[ROWS][COLS];
    private final int[][] saltTimers = new int[ROWS][COLS];
    private final int[][] gravelSnowTimers = new int[ROWS][COLS];
    private final List<RoadNode> roadNodes = new ArrayList<>();
    private final List<int[]> roadCells = new ArrayList<>();
    private final List<TrafficCar> trafficCars = new ArrayList<>();

    private boolean busMode = false;
    private final Random random = new Random();

    private int roundDurationSeconds = 300;
    private int remainingSeconds = 300;
    private long lastSecondUpdate = System.currentTimeMillis();

    private int weatherCounter = 0;

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

    private String message = "Cél: takaríts minél több havas és jeges útszakaszt.";

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

        if (weatherCounter >= 3) {
            weatherCounter = 0;
            randomWeatherChange();
        }

        moveTrafficCars();
        updateSaltedRoads();
        refreshView();
    }

    private void updateSaltedRoads() {
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {

                if (roadMap[r][c] == SALTED) {
                    saltTimers[r][c]--;

                    if (saltTimers[r][c] <= 0) {
                        roadMap[r][c] = ROAD;
                        cleanedTiles++;
                    }
                }
            }
        }
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
        for (int i = 0; i < 10; i++) {
            int[] p = getRandomRoadCell();
            if (p == null) return;

            int r = p[0];
            int c = p[1];

            if (roadMap[r][c] == SALTED) {
                continue;
            }

            if (roadMap[r][c] == GRAVEL) {
                gravelSnowTimers[r][c]++;

                if (gravelSnowTimers[r][c] >= 2) {
                    roadMap[r][c] = SNOW;
                    gravelSnowTimers[r][c] = 0;
                    totalDirtyTiles++;
                }

                continue;
            }

            if (roadMap[r][c] == ROAD) {
                double roll = random.nextDouble();

                if (roll < 0.70) {
                    roadMap[r][c] = SNOW;
                } else if (roll < 0.85) {
                    roadMap[r][c] = DEEP_SNOW;
                } else {
                    roadMap[r][c] = ICE;
                }

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

        playerRow = 5;
        playerCol = 1;

        targetRow = -1;
        targetCol = -1;

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
        roadNodes.clear();
        roadCells.clear();

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                roadMap[r][c] = FIELD;
                snowPressure[r][c] = 0;
                saltTimers[r][c] = 0;
                gravelSnowTimers[r][c] = 0;
            }
        }

        generateRoadGraph();

        if (!roadCells.isEmpty()) {
            int[] start = roadCells.get(0);
            playerRow = start[0];
            playerCol = start[1];
            roadMap[playerRow][playerCol] = DEPOT;
        }

        targetRow = -1;
        targetCol = -1;

        placeRandomTunnelsAndBridges();
        addInitialSnowAndIce();
    }

    private void generateRoadGraph() {
        int nodeCount = 10 + random.nextInt(5);

        for (int i = 0; i < nodeCount; i++) {
            int row = 4 + random.nextInt(ROWS - 8);
            int col = 4 + random.nextInt(COLS - 8);

            RoadNode node = new RoadNode("N" + i, row, col);
            roadNodes.add(node);
        }

        roadNodes.sort((a, b) -> Integer.compare(a.displayRow, b.displayRow));

        for (int i = 0; i < roadNodes.size() - 1; i++) {
            connectRoadNodes(roadNodes.get(i), roadNodes.get(i + 1));
        }

        for (int i = 0; i < roadNodes.size() / 2; i++) {
            RoadNode a = roadNodes.get(random.nextInt(roadNodes.size()));
            RoadNode b = roadNodes.get(random.nextInt(roadNodes.size()));

            if (a != b) {
                connectRoadNodes(a, b);
            }
        }
    }

    private void connectRoadNodes(RoadNode a, RoadNode b) {
        a.connect(b);
        int r = a.displayRow;
        int c = a.displayCol;

        markRoadWide(r, c);

        while (r != b.displayRow) {
            r += Integer.compare(b.displayRow, r);
            markRoadWide(r, c);
        }

        while (c != b.displayCol) {
            c += Integer.compare(b.displayCol, c);
            markRoadWide(r, c);
        }
    }

    private void markRoadWide(int row, int col) {
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int r = row + dr;
                int c = col + dc;

                if (r < 0 || r >= ROWS || c < 0 || c >= COLS) continue;

                if (roadMap[r][c] == FIELD) {
                    roadMap[r][c] = ROAD;
                    roadCells.add(new int[]{r, c});
                }
            }
        }
    }

    private int[] getRandomRoadCell() {
        if (roadCells.isEmpty()) return null;

        int[] cell = roadCells.get(random.nextInt(roadCells.size()));
        return new int[]{cell[0], cell[1]};
    }

    private static class RoadNode {
        private final String id;
        private final List<RoadNode> neighbours = new ArrayList<>();

        // csak kirajzoláshoz
        private final int displayRow;
        private final int displayCol;

        private RoadNode(String id, int displayRow, int displayCol) {
            this.id = id;
            this.displayRow = displayRow;
            this.displayCol = displayCol;
        }

        private void connect(RoadNode other) {
            if (!neighbours.contains(other)) {
                neighbours.add(other);
            }
            if (!other.neighbours.contains(this)) {
                other.neighbours.add(this);
            }
        }
    }

    private void addDirtyTile(int r, int c, int type) {
        if (roadMap[r][c] == ROAD) {
            roadMap[r][c] = type;
            totalDirtyTiles++;
        }
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

        for (int i = 0; i < configuredCarCount; i++) {
            int[] start = getRandomRoadCell();

            if (start == null) continue;

            List<int[]> route = new ArrayList<>();
            route.add(new int[]{start[0], start[1]});

            TrafficCar car = new TrafficCar(route, 0, String.valueOf((char)('A' + (i % 26))), i);
            trafficCars.add(car);
        }

        if (roadCells.size() >= 2) {
            int[] a = getRandomRoadCell();
            int[] b = getRandomRoadCell();

            if (a != null && b != null) {
                List<int[]> route = new ArrayList<>();
                route.add(new int[]{a[0], a[1]});

                TrafficCar bus = new TrafficCar(route, 0, "BUS", 99);
                bus.makeBus(a[0], a[1], b[0], b[1]);
                trafficCars.add(bus);
            }
        }
    }

    private void moveTrafficCars() {
        for (TrafficCar car : trafficCars) {
            if (car.stuckTicks > 0) {
                car.stuckTicks--;
                continue;
            }

            int currentRow = car.getRow();
            int currentCol = car.getCol();

            if (currentRow == car.targetRow && currentCol == car.targetCol) {
                car.chooseNewTarget();
            }

            int[] target = findShortestPathNextStep(car, currentRow, currentCol, car.targetRow, car.targetCol);

            if (target == null) {
                car.chooseNewTarget();
                target = findShortestPathNextStep(car, currentRow, currentCol, car.targetRow, car.targetCol);
            }

            int dr = 0;
            int dc = 0;

            if (target != null) {
                dr = Integer.compare(target[0], currentRow);
                dc = Integer.compare(target[1], currentCol);
            }
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

            car.moveTo(rr, cc);
            car.checkBusTerminalReached();

            handleCarTileEffect(rr, cc);
        }
    }

    private int[] findShortestPathNextStep(TrafficCar car, int startRow, int startCol, int goalRow, int goalCol) {
        if (goalRow < 0 || goalCol < 0) return null;

        boolean[][] visited = new boolean[ROWS][COLS];
        int[][] prevRow = new int[ROWS][COLS];
        int[][] prevCol = new int[ROWS][COLS];

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                prevRow[r][c] = -1;
                prevCol[r][c] = -1;
            }
        }

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        int[][] dirs = {
                {1, 0},
                {-1, 0},
                {0, 1},
                {0, -1}
        };

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int r = current[0];
            int c = current[1];

            if (r == goalRow && c == goalCol) {
                break;
            }

            for (int[] dir : dirs) {
                int nr = r + dir[0];
                int nc = c + dir[1];

                if (nr < 0 || nr >= ROWS || nc < 0 || nc >= COLS) continue;
                if (visited[nr][nc]) continue;
                if (!canCarEnter(car, nr, nc)) continue;

                visited[nr][nc] = true;
                prevRow[nr][nc] = r;
                prevCol[nr][nc] = c;
                queue.add(new int[]{nr, nc});
            }
        }

        if (!visited[goalRow][goalCol]) {
            return null;
        }

        int r = goalRow;
        int c = goalCol;

        while (!(prevRow[r][c] == startRow && prevCol[r][c] == startCol)) {
            int pr = prevRow[r][c];
            int pc = prevCol[r][c];

            if (pr == -1 || pc == -1) {
                return null;
            }

            r = pr;
            c = pc;
        }

        return new int[]{r, c};
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

    private boolean isDriveable(int r, int c) {
        if (r < 0 || r >= ROWS || c < 0 || c >= COLS) return false;

        int tile = roadMap[r][c];

        return tile == ROAD
                || tile == DEPOT
                || tile == TUNNEL
                || tile == BRIDGE
                || tile == SNOW
                || tile == ICE
                || tile == SALTED
                || tile == GRAVEL;
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

        message = "Mozgás sikeres.";
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

            if (tile == SNOW || tile == BROKEN_ICE) {

                snowplow.consumeSalt(10);

                roadMap[r][c] = SALTED;
                saltTimers[r][c] = 4;

                return 1;
            }

            if (tile == DEEP_SNOW || tile == ICE) {

                snowplow.consumeSalt(10);

                roadMap[r][c] = SALTED;
                saltTimers[r][c] = 8;

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

        if (head instanceof SweeperHead) {
            if (tile == SNOW || tile == DEEP_SNOW || tile == BROKEN_ICE || tile == GRAVEL) {

                if (tile == SNOW || tile == DEEP_SNOW) {
                    pushSnowNextToRoad(r, c);
                }

                roadMap[r][c] = ROAD;
                cleanedTiles++;
                return 1;
            }

            return 0;
        }

        if (head instanceof ThrowerHead) {
            if (tile == SNOW || tile == DEEP_SNOW || tile == BROKEN_ICE || tile == GRAVEL) {

                if (tile == SNOW || tile == DEEP_SNOW) {
                    throwSnowFarAway(r, c);
                }

                roadMap[r][c] = ROAD;
                cleanedTiles++;
                return 1;
            }

            return 0;
        }

        return 0;
    }

    private void pushSnowNextToRoad(int r, int c) {
        int[][] dirs = {
                {0, 1},
                {1, 0},
                {0, -1},
                {-1, 0}
        };

        for (int[] dir : dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (nr < 0 || nr >= ROWS || nc < 0 || nc >= COLS) continue;

            if (roadMap[nr][nc] == ROAD) {
                roadMap[nr][nc] = SNOW;
                return;
            }

            if (roadMap[nr][nc] == FIELD) {
                return;
            }
        }
    }

    private void throwSnowFarAway(int r, int c) {
        int[][] dirs = {
                {0, 3},
                {3, 0},
                {0, -3},
                {-3, 0}
        };

        for (int[] dir : dirs) {
            int nr = r + dir[0];
            int nc = c + dir[1];

            if (nr < 0 || nr >= ROWS || nc < 0 || nc >= COLS) continue;

            if (roadMap[nr][nc] == ROAD) {
                roadMap[nr][nc] = SNOW;
                return;
            }

            if (roadMap[nr][nc] == FIELD) {
                return;
            }
        }
    }

    private void handleCollision() {
        collisions++;

        int penalty = 40;
        chargeCleaner(penalty);

        int[] depot = getRandomRoadCell();
        if (depot != null) {
            playerRow = depot[0];
            playerCol = depot[1];
        }

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

                    if (targetRow >= 0 && targetCol >= 0 && roadMap[targetRow][targetCol] == DEPOT) {
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
            message = "Jó munka: az utak legalább 70%-a tiszta.";
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

    public class TrafficCar {
        private final List<int[]> route;
        private int index;
        private int stuckTicks = 0;
        private final String label;
        private final int colorIndex;
        private int targetRow;
        private int targetCol;
        private int row;
        private int col;

        private boolean bus;
        private int terminalArow;
        private int terminalAcol;
        private int terminalBrow;
        private int terminalBcol;
        private int busRounds;

        TrafficCar(List<int[]> route, int index, String label, int colorIndex) {
            this.route = route;
            this.index = Math.min(index, route.size() - 1);
            this.row = route.get(this.index)[0];
            this.col = route.get(this.index)[1];
            this.label = label;
            this.colorIndex = colorIndex;
            chooseNewTarget();
        }

        private void makeBus(int aRow, int aCol, int bRow, int bCol) {
            bus = true;
            terminalArow = aRow;
            terminalAcol = aCol;
            terminalBrow = bRow;
            terminalBcol = bCol;
            targetRow = terminalBrow;
            targetCol = terminalBcol;
        }

        private void chooseNewTarget() {
            int[] target = getRandomRoadCell();

            if (target != null) {
                targetRow = target[0];
                targetCol = target[1];
            }
        }

        public void moveTo(int row, int col) {
            this.row = row;
            this.col = col;
        }

        private void checkBusTerminalReached() {
            if (!bus) return;

            if (row == targetRow && col == targetCol) {
                busRounds++;

                if (targetRow == terminalArow && targetCol == terminalAcol) {
                    targetRow = terminalBrow;
                    targetCol = terminalBcol;
                } else {
                    targetRow = terminalArow;
                    targetCol = terminalAcol;
                }
            }
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        public String getLabel() {
            if (bus) {
                return "BUS" + busRounds;
            }

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

        int[] start = getRandomRoadCell();
        if (start != null) {
            playerRow = start[0];
            playerCol = start[1];
        }

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