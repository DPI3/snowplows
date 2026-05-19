package view;

import javax.swing.*;
import src.*;
import controller.GameController;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class GameScreen extends JFrame {
    private TopPill roundTopPill;
    private TopPill modeTopPill;
    private TopPill moneyTopPill;
    private GrayInfoBox infoBox;
    private JLabel statusLabel;
    private BoardPanel boardPanel;
    private Role role;
    private GameController gameController;

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
        if (boardPanel != null) boardPanel.setGameController(gameController);
    }

    public void roundChanged(int round) {
        if (roundTopPill != null) roundTopPill.setText("Kör: " + round);
    }

    public void moneyChanged() {
        if (moneyTopPill == null || role == null) return;
        if (role instanceof CleanerRole c) moneyTopPill.setText(Integer.toString(c.getMoney()));
        if (role instanceof BusdriverRole b) moneyTopPill.setText(Integer.toString(b.getMoney()));
    }

    public void headChanged() {
        if (infoBox == null) return;
        if (role instanceof CleanerRole c && c.getSnowplow() != null && c.getSnowplow().getCurrentHead() != null) {
            infoBox.setCurrentHeadLabel(c.getSnowplow().getCurrentHead().getClass().getSimpleName());
        }
    }

    public void roleChanged(Role role) {
        this.role = role;
        if (modeTopPill == null) return;
        if (role instanceof CleanerRole) modeTopPill.setText("SNOWPLOW MODE");
        if (role instanceof BusdriverRole) modeTopPill.setText("BUS MODE");
    }

    public Role getRole() { return role; }

    public GameScreen(Role role, Store store) {
        this.role = role;
        setTitle("Snowplow - Game Screen");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        MainBgPanel mainBg = new MainBgPanel();
        mainBg.setLayout(new BorderLayout());

        Image moneyIcon = null;
        try {
            moneyIcon = ImageIO.read(new File("money.png"));
        } catch (Exception e) {
            System.err.println("Nem található a money.png!");
        }

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        topBar.setOpaque(false);
        roundTopPill = new TopPill("Kör: 0", 220, null);
        modeTopPill = new TopPill("SNOWPLOW MODE", 380, null);
        moneyTopPill = new TopPill("", 160, moneyIcon);
        topBar.add(roundTopPill);
        topBar.add(modeTopPill);
        topBar.add(moneyTopPill);
        mainBg.add(topBar, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setOpaque(false);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 20));

        boardPanel = new BoardPanel();
        centerPanel.add(boardPanel, BorderLayout.CENTER);

        statusLabel = new JLabel("Nyomd meg a Startot. Mozgás: WASD/nyilak, takarítás: C.");
        statusLabel.setForeground(Color.decode("#E2E874"));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(statusLabel, BorderLayout.SOUTH);
        mainBg.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(260, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        StyledButton startBtn = new StyledButton("START", 200, 55, Color.decode("#E2E874"));
        startBtn.addActionListener(e -> {
            if (gameController != null) gameController.startGame();
            requestFocusInWindow();
        });
        rightPanel.add(startBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        StyledButton cleanBtn = new StyledButton("CLEAN", 200, 55, Color.decode("#E2E874"));
        cleanBtn.addActionListener(e -> {
            if (gameController != null) gameController.cleanCurrentTile();
            requestFocusInWindow();
        });
        rightPanel.add(cleanBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        StyledButton storeBtn = new StyledButton("STORE", 200, 55, Color.decode("#E2E874"));
        storeBtn.addActionListener(e -> {
            if (gameController != null) gameController.openStore();
        });
        rightPanel.add(storeBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        infoBox = new GrayInfoBox();
        infoBox.setChangeAction(e -> {
            if (gameController != null) gameController.cleanCurrentTile();
            requestFocusInWindow();
        });
        rightPanel.add(infoBox);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 35)));

        StyledButton settingsBtn = new StyledButton("SETTINGS", 200, 55, Color.decode("#E2E874"));
        settingsBtn.addActionListener(e -> {
            if (gameController != null) gameController.openSettings();
        });
        rightPanel.add(settingsBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        StyledButton menuBtn = new StyledButton("MENU", 200, 55, Color.decode("#E2E874"));
        menuBtn.addActionListener(e -> {
            if (gameController != null) gameController.openMenu();
        });
        rightPanel.add(menuBtn);

        mainBg.add(rightPanel, BorderLayout.EAST);
        setContentPane(mainBg);
        moneyChanged();
        headChanged();
    }

    @Override
    public void repaint() {
        super.repaint();
        if (statusLabel != null && gameController != null) {
            statusLabel.setText(gameController.getMessage());
        }
    }

    private static class BoardPanel extends JPanel {
        private GameController gameController;

        public BoardPanel() {
            setOpaque(false);
            setPreferredSize(new Dimension(650, 450));
        }

        public void setGameController(GameController gameController) {
            this.gameController = gameController;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(30, 38, 55, 210));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

            if (gameController == null) {
                g2.setColor(Color.WHITE);
                g2.drawString("Pálya betöltése...", 30, 40);
                g2.dispose();
                return;
            }

            int[][] map = gameController.getRoadMap();
            int rows = map.length;
            int cols = map[0].length;
            int cell = Math.min((getWidth() - 60) / cols, (getHeight() - 60) / rows);
            int startX = (getWidth() - cols * cell) / 2;
            int startY = (getHeight() - rows * cell) / 2;

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int x = startX + c * cell;
                    int y = startY + r * cell;
                    switch (map[r][c]) {
                        case 0 -> g2.setColor(new Color(34, 55, 70));
                        case 1 -> g2.setColor(new Color(115, 130, 145));
                        case 2 -> g2.setColor(new Color(230, 240, 245));
                        case 3 -> g2.setColor(new Color(150, 210, 235));
                        case 4 -> g2.setColor(new Color(226, 232, 116));
                        default -> g2.setColor(Color.GRAY);
                    }
                    g2.fillRoundRect(x + 3, y + 3, cell - 6, cell - 6, 12, 12);
                    g2.setColor(new Color(0, 0, 0, 70));
                    g2.drawRoundRect(x + 3, y + 3, cell - 6, cell - 6, 12, 12);
                }
            }

            int pr = gameController.getPlayerRow();
            int pc = gameController.getPlayerCol();
            int px = startX + pc * cell;
            int py = startY + pr * cell;
            g2.setColor(new Color(238, 134, 149));
            g2.fillOval(px + 8, py + 8, cell - 16, cell - 16);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, cell / 4)));
            g2.drawString("P", px + cell / 2 - 5, py + cell / 2 + 5);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString("Szürke: út | Fehér: hó | Kék: jég | Sárga: terminál/cél", 25, getHeight() - 18);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CleanerRole role = new CleanerRole("Cleaner-1", 300, new Snowplow("1", new Lane(), 10, new SweeperHead()));
            java.util.List<Buyable> l = new ArrayList<>();
            l.add(new SaltSpreaderHead());
            l.add(new GravelSpreaderHead());
            l.add(new IcebreakerHead());
            l.add(new ThrowerHead());
            l.add(new SweeperHead());
            l.add(new DragonHead());
            Store store = new Store(l);
            GameScreen screen = new GameScreen(role, store);
            screen.setVisible(true);
        });
    }
}
