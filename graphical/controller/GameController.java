package controller;

import java.awt.Point;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
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

    // Csak kirajzolási kódok. Játéklogikára nem használjuk.
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

    private int lastDirRow = 0;
    private int lastDirCol = 1;

    private final RoadNetwork roadNetwork = new RoadNetwork();
    private final List<Lane> playableLanes = new ArrayList<>();
    private final Map<Lane, Point> lanePositions = new HashMap<>();
    private final Map<String, Lane> lanesByPosition = new HashMap<>();
    private final Map<Lane, List<Lane>> neighbours = new HashMap<>();
    private final Map<Lane, Integer> snowPressure = new HashMap<>();
    private final Map<Lane, Integer> gravelSnowTimers = new HashMap<>();
    private final Map<Lane, Integer> laneVisualType = new HashMap<>();

    private Lane playerLane;
    private Lane targetLane;

    private final List<RoadNode> roadNodes = new ArrayList<>();
    private final List<TrafficCar> trafficCars = new ArrayList<>();
    private final List<DrawableRoad> drawableRoads = new ArrayList<>();

    private boolean busMode = false;
    private final Random random = new Random();

    private int roundDurationSeconds = 300;
    private int remainingSeconds = 300;
    private long lastSecondUpdate = System.currentTimeMillis();

    private int weatherCounter = 0;

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
                nextRound();
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

    private void nextRound() {
        game.setCurrentRound(game.getRound() + 1);
        remainingSeconds = roundDurationSeconds;
        lastSecondUpdate = System.currentTimeMillis();

        busMode = !busMode;

        Lane start = getRandomLane();

        if (start != null) {
            playerLane = start;
            syncSnowplowToPlayerLane();
        }

        if (busMode) {
            targetLane = getRandomLane();

            if (targetLane != null) {
                laneVisualType.put(targetLane, DEPOT);
            }

            message = "Új kör: BUS MODE következik.";
        } else {
            if (targetLane != null) {
                laneVisualType.put(targetLane, ROAD);
            }

            targetLane = null;
            message = "Új kör: SNOWPLOW MODE következik.";
        }

        gameScreen.setModeText(busMode ? "BUS MODE" : "SNOWPLOW MODE");
        gameScreen.moneyChanged();
    }

    private void updateSaltedRoads() {
        for (Lane lane : playableLanes) {
            boolean wasSalted = lane.isSalted();
            lane.tickSalt();

            if (wasSalted && !lane.isSalted()) {
                cleanedTiles++;
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

    private void restartGameState() {
        running = false;

        game.setCurrentRound(0);
        busMode = false;
        remainingSeconds = roundDurationSeconds;
        lastSecondUpdate = System.currentTimeMillis();

        cleanedTiles = 0;
        totalDirtyTiles = 0;
        collisions = 0;

        buildPlayableMap();
        resetTrafficCars();
        syncSnowplowToPlayerLane();

        message = "Új játék előkészítve. Start gombbal indul.";
        refreshView();
    }

    public void restartGame() {
        stopGame();
        restartGameState();
    }

    private void buildPlayableMap() {
        playableLanes.clear();
        lanePositions.clear();
        lanesByPosition.clear();
        neighbours.clear();
        snowPressure.clear();
        gravelSnowTimers.clear();
        laneVisualType.clear();
        roadNodes.clear();
        drawableRoads.clear();
        roadNetwork.getNodes().clear();
        roadNetwork.getRoads().clear();
        totalDirtyTiles = 0;

        generateRoadGraph();

        if (!playableLanes.isEmpty()) {
            playerLane = playableLanes.get(0);
            laneVisualType.put(playerLane, DEPOT);
        } else {
            playerLane = null;
        }

        targetLane = null;

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

        drawableRoads.add(new DrawableRoad(
                a.displayRow,
                a.displayCol,
                b.displayRow,
                b.displayCol
        ));

        int r = a.displayRow;
        int c = a.displayCol;

        Lane previousCenterLane = markRoadWide(r, c, ROAD);

        while (r != b.displayRow) {
            r += Integer.compare(b.displayRow, r);
            Lane currentCenterLane = markRoadWide(r, c, ROAD);
            connectLanes(previousCenterLane, currentCenterLane);
            previousCenterLane = currentCenterLane;
        }

        while (c != b.displayCol) {
            c += Integer.compare(b.displayCol, c);
            Lane currentCenterLane = markRoadWide(r, c, ROAD);
            connectLanes(previousCenterLane, currentCenterLane);
            previousCenterLane = currentCenterLane;
        }
    }

    private Lane markRoadWide(int row, int col, int visualType) {
        Lane center = null;

        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                Lane lane = getOrCreateLane(row + dr, col + dc, visualType);

                if (lane == null) continue;

                if (dr == 0 && dc == 0) {
                    center = lane;
                }

                Lane right = getLaneAt(row + dr, col + dc + 1);
                Lane down = getLaneAt(row + dr + 1, col + dc);

                connectLanes(lane, right);
                connectLanes(lane, down);
            }
        }

        return center;
    }

    private Lane getOrCreateLane(int row, int col, int visualType) {
        if (row < 0 || row >= ROWS || col < 0 || col >= COLS) return null;

        String key = key(row, col);
        Lane existing = lanesByPosition.get(key);

        if (existing != null) {
            if (!laneVisualType.containsKey(existing)) {
                laneVisualType.put(existing, visualType);
            }
            return existing;
        }

        Road road = createRoadByVisualType(visualType);
        Node source = new Intersection("S_" + row + "_" + col);
        Node destination = new Intersection("D_" + row + "_" + col);

        road.setSource(source);
        road.setDestination(destination);

        Lane lane = new Lane("lane_" + row + "_" + col, source, destination);
        lane.setState(new Clear());
        road.addLane(lane);

        roadNetwork.addNode(source);
        roadNetwork.addNode(destination);
        roadNetwork.addRoad(road);

        playableLanes.add(lane);
        lanePositions.put(lane, new Point(col, row));
        lanesByPosition.put(key, lane);
        neighbours.put(lane, new ArrayList<>());
        laneVisualType.put(lane, visualType);

        return lane;
    }

    private Road createRoadByVisualType(int visualType) {
        if (visualType == BRIDGE) return new Bridge();
        if (visualType == TUNNEL) return new Tunnel();
        return new NormalRoad();
    }

    private void connectLanes(Lane a, Lane b) {
        if (a == null || b == null || a == b) return;

        neighbours.computeIfAbsent(a, k -> new ArrayList<>());
        neighbours.computeIfAbsent(b, k -> new ArrayList<>());

        if (!neighbours.get(a).contains(b)) {
            neighbours.get(a).add(b);
        }

        if (!neighbours.get(b).contains(a)) {
            neighbours.get(b).add(a);
        }
    }

    private Lane getLaneAt(int row, int col) {
        return lanesByPosition.get(key(row, col));
    }

    private String key(int row, int col) {
        return row + ":" + col;
    }

    private Lane getRandomLane() {
        if (playableLanes.isEmpty()) return null;
        return playableLanes.get(random.nextInt(playableLanes.size()));
    }

    private static class RoadNode {
        private final String id;
        private final List<RoadNode> neighbours = new ArrayList<>();
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

    private void placeRandomTunnelsAndBridges() {
        for (int i = 0; i < 6; i++) {
            placeRoadSegment(TUNNEL, 3 + random.nextInt(4));
        }

        for (int i = 0; i < 6; i++) {
            placeRoadSegment(BRIDGE, 3 + random.nextInt(4));
        }
    }

    private void placeRoadSegment(int visualType, int length) {
        Lane start = getRandomLane();
        if (start == null) return;

        Point p = lanePositions.get(start);
        if (p == null) return;

        boolean horizontal = random.nextBoolean();

        List<Lane> segment = new ArrayList<>();

        for (int i = 0; i < length; i++) {
            int row = horizontal ? p.y : p.y + i;
            int col = horizontal ? p.x + i : p.x;

            Lane lane = getLaneAt(row, col);

            if (lane == null) return;
            if (lane == playerLane) return;

            segment.add(lane);
        }

        for (Lane lane : segment) {
            laneVisualType.put(lane, visualType);
        }
    }

    private void addInitialSnowAndIce() {
        for (int i = 0; i < 35; i++) {
            Lane lane = getRandomLane();

            if (lane == null || lane == playerLane) continue;

            if (random.nextBoolean()) {
                lane.setState(new ThinSnow());
            } else {
                lane.setState(new IceSheet());
            }

            totalDirtyTiles++;
        }
    }

    private void randomWeatherChange() {
        if (playableLanes.isEmpty()) return;

        for (int i = 0; i < 10; i++) {
            Lane lane = getRandomLane();

            if (lane == null || lane.isSalted()) {
                continue;
            }

            LaneState state = lane.getLaneState();

            if (state instanceof Gravel) {
                int counter = gravelSnowTimers.getOrDefault(lane, 0) + 1;
                gravelSnowTimers.put(lane, counter);

                if (counter >= 2) {
                    lane.setState(new ThinSnow());
                    gravelSnowTimers.put(lane, 0);
                    totalDirtyTiles++;
                }

                continue;
            }

            if (state instanceof Clear) {
                double roll = random.nextDouble();

                if (roll < 0.70) {
                    lane.setState(new ThinSnow());
                } else if (roll < 0.85) {
                    lane.setState(new DeepSnow());
                } else {
                    lane.setState(new IceSheet());
                }

                totalDirtyTiles++;
            }
        }

        message = "Időjárás: új hó vagy jég jelent meg a pályán.";
    }

    private void resetTrafficCars() {
        trafficCars.clear();

        for (int i = 0; i < configuredCarCount; i++) {
            Lane start = getRandomLane();

            if (start == null) continue;

            TrafficCar car = new TrafficCar(start, String.valueOf((char) ('A' + (i % 26))), i);
            trafficCars.add(car);
        }

        if (playableLanes.size() >= 2) {
            Lane a = getRandomLane();
            Lane b = getRandomLane();

            if (a != null && b != null && a != b) {
                TrafficCar bus = new TrafficCar(a, "BUS", 99);
                bus.makeBus(a, b);
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

            if (car.currentLane == car.targetLane) {
                car.chooseNewTarget();
            }

            Lane next = findShortestPathNextStep(car, car.currentLane, car.targetLane);

            if (next == null) {
                car.chooseNewTarget();
                next = findShortestPathNextStep(car, car.currentLane, car.targetLane);
            }

            if (next == null) {
                car.stuckTicks = 1;
                continue;
            }

            int dr = getDirectionRow(car.currentLane, next);
            int dc = getDirectionCol(car.currentLane, next);

            Lane destination = next;

            if (destination.getLaneState() instanceof IceSheet) {
                destination = slideOnIce(car, destination, dr, dc);

                if (destination == null) {
                    continue;
                }
            }

            TrafficCar other = getTrafficCarAt(car, destination);

            if (other != null) {
                crashCars(car, other, destination);
                continue;
            }

            car.moveTo(destination);
            car.checkBusTerminalReached();

            handleCarLaneEffect(destination);
        }
    }

    private Lane findShortestPathNextStep(TrafficCar car, Lane start, Lane goal) {
        if (start == null || goal == null) return null;

        Set<Lane> visited = new HashSet<>();
        Map<Lane, Lane> previous = new HashMap<>();
        Queue<Lane> queue = new ArrayDeque<>();

        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Lane current = queue.poll();

            if (current == goal) {
                break;
            }

            for (Lane next : neighbours.getOrDefault(current, new ArrayList<>())) {
                if (visited.contains(next)) continue;
                if (!canCarEnter(car, next)) continue;

                visited.add(next);
                previous.put(next, current);
                queue.add(next);
            }
        }

        if (!visited.contains(goal)) {
            return null;
        }

        Lane current = goal;

        while (previous.containsKey(current) && previous.get(current) != start) {
            current = previous.get(current);
        }

        if (previous.get(current) == start) {
            return current;
        }

        return goal == start ? start : null;
    }

    private boolean canCarEnter(TrafficCar self, Lane lane) {
        if (lane == null) return false;
        if (lane.hasAccident()) return false;

        LaneState state = lane.getLaneState();

        if (state instanceof DeepSnow || state instanceof BrokenIce) {
            return false;
        }

        TrafficCar other = getTrafficCarAt(self, lane);

        return other == null || state instanceof IceSheet;
    }

    private Lane slideOnIce(TrafficCar car, Lane start, int dr, int dc) {
        Lane current = start;

        for (int i = 0; i < SLIDE_LIMIT; i++) {
            TrafficCar other = getTrafficCarAt(car, current);

            if (other != null) {
                crashCars(car, other, current);
                return null;
            }

            Lane next = findNeighbourLaneByDirection(current, dr, dc);

            if (next == null || !canCarEnter(car, next)) {
                return current;
            }

            current = next;

            if (!(current.getLaneState() instanceof IceSheet)) {
                return current;
            }
        }

        return current;
    }

    private void handleCarLaneEffect(Lane lane) {
        if (lane == null) return;

        if (lane.getLaneState() instanceof ThinSnow) {
            int pressure = snowPressure.getOrDefault(lane, 0) + 1;
            snowPressure.put(lane, pressure);

            if (pressure >= SNOW_TO_ICE_PASSES) {
                lane.setState(new IceSheet());
                snowPressure.put(lane, 0);
                message = "Az autók letaposták a havat, ezért jégpáncél alakult ki.";
            }
        }
    }

    private TrafficCar getTrafficCarAt(TrafficCar self, Lane lane) {
        for (TrafficCar car : trafficCars) {
            if (car != self && car.currentLane == lane) {
                return car;
            }
        }

        return null;
    }

    private void crashCars(TrafficCar first, TrafficCar second, Lane lane) {
        collisions++;

        first.stuckTicks = CAR_STUCK_TICKS;
        second.stuckTicks = CAR_STUCK_TICKS;

        if (lane != null) {
            lane.setHasAccident(true);
            snowPressure.put(lane, 0);
        }

        message = "Autóbaleset történt, a sáv járhatatlanná vált.";
    }

    private void movePlayer(int dr, int dc) {
        if (!running) {
            message = "A játék nem fut. Nyomd meg a START gombot.";
            return;
        }

        Lane next = findNeighbourLaneByDirection(playerLane, dr, dc);

        if (next == null) {
            message = "Nem lehet lemenni a pályáról.";
            return;
        }

        if (!next.isPassable()) {
            message = "Csak járható úton, hídon vagy alagútban haladhatsz.";
            return;
        }

        playerLane = next;
        lastDirRow = dr;
        lastDirCol = dc;

        syncSnowplowToPlayerLane();

        message = "Mozgás sikeres.";

        checkPlayerTrafficCollision();
        checkMissionEnd();
        refreshView();
    }

    private void checkPlayerTrafficCollision() {
        if (!busMode || playerLane == null) return;

        for (TrafficCar car : trafficCars) {
            if (car.currentLane == playerLane) {
                collisions++;
                car.stuckTicks = CAR_STUCK_TICKS;

                playerLane.setHasAccident(true);

                int penalty = 30;
                chargeCurrentRole(penalty);

                message = "A busz ütközött egy járművel. -" + penalty + " pénz.";
                return;
            }
        }
    }

    private void chargeCurrentRole(int amount) {
        Role role = gameScreen.getRole();

        if (role instanceof CleanerRole) {
            ((CleanerRole) role).changeMoney(-amount);
        }

        if (role instanceof BusdriverRole) {
            ((BusdriverRole) role).changeMoney(-amount);
        }
    }

    private Lane findNeighbourLaneByDirection(Lane from, int dr, int dc) {
        if (from == null) return null;

        Point p = lanePositions.get(from);
        if (p == null) return null;

        return getLaneAt(p.y + dr, p.x + dc);
    }

    private int getDirectionRow(Lane from, Lane to) {
        Point a = lanePositions.get(from);
        Point b = lanePositions.get(to);

        if (a == null || b == null) return 0;
        return Integer.compare(b.y, a.y);
    }

    private int getDirectionCol(Lane from, Lane to) {
        Point a = lanePositions.get(from);
        Point b = lanePositions.get(to);

        if (a == null || b == null) return 0;
        return Integer.compare(b.x, a.x);
    }

    public void cleanCurrentTile() {
        cleanAroundPlayer();
    }

    public void cleanAroundPlayer() {
        if (!running) {
            message = "Takarításhoz előbb indítsd el a játékot.";
            return;
        }

        Lane target = findNeighbourLaneByDirection(playerLane, lastDirRow, lastDirCol);

        int cleaned = cleanLane(target);

        if (cleaned > 0) {
            int reward = cleaned * 25 * plowLevel;
            rewardCleaner(reward);
            message = "Takarítás az aktuális irányba: +" + reward + " pénz.";
        } else {
            message = "Nincs takarítható sáv a hókotró előtt.";
        }

        checkMissionEnd();
        refreshView();
    }

    private int cleanLane(Lane lane) {
        if (lane == null) return 0;

        Role role = gameScreen.getRole();
        if (!(role instanceof CleanerRole)) return 0;

        CleanerRole cleaner = (CleanerRole) role;
        Snowplow snowplow = cleaner.getSnowplow();

        if (snowplow == null || snowplow.getCurrentHead() == null) return 0;

        Head head = snowplow.getCurrentHead();
        LaneState before = lane.getLaneState();

        if (before instanceof Clear && !lane.isSalted()) {
            return 0;
        }

        if (head instanceof DragonHead) {
            if (snowplow.getBiokeroseneStock() < 10) return 0;

            if (before instanceof ThinSnow ||
                before instanceof DeepSnow ||
                before instanceof IceSheet ||
                before instanceof BrokenIce) {

                snowplow.consumeBiokerosene(10);
                lane.setState(new Clear());
                cleanedTiles++;
                return 1;
            }

            return 0;
        }

        if (head instanceof IcebreakerHead) {
            if (before instanceof IceSheet) {
                lane.setState(new BrokenIce());
                return 1;
            }

            return 0;
        }

        if (head instanceof SaltSpreaderHead) {
            if (snowplow.getSaltStock() < 10) return 0;

            if (before instanceof ThinSnow || before instanceof BrokenIce) {
                snowplow.consumeSalt(10);
                lane.applySalt(4);
                return 1;
            }

            if (before instanceof DeepSnow || before instanceof IceSheet) {
                snowplow.consumeSalt(10);
                lane.applySalt(8);
                return 1;
            }

            return 0;
        }

        if (head instanceof GravelSpreaderHead) {
            if (snowplow.getGravelStock() < 10) return 0;

            if (before instanceof IceSheet) {
                snowplow.consumeGravel(10);
                lane.setState(new Gravel());
                return 1;
            }

            return 0;
        }

        if (head instanceof SweeperHead) {
            return moveMaterialInArrowDirection(lane, 1);
        }

        if (head instanceof ThrowerHead) {
            return moveMaterialInArrowDirection(lane, 3);
        }

        snowplow.clean(lane);

        LaneState after = lane.getLaneState();

        if (after != null && before != null && after.getClass() != before.getClass()) {
            cleanedTiles++;
            return 1;
        }

        return 0;
    }

    private int moveMaterialInArrowDirection(Lane lane, int distance) {
        LaneState material = lane.removeMovableMaterial();

        if (material == null) return 0;

        Lane target = lane;

        for (int i = 0; i < distance; i++) {
            target = findNeighbourLaneByDirection(target, lastDirRow, lastDirCol);

            if (target == null) {
                cleanedTiles++;
                return 1;
            }
        }

        if (target.getLaneState() instanceof Clear && !target.hasAccident()) {
            target.placeMaterial(material);
        }

        cleanedTiles++;
        return 1;
    }

    private void handleCollision() {
        collisions++;

        int penalty = 40;
        chargeCleaner(penalty);

        Lane depot = getRandomLane();

        if (depot != null) {
            playerLane = depot;
            syncSnowplowToPlayerLane();
        }

        message = "Ütközés történt. -" + penalty + " pénz, visszakerültél a depóba.";
        refreshView();
    }

    private void checkMissionEnd() {
        if (busMode) {
            if (playerLane == targetLane) {
                completedJobs++;

                Role role = gameScreen.getRole();

                if (role instanceof BusdriverRole) {
                    ((BusdriverRole) role).changeMoney(100);
                }

                Lane oldTarget = targetLane;
                Lane newTarget = getRandomLane();

                if (oldTarget != null) {
                    laneVisualType.put(oldTarget, ROAD);
                }

                if (newTarget != null) {
                    targetLane = newTarget;
                    laneVisualType.put(targetLane, DEPOT);
                }

                message = "Busz cél teljesítve: +100 pénz. Új célállomás kijelölve.";
            }

            return;
        }

        if (getCleanPercent() >= 70) {
            completedJobs++;

            Role role = gameScreen.getRole();

            if (role instanceof CleanerRole) {
                ((CleanerRole) role).changeMoney(100);
            }

            message = "Jó munka: az utak legalább 70%-a tiszta. +100 pénz.";
        }
    }

    private void syncSnowplowToPlayerLane() {
        Role role = gameScreen.getRole();

        if (role instanceof CleanerRole) {
            Snowplow snowplow = ((CleanerRole) role).getSnowplow();

            if (snowplow != null) {
                snowplow.setCurrentLane(playerLane);
                snowplow.setFacingLane(playerLane);
            }
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

    public List<Lane> getPlayableLanes() {
        return playableLanes;
    }

    public Point getLanePosition(Lane lane) {
        return lanePositions.get(lane);
    }

    public Lane getPlayerLane() {
        return playerLane;
    }

    public Lane getTargetLane() {
        return targetLane;
    }

    /*
     * Csak a régi GameScreen kompatibilitása miatt marad.
     * Nem tárolt játékállapot, hanem minden repaint előtt Lane objektumokból számolt kirajzolási másolat.
     */
    public int[][] getRoadMap() {
        int[][] viewMap = new int[ROWS][COLS];

        for (Lane lane : playableLanes) {
            Point p = lanePositions.get(lane);
            if (p == null) continue;

            int visual = laneToVisualCode(lane);
            viewMap[p.y][p.x] = visual;
        }

        return viewMap;
    }

    private int laneToVisualCode(Lane lane) {
        if (lane == null) return FIELD;

        if (lane.hasAccident()) {
            return CRASHED_LANE;
        }

        if (lane.isSalted()) {
            return SALTED;
        }

        LaneState state = lane.getLaneState();

        if (state instanceof ThinSnow) return SNOW;
        if (state instanceof DeepSnow) return DEEP_SNOW;
        if (state instanceof IceSheet) return ICE;
        if (state instanceof BrokenIce) return BROKEN_ICE;
        if (state instanceof Gravel) return GRAVEL;

        return laneVisualType.getOrDefault(lane, ROAD);
    }

    public int getPlayerRow() {
        Point p = lanePositions.get(playerLane);
        return p == null ? -1 : p.y;
    }

    public int getPlayerCol() {
        Point p = lanePositions.get(playerLane);
        return p == null ? -1 : p.x;
    }

    public int getTargetRow() {
        Point p = lanePositions.get(targetLane);
        return p == null ? -1 : p.y;
    }

    public int getTargetCol() {
        Point p = lanePositions.get(targetLane);
        return p == null ? -1 : p.x;
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

    public int getLastDirRow() {
        return lastDirRow;
    }

    public int getLastDirCol() {
        return lastDirCol;
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
        private int stuckTicks = 0;
        private final String label;
        private final int colorIndex;

        private Lane currentLane;
        private Lane targetLane;

        private boolean bus;
        private Lane terminalA;
        private Lane terminalB;
        private int busRounds;

        TrafficCar(Lane startLane, String label, int colorIndex) {
            this.currentLane = startLane;
            this.label = label;
            this.colorIndex = colorIndex;
            chooseNewTarget();
        }

        private void makeBus(Lane a, Lane b) {
            bus = true;
            terminalA = a;
            terminalB = b;
            currentLane = a;
            targetLane = b;
        }

        private void chooseNewTarget() {
            Lane target = getRandomLane();

            if (target != null) {
                targetLane = target;
            }
        }

        public void moveTo(Lane lane) {
            this.currentLane = lane;
        }

        private void checkBusTerminalReached() {
            if (!bus) return;

            if (currentLane == targetLane) {
                busRounds++;

                if (targetLane == terminalA) {
                    targetLane = terminalB;
                } else {
                    targetLane = terminalA;
                }
            }
        }

        public int getRow() {
            Point p = lanePositions.get(currentLane);
            return p == null ? -1 : p.y;
        }

        public int getCol() {
            Point p = lanePositions.get(currentLane);
            return p == null ? -1 : p.x;
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

        remainingSeconds = roundDurationSeconds;
        lastSecondUpdate = System.currentTimeMillis();

        Lane start = getRandomLane();

        if (start != null) {
            playerLane = start;
            syncSnowplowToPlayerLane();
        }

        if (busMode) {
            targetLane = getRandomLane();

            if (targetLane != null) {
                laneVisualType.put(targetLane, DEPOT);
            }

            message = "BUS MODE: juss el a depóba.";
        } else {
            targetLane = null;
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

    public List<DrawableRoad> getDrawableRoads() {
        return drawableRoads;
    }

    public static class DrawableRoad {
        public final int fromRow;
        public final int fromCol;
        public final int toRow;
        public final int toCol;

        public DrawableRoad(int fromRow, int fromCol, int toRow, int toCol) {
            this.fromRow = fromRow;
            this.fromCol = fromCol;
            this.toRow = toRow;
            this.toCol = toCol;
        }
    }
}