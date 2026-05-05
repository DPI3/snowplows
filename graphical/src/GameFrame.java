package src;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameFrame extends JFrame {
    private final CardLayout cardLayout;
    private final JPanel cardPanel;

    private MenuPanel menuPanel;
    private JPanel gamePanel;
    private Game currentGame;

    public GameFrame() {
        setTitle("Zuzmaravaros - Hokotro jatek");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setResizable(true);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        menuPanel = new MenuPanel();
        menuPanel.setOnStartGame(this::startGame);
        cardPanel.add(menuPanel, "menu");

        setContentPane(cardPanel);
        cardLayout.show(cardPanel, "menu");
    }

    private void startGame() {
        int rounds = menuPanel.getRounds();
        int vehicleCount = menuPanel.getVehicleCount();
        int playerCount = menuPanel.getPlayerCount();

        currentGame = buildGame(rounds, vehicleCount, playerCount);

        MapPanel mapPanel = new MapPanel(currentGame);
        setupNodePositions(mapPanel, currentGame);

        GameController controller = new GameController(currentGame, mapPanel);

        InfoPanel infoPanel = new InfoPanel(currentGame, mapPanel);

        JLabel statusLabel = new JLabel("Jatek elindult! Nyomj SPACE-t a kovetkezo korhoz.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(Color.WHITE);
        statusLabel.setOpaque(true);
        statusLabel.setBackground(new Color(40, 44, 52));
        statusLabel.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        controller.setStatusLabel(statusLabel);

        JPanel toolbar = createToolbar(controller);

        gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(mapPanel, BorderLayout.CENTER);
        gamePanel.add(infoPanel, BorderLayout.EAST);
        gamePanel.add(statusLabel, BorderLayout.SOUTH);
        gamePanel.add(toolbar, BorderLayout.NORTH);

        mapPanel.setFocusable(true);
        mapPanel.addKeyListener(controller);

        cardPanel.add(gamePanel, "game");
        cardLayout.show(cardPanel, "game");
        mapPanel.requestFocusInWindow();

        currentGame.addObserver(() -> {
            if (currentGame.isOver()) {
                SwingUtilities.invokeLater(this::showResults);
            }
        });
    }

    private JPanel createToolbar(GameController controller) {
        JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 3));
        toolbar.setBackground(new Color(50, 55, 65));

        JButton tickBtn = createToolButton("Kor (SPACE)");
        tickBtn.addActionListener(e -> {
            if (!currentGame.isOver()) {
                currentGame.tick();
            }
            ((Component) e.getSource()).getParent().getParent().requestFocusInWindow();
        });

        JButton cleanBtn = createToolButton("Takaritas (T)");
        cleanBtn.addActionListener(e -> {
            controller.keyPressed(new java.awt.event.KeyEvent(
                    (Component) e.getSource(), 0, 0, 0, java.awt.event.KeyEvent.VK_T, 'T'));
        });

        JButton headBtn = createToolButton("Fej csere (H)");
        headBtn.addActionListener(e -> {
            controller.keyPressed(new java.awt.event.KeyEvent(
                    (Component) e.getSource(), 0, 0, 0, java.awt.event.KeyEvent.VK_H, 'H'));
        });

        JButton storeBtn = createToolButton("Bolt (B)");
        storeBtn.addActionListener(e -> {
            controller.keyPressed(new java.awt.event.KeyEvent(
                    (Component) e.getSource(), 0, 0, 0, java.awt.event.KeyEvent.VK_B, 'B'));
        });

        toolbar.add(tickBtn);
        toolbar.add(cleanBtn);
        toolbar.add(headBtn);
        toolbar.add(storeBtn);

        return toolbar;
    }

    private JButton createToolButton(String text) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.PLAIN, 11));
        b.setBackground(new Color(70, 75, 85));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setFocusable(false);
        return b;
    }

    private void showResults() {
        ResultPanel resultPanel = new ResultPanel(currentGame);
        resultPanel.setOnBackToMenu(() -> {
            cardLayout.show(cardPanel, "menu");
        });
        cardPanel.add(resultPanel, "result");
        cardLayout.show(cardPanel, "result");
    }

    private Game buildGame(int rounds, int vehicleCount, int playerCount) {
        Game game = new Game();
        game.getWeather().setSnowIntensity(2);

        RoadNetwork rn = game.getRoadNetwork();

        Terminal termA = new Terminal("Terminal_A");
        Terminal termB = new Terminal("Terminal_B");
        Intersection i1 = new Intersection("Kereszt_1");
        Intersection i2 = new Intersection("Kereszt_2");
        Intersection i3 = new Intersection("Kereszt_3");
        Intersection i4 = new Intersection("Kereszt_4");
        Residence res1 = new Residence("Lakas_1");
        Workplace work1 = new Workplace("Munkahely_1");

        rn.addNode(termA);
        rn.addNode(termB);
        rn.addNode(i1);
        rn.addNode(i2);
        rn.addNode(i3);
        rn.addNode(i4);
        rn.addNode(res1);
        rn.addNode(work1);

        addRoad(rn, termA, i1, "ut_TA_K1", false, false);
        addRoad(rn, i1, i2, "ut_K1_K2", false, false);
        addRoad(rn, i2, termB, "ut_K2_TB", false, false);
        addRoad(rn, i1, i3, "ut_K1_K3", false, false);
        addRoad(rn, i3, i4, "ut_K3_K4", true, false);
        addRoad(rn, i4, i2, "ut_K4_K2", false, false);
        addRoad(rn, res1, i3, "ut_L1_K3", false, false);
        addRoad(rn, i4, work1, "ut_K4_M1", false, true);

        try {
            java.lang.reflect.Field f = Game.class.getDeclaredField("maxRound");
            f.setAccessible(true);
            f.setInt(game, rounds);
        } catch (Exception ex) {
        }

        Lane startLane = rn.getRoads().get(0).getLanes().get(0);

        Snowplow sp1 = new Snowplow("Hokotro_1", startLane, 50, new ThrowerHead());
        game.addVehicle(sp1);

        if (vehicleCount >= 2) {
            Bus bus1 = new Bus("Busz_1", startLane, 40, termA, termB);
            game.addVehicle(bus1);
        }

        if (vehicleCount >= 3) {
            Car car1 = new Car("Auto_1", startLane, 50, res1, work1);
            game.addVehicle(car1);
        }

        for (int i = 3; i < vehicleCount; i++) {
            Car extraCar = new Car("Auto_" + (i - 1), startLane, 45, res1, work1);
            game.addVehicle(extraCar);
        }

        List<Role> roles1 = new ArrayList<>();
        roles1.add(new CleanerRole("Takarito_1", 100, sp1));
        game.addPlayer(new Player(1, "Jatekos_1", roles1));

        if (playerCount >= 2 && vehicleCount >= 2) {
            List<Role> roles2 = new ArrayList<>();
            Bus bus = null;
            for (Vehicle v : game.getVehicles()) {
                if (v instanceof Bus) { bus = (Bus) v; break; }
            }
            if (bus != null) {
                roles2.add(new BusdriverRole("Buszvezeto_1", bus, rn));
            }
            game.addPlayer(new Player(2, "Jatekos_2", roles2));
        }

        for (int i = 2; i < playerCount; i++) {
            game.addPlayer(new Player(i + 1, "Jatekos_" + (i + 1), new ArrayList<>()));
        }

        return game;
    }

    private void addRoad(RoadNetwork rn, Node src, Node dst, String name, boolean isBridge, boolean isTunnel) {
        Road road;
        if (isTunnel) {
            road = new Tunnel();
        } else if (isBridge) {
            road = new Bridge();
        } else {
            road = new NormalRoad();
        }
        road.setSource(src);
        road.setDestination(dst);

        Lane lane1 = new Lane(name + "_1", src, dst);
        Lane lane2 = new Lane(name + "_2", dst, src);
        road.addLane(lane1);
        road.addLane(lane2);

        rn.addRoad(road);
    }

    private void setupNodePositions(MapPanel mapPanel, Game game) {
        RoadNetwork rn = game.getRoadNetwork();
        List<Node> nodes = rn.getNodes();

        int[][] positions = {
            {100, 300},
            {700, 300},
            {250, 200},
            {550, 200},
            {250, 420},
            {550, 420},
            {100, 500},
            {700, 500},
        };

        for (int i = 0; i < Math.min(nodes.size(), positions.length); i++) {
            mapPanel.setNodePosition(nodes.get(i), positions[i][0], positions[i][1]);
        }
    }
}
