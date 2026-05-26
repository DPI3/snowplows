package view;

import controller.GameController;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import src.*;

/**
 * A játék fő képernyője, amely tartalmazza a játékteret, a felső információs sávot,
 * a jobb oldali vezérlőpanelt és a HUD elemeket.
 */
public class GameScreen extends JFrame {
    /** A kör és idő információt megjelenítő kapszula. */
    private TopPill roundTopPill;
    /** A játékmódot megjelenítő kapszula. */
    private TopPill modeTopPill;
    /** A pénzösszeget megjelenítő kapszula. */
    private TopPill moneyTopPill;

    /** Az állapotüzenetet megjelenítő címke. */
    private JLabel statusLabel;
    /** A tisztítási százalékot megjelenítő HUD címke. */
    private JLabel cleanLabel;
    /** Az ütközések számát megjelenítő HUD címke. */
    private JLabel collisionLabel;
    /** A teljesített küldetések számát megjelenítő HUD címke. */
    private JLabel completedLabel;

    /** A fej információkat és váltó gombot tartalmazó doboz. */
    private GrayInfoBox infoBox;
    /** A játékteret megjelenítő panel. */
    private BoardPanel boardPanel;

    /** A játékvezérlő hivatkozása. */
    private GameController gameController;
    /** A szöveg színének hexadecimális kódja. */
    private String TEXT_COLOR = "#E2E874";

    /** A só készletet megjelenítő HUD címke. */
    private JLabel saltStockLabel;
    /** A biokerozin készletet megjelenítő HUD címke. */
    private JLabel bioStockLabel;
    /** A kavics készletet megjelenítő HUD címke. */
    private JLabel gravelStockLabel;

    /**
     * Létrehozza a játék képernyőt a megadott szereppel és bolttal,
     * felépíti a teljes felhasználói felületet.
     *
     * @param role  a játékos szerepe
     * @param store a bolt objektum
     */
    public GameScreen(Role role, Store store) {

        setTitle("Snowplow - Game Screen");
        setSize(1400, 850);
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

        roundTopPill = new TopPill("Kör: 0 | Idő: 05:00", 320, null);
        modeTopPill = new TopPill("SNOWPLOW MODE", 380, null);

        modeTopPill.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (gameController != null) {
                    gameController.toggleGameMode();
                }
            }
        });

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

        statusLabel = new JLabel("Nyomd meg a START gombot. Mozgás: WASD / nyilak, takarítás: C.");
        statusLabel.setForeground(Color.decode(TEXT_COLOR));
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        statusLabel.setHorizontalAlignment(SwingConstants.CENTER);

        centerPanel.add(statusLabel, BorderLayout.SOUTH);
        mainBg.add(centerPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(270, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(30, 20, 20, 20));

        StyledButton startBtn = new StyledButton("START", 200, 45, Color.decode(TEXT_COLOR));
        startBtn.addActionListener(e -> {
            if (gameController != null) {
                gameController.startGame();
            }
            requestFocusInWindow();
        });

        StyledButton cleanBtn = new StyledButton("CLEAN", 200, 45, Color.decode(TEXT_COLOR));
        cleanBtn.addActionListener(e -> {
            if (gameController != null) {
                gameController.cleanCurrentTile();
            }
            requestFocusInWindow();
        });

        StyledButton resetBtn = new StyledButton("RESET", 200, 45, Color.decode(TEXT_COLOR));
        resetBtn.addActionListener(e -> {
            if (gameController != null) {
                gameController.restartGame();
            }
            requestFocusInWindow();
        });

        StyledButton storeBtn = new StyledButton("STORE", 200, 45, Color.decode(TEXT_COLOR));
        storeBtn.addActionListener(e -> {
            if (gameController != null) {
                gameController.openStore();
            }
        });

        StyledButton settingsBtn = new StyledButton("SETTINGS", 200, 45, Color.decode(TEXT_COLOR));
        settingsBtn.addActionListener(e -> {
            if (gameController != null) {
                gameController.openSettings();
            }
        });

        StyledButton menuBtn = new StyledButton("MENU", 200, 45, Color.decode(TEXT_COLOR));
        menuBtn.addActionListener(e -> {
            if (gameController != null) {
                gameController.openMenu();
            }
        });

        rightPanel.add(startBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(cleanBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(resetBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        infoBox = new GrayInfoBox();
        infoBox.setChangeAction(e -> {
            if (gameController != null) {
                gameController.showHeadSelector();
            }
            requestFocusInWindow();
        });

        rightPanel.add(infoBox);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        JPanel hudPanel = createHudPanel();
        rightPanel.add(hudPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 15)));

        rightPanel.add(storeBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(settingsBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        rightPanel.add(menuBtn);

        mainBg.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainBg);

        moneyChanged();
        infoBox.setCurrentHeadLabel(((CleanerRole)role).getSnowplow().getCurrentHead().getClass().getSimpleName());

    }

    /**
     * Létrehozza a HUD panelt a tisztítás, ütközés, küldetés és készlet címkékkel.
     *
     * @return a HUD elemeket tartalmazó panel
     */
    private JPanel createHudPanel() {
        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        cleanLabel = createHudLabel("Tisztítás: 0%");
        collisionLabel = createHudLabel("Ütközés: 0");
        completedLabel = createHudLabel("Küldetés: 0");
        saltStockLabel = createHudLabel("Só: 0%");
        bioStockLabel = createHudLabel("Biokerozin: 0%");
        gravelStockLabel = createHudLabel("Gravel: 0%");

        panel.add(cleanLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(collisionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(completedLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(saltStockLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(bioStockLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(gravelStockLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));

        return panel;
    }

    /**
     * Létrehoz egy fehér, félkövér HUD címkét a megadott szöveggel.
     *
     * @param text a címke szövege
     * @return az elkészített HUD címke
     */
    private JLabel createHudLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("SansSerif", Font.BOLD, 13));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Beállítja a játékvezérlőt és átadja a tábla panelnek is.
     *
     * @param gameController a játékvezérlő
     */
    public void setGameController(GameController gameController) {
        this.gameController = gameController;

        if (boardPanel != null) {
            boardPanel.setGameController(gameController);
        }
    }

    /**
     * Frissíti a kör kijelzőt az új körszámmal.
     *
     * @param round az aktuális kör száma
     */
    public void roundChanged(int round) {
        if (roundTopPill != null) {
            roundTopPill.setText("Kör: " + round);
        }
    }

    /**
     * Frissíti a pénz kijelzőt a játékos aktuális egyenlegével.
     */
    public void moneyChanged() {
        if (moneyTopPill == null || gameController == null) return;

        if (gameController.getRole() instanceof CleanerRole) {
            moneyTopPill.setText(Integer.toString(((CleanerRole) gameController.getRole()).getMoney()));
        }

        if (gameController.getRole() instanceof BusdriverRole) {
            moneyTopPill.setText(Integer.toString(((BusdriverRole) gameController.getRole()).getMoney()));
        }
    }

    /**
     * Frissíti a fej információs dobozt az aktuális hókotró fejének nevével.
     */
    public void headChanged() {
        if (infoBox == null) return;
        if(gameController == null) {
            infoBox.setCurrentHeadLabel("DEFAULT");
            return;
        }
        if (gameController.getRole() instanceof CleanerRole) {
            CleanerRole c = (CleanerRole) gameController.getRole();
            if (c.getSnowplow() != null && c.getSnowplow().getCurrentHead() != null) {
                infoBox.setCurrentHeadLabel(c.getSnowplow().getCurrentHead().getClass().getSimpleName());
            } else {
                infoBox.setCurrentHeadLabel("DEFAULT");
            }
        } else {
            infoBox.setCurrentHeadLabel("DEFAULT");
        }
    }

    /**
     * Frissíti a játékmód kijelzőt a vezérlő aktuális módjának megfelelően.
     */
    public void roleChanged() {

        if (modeTopPill == null || gameController == null) return;

        modeTopPill.setText(gameController.isBusMode() ? "BUS MODE" : "SNOWPLOW MODE");
    }

    /**
     * Frissíti a HUD elemeket a tisztítási százalékkal, ütközések és teljesített küldetések számával.
     *
     * @param cleanPercent   a tisztított terület százaléka
     * @param collisions     az ütközések száma
     * @param completedJobs  a teljesített küldetések száma
     */
    public void updateHud(int cleanPercent, int collisions, int completedJobs) {
        if (cleanLabel != null) cleanLabel.setText("Tisztítás: " + cleanPercent + "% / 70%");
        if (collisionLabel != null) collisionLabel.setText("Ütközés: " + collisions);
        if (completedLabel != null) completedLabel.setText("Küldetés: " + completedJobs);
    }

    /**
     * Visszaadja a játékos aktuális szerepét.
     *
     * @return a játékos szerepe
     */
    public Role getRole() {
        return gameController.getRole();
    }

    /**
     * Újrarajzolja a képernyőt és frissíti az állapotüzenetet.
     */
    @Override
    public void repaint() {
        super.repaint();

        if (statusLabel != null && gameController != null) {
            statusLabel.setText(gameController.getMessage());
        }
    }

    /**
     * Frissíti az idő kijelzőt a megadott másodpercértékkel.
     *
     * @param seconds a hátralévő idő másodpercben
     */
    public void timeChanged(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;

        if (roundTopPill != null) {
            int round = 0;

            if (gameController != null) {
                round = gameController.getCurrentRoundForDisplay();
            }

            roundTopPill.setText(String.format("Kör: %d | Idő: %02d:%02d",
                    round,
                    min,
                    sec));
        }
    }

    /**
     * Beállítja a mód kapszula szövegét.
     *
     * @param text a megjelenítendő szöveg
     */
    public void setModeText(String text) {
        if (modeTopPill != null) {
            modeTopPill.setText(text);
        }
    }

    /**
     * Frissíti a készlet HUD címkéit a hókotró aktuális só, biokerozin és kavics készletével.
     */
    public void updateStockHud() {
        if(gameController == null) return;
        if (!(getRole() instanceof CleanerRole)) return;

        Snowplow snowplow = ((CleanerRole) getRole()).getSnowplow();
        if (snowplow == null) return;

        if (saltStockLabel != null) {
            saltStockLabel.setText("Só: " + snowplow.getSaltStock() + "%");
        }

        if (bioStockLabel != null) {
            bioStockLabel.setText("Biokerozin: " + snowplow.getBiokeroseneStock() + "%");
        }

        if (gravelStockLabel != null) {
            gravelStockLabel.setText("Gravel: " + snowplow.getGravelStock() + "%");
        }
    }

    /**
     * A játékteret megjelenítő belső panel, amely kirajzolja a térképet,
     * a hókotrót, a forgalmi autókat, a minitérképet és a jelmagyarázatot.
     */
    private class BoardPanel extends JPanel {
        /** A játékvezérlő hivatkozása. */
        private GameController gameController;

        /**
         * Létrehoz egy új játéktér panelt alapértelmezett mérettel.
         */
        public BoardPanel() {
            setOpaque(false);
            setPreferredSize(new Dimension(700, 520));
        }

        /**
         * Beállítja a játékvezérlőt a panelhez.
         *
         * @param gameController a játékvezérlő
         */
        public void setGameController(GameController gameController) {
            this.gameController = gameController;
        }

        /**
         * Kirajzolja a teljes játékteret: háttér, térkép, utak, járművek,
         * minitérkép és jelmagyarázat.
         *
         * @param g a grafikus kontextus
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            GradientPaint bg = new GradientPaint(
                    0,
                    0,
                    new Color(20, 30, 48),
                    0,
                    getHeight(),
                    new Color(9, 18, 32)
            );

            g2.setPaint(bg);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

            drawSnowyBackground(g2);

            if (gameController == null) {
                g2.setColor(Color.WHITE);
                g2.drawString("Pálya betöltése...", 30, 40);
                g2.dispose();
                return;
            }

            int[][] map = gameController.getRoadMap();

            int rows = map.length;
            int cols = map[0].length;

            int cell = 42;

            int visibleCols = Math.max(1, (getWidth() - 70) / cell);
            int visibleRows = Math.max(1, (getHeight() - 90) / cell);

            int cameraCol = gameController.getPlayerCol() - visibleCols / 2;
            int cameraRow = gameController.getPlayerRow() - visibleRows / 2;

            cameraCol = Math.max(0, Math.min(cameraCol, cols - visibleCols));
            cameraRow = Math.max(0, Math.min(cameraRow, rows - visibleRows));

            int startX = 25 - cameraCol * cell;
            int startY = 25 - cameraRow * cell;

            drawMapTiles(g2, map, rows, cols, cell, startX, startY);
            drawRoadDetails(g2, map, rows, cols, cell, startX, startY);
            drawTrafficCars(g2, cell, startX, startY);

            if (gameController.isBusMode()) {
                drawBus(
                        g2,
                        startX + gameController.getPlayerCol() * cell,
                        startY + gameController.getPlayerRow() * cell,
                        cell
                );
            } else {
                drawSnowplow(
                        g2,
                        startX + gameController.getPlayerCol() * cell,
                        startY + gameController.getPlayerRow() * cell,
                        cell
                );
            }

            drawMiniMap(g2, map, rows, cols);
            drawMissionOverlay(g2);
            drawLegend(g2);

            g2.dispose();
        }

        /**
         * Kirajzolja a havas háttér dekorációs elemeit.
         *
         * @param g2 a grafikus kontextus
         */
        private void drawSnowyBackground(Graphics2D g2) {
            g2.setColor(new Color(235, 247, 255, 22));

            for (int i = 0; i < 60; i++) {
                int x = (i * 47 + 23) % Math.max(1, getWidth());
                int y = (i * 31 + 17) % Math.max(1, getHeight());
                int size = 2 + (i % 3);

                g2.fillOval(x, y, size, size);
            }
        }

        /**
         * Kirajzolja a térkép csempéit a megadott paraméterek alapján.
         *
         * @param g2     a grafikus kontextus
         * @param map    a térkép mátrix
         * @param rows   a sorok száma
         * @param cols   az oszlopok száma
         * @param cell   a cella mérete pixelben
         * @param startX a kirajzolás kezdő X koordinátája
         * @param startY a kirajzolás kezdő Y koordinátája
         */
        private void drawMapTiles(Graphics2D g2, int[][] map, int rows, int cols, int cell, int startX, int startY) {
            Rectangle clip = g2.getClipBounds();

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int x = startX + c * cell;
                    int y = startY + r * cell;

                    if (x + cell < 0 || y + cell < 0 || x > getWidth() || y > getHeight()) {
                        continue;
                    }

                    if (map[r][c] == 0) {
                        g2.setColor(new Color(221, 236, 244, 105));
                        g2.fillRoundRect(x + 2, y + 2, cell - 4, cell - 4, 14, 14);

                        g2.setColor(new Color(255, 255, 255, 35));
                        g2.fillOval(
                                x + cell / 4,
                                y + cell / 4,
                                Math.max(3, cell / 7),
                                Math.max(3, cell / 7)
                        );
                    }
                }
            }
        }

        /**
         * Kirajzolja az út részleteit: aszfalt, sáv jelölések és speciális felületek.
         *
         * @param g2     a grafikus kontextus
         * @param map    a térkép mátrix
         * @param rows   a sorok száma
         * @param cols   az oszlopok száma
         * @param cell   a cella mérete pixelben
         * @param startX a kirajzolás kezdő X koordinátája
         * @param startY a kirajzolás kezdő Y koordinátája
         */
        private void drawRoadDetails(Graphics2D g2, int[][] map, int rows, int cols, int cell, int startX, int startY) {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (map[r][c] == 0) continue;

                    int x = startX + c * cell;
                    int y = startY + r * cell;

                    if (x + cell < 0 || y + cell < 0 || x > getWidth() || y > getHeight()) {
                        continue;
                    }

                    if (map[r][c] == 4) {
                        drawDepot(g2, x, y, cell);
                        continue;
                    }

                    if (map[r][c] == 5) {
                        drawTunnel(g2, x, y, cell);
                        continue;
                    }

                    if (map[r][c] == 6) {
                        drawBridge(g2, x, y, cell);
                        continue;
                    }

                    g2.setColor(new Color(78, 88, 101));
                    g2.fillRoundRect(x + 3, y + 3, cell - 6, cell - 6, 12, 12);

                    boolean north = r > 0 && map[r - 1][c] != 0;
                    boolean south = r < rows - 1 && map[r + 1][c] != 0;
                    boolean west = c > 0 && map[r][c - 1] != 0;
                    boolean east = c < cols - 1 && map[r][c + 1] != 0;

                    g2.setColor(new Color(70, 80, 92));

                    if (north) g2.fillRect(x + cell / 3, y, cell / 3, cell / 2);
                    if (south) g2.fillRect(x + cell / 3, y + cell / 2, cell / 3, cell / 2);
                    if (west) g2.fillRect(x, y + cell / 3, cell / 2, cell / 3);
                    if (east) g2.fillRect(x + cell / 2, y + cell / 3, cell / 2, cell / 3);

                    g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[]{8f, 8f}, 0f));
                    g2.setColor(new Color(205, 210, 168, 135));

                    if (east || west) {
                        g2.drawLine(x + 8, y + cell / 2, x + cell - 8, y + cell / 2);
                    } else if (north || south) {
                        g2.drawLine(x + cell / 2, y + 8, x + cell / 2, y + cell - 8);
                    }

                    if (map[r][c] == 2) {
                        drawSnow(g2, x, y, cell);
                    } else if (map[r][c] == 3) {
                        drawIce(g2, x, y, cell);
                    } else if (map[r][c] == 8) {
                        drawBrokenIce(g2, x, y, cell);
                    } else if (map[r][c] == 7) {
                        drawGravel(g2, x, y, cell);
                    } else if (map[r][c] == 9) {
                        drawCrashedLane(g2, x, y, cell);
                    } else if (map[r][c] == 10) {
                        drawDeepSnow(g2, x, y, cell);
                    } else if (map[r][c] == 11) {
                        drawSalted(g2, x, y, cell);
                    }
                }
            }
        }

        /**
         * Kirajzol egy sózott útfelületet a megadott cellába.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawSalted(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(230, 230, 255, 170));
            g2.fillRoundRect(x + 7, y + 7, cell - 14, cell - 14, 10, 10);

            g2.setColor(Color.WHITE);
            g2.drawString("S", x + cell / 2 - 4, y + cell / 2 + 5);
        }

        /**
         * Kirajzol egy depó cellát sárga háttérrel és "D" jelöléssel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawDepot(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(218, 223, 88));
            g2.fillRoundRect(x + 4, y + 4, cell - 8, cell - 8, 14, 14);

            g2.setColor(new Color(76, 82, 58));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(x + 5, y + 5, cell - 10, cell - 10, 14, 14);

            g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(11, cell / 4)));
            g2.drawString("D", x + cell / 2 - 5, y + cell / 2 + 5);
        }

        /**
         * Kirajzol egy havas útfelületet fehér háttérrel és hópehely jelöléssel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawSnow(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(245, 251, 255, 220));
            g2.fillRoundRect(x + 7, y + 7, cell - 14, cell - 14, 10, 10);

            g2.setColor(new Color(205, 222, 235));
            g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, cell / 4)));
            g2.drawString("*", x + cell / 2 - 4, y + cell / 2 + 6);
        }

        /**
         * Kirajzol egy jeges útfelületet kék háttérrel és jégrepedés mintával.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawIce(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(125, 204, 232, 185));
            g2.fillRoundRect(x + 7, y + 7, cell - 14, cell - 14, 10, 10);

            g2.setColor(new Color(230, 250, 255, 190));
            g2.setStroke(new BasicStroke(2f));
            g2.drawLine(x + 10, y + cell - 12, x + cell - 10, y + 10);
            g2.drawLine(x + 14, y + 12, x + cell - 14, y + cell - 12);
        }

        /**
         * Kirajzol egy alagút cellát sötét háttérrel és "T" jelöléssel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawTunnel(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(35, 35, 45));
            g2.fillRoundRect(x + 3, y + 3, cell - 6, cell - 6, 12, 12);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(11, cell / 4)));
            g2.drawString("T", x + cell / 2 - 5, y + cell / 2 + 6);
        }

        /**
         * Kirajzol egy híd cellát barna háttérrel és "H" jelöléssel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawBridge(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(135, 95, 55));
            g2.fillRoundRect(x + 3, y + 3, cell - 6, cell - 6, 12, 12);

            g2.setColor(new Color(230, 230, 230));
            g2.setStroke(new BasicStroke(3f));
            g2.drawLine(x + 7, y + 10, x + cell - 7, y + 10);
            g2.drawLine(x + 7, y + cell - 10, x + cell - 7, y + cell - 10);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(11, cell / 4)));
            g2.drawString("H", x + cell / 2 - 5, y + cell / 2 + 6);
        }

        /**
         * Kirajzol egy törött jég felületet repedés mintával és "X" jelöléssel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawBrokenIce(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(105, 185, 215, 190));
            g2.fillRoundRect(x + 7, y + 7, cell - 14, cell - 14, 10, 10);

            g2.setColor(new Color(235, 252, 255, 220));
            g2.setStroke(new BasicStroke(2f));

            g2.drawLine(x + 11, y + 12, x + cell / 2, y + cell / 2);
            g2.drawLine(x + cell / 2, y + cell / 2, x + cell - 12, y + 14);
            g2.drawLine(x + cell / 2, y + cell / 2, x + 14, y + cell - 13);
            g2.drawLine(x + cell / 2, y + cell / 2, x + cell - 13, y + cell - 12);

            g2.setColor(Color.WHITE);
            g2.drawString("X", x + cell / 2 - 4, y + cell / 2 + 5);
        }

        /**
         * Kirajzol egy kavicsos útfelületet barna háttérrel és kavics pöttyökkel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawGravel(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(135, 105, 75, 190));
            g2.fillRoundRect(x + 7, y + 7, cell - 14, cell - 14, 10, 10);

            g2.setColor(new Color(95, 70, 50));
            g2.fillOval(x + 14, y + 15, 5, 5);
            g2.fillOval(x + 26, y + 24, 4, 4);
            g2.fillOval(x + 38, y + 18, 5, 5);
        }

        /**
         * Kirajzolja a forgalmi autókat a térképen különböző színekkel.
         *
         * @param g2     a grafikus kontextus
         * @param cell   a cella mérete pixelben
         * @param startX a térkép kirajzolásának kezdő X koordinátája
         * @param startY a térkép kirajzolásának kezdő Y koordinátája
         */
        private void drawTrafficCars(Graphics2D g2, int cell, int startX, int startY) {
            if (gameController.getTrafficCars() == null) return;

            Color[] colors = {
                    new Color(72, 167, 220),
                    new Color(224, 92, 92),
                    new Color(109, 201, 119),
                    new Color(232, 179, 66),
                    new Color(166, 119, 222),
                    new Color(230, 129, 68)
            };

            for (GameController.TrafficCar car : gameController.getTrafficCars()) {
                int x = startX + car.getCol() * cell;
                int y = startY + car.getRow() * cell;
                int pad = Math.max(7, cell / 6);

                g2.setColor(new Color(37, 42, 55, 110));
                g2.fillOval(x + pad, y + cell - pad, cell - 2 * pad, Math.max(5, cell / 8));

                g2.setColor(colors[car.getColorIndex() % colors.length]);
                g2.fillRoundRect(x + pad, y + cell / 3, cell - 2 * pad, cell / 3, 10, 10);

                g2.setColor(new Color(176, 222, 245));
                g2.fillRoundRect(x + pad + 5, y + cell / 3 + 3, cell / 3, cell / 7, 5, 5);

                g2.setColor(new Color(20, 24, 30));
                g2.fillOval(x + pad + 3, y + cell / 2 + 8, Math.max(5, cell / 8), Math.max(5, cell / 8));
                g2.fillOval(x + cell - pad - 9, y + cell / 2 + 8, Math.max(5, cell / 8), Math.max(5, cell / 8));
            }
        }

        /**
         * Kirajzolja a hókotrót a megadott pozícióba árnyékkal, testtel,
         * kabinnal, kerekekkel, fejjel és iránynyíllal.
         *
         * @param g2   a grafikus kontextus
         * @param x    a hókotró cellájának bal felső X koordinátája
         * @param y    a hókotró cellájának bal felső Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawSnowplow(Graphics2D g2, int x, int y, int cell) {
            int pad = Math.max(5, cell / 8);

            g2.setColor(new Color(30, 35, 45, 120));
            g2.fillOval(x + pad, y + cell - pad, cell - 2 * pad, Math.max(6, cell / 7));

            g2.setColor(new Color(70, 120, 190));
            g2.fillRoundRect(x + pad, y + cell / 4, cell - 2 * pad, cell / 2, 12, 12);

            g2.setColor(new Color(180, 225, 245));
            g2.fillRoundRect(x + cell / 2, y + cell / 3, cell / 4, cell / 5, 6, 6);

            g2.setColor(new Color(25, 25, 25));
            int wheel = Math.max(6, cell / 7);
            g2.fillOval(x + pad + 3, y + cell / 2 + 9, wheel, wheel);
            g2.fillOval(x + cell - pad - wheel - 3, y + cell / 2 + 9, wheel, wheel);

            Head head = null;

            if (gameController.getRole() instanceof CleanerRole) {
                Snowplow snowplow = ((CleanerRole) gameController.getRole()).getSnowplow();

                if (snowplow != null) {
                    head = snowplow.getCurrentHead();
                }
            }
            drawSnowplowHead(g2, x, y, cell, head);
            drawDirectionArrow(g2, x, y, cell);
        }

        /**
         * Kirajzolja a hókotró fejét a típusának megfelelő megjelenéssel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a hókotró cellájának bal felső X koordinátája
         * @param y    a hókotró cellájának bal felső Y koordinátája
         * @param cell a cella mérete pixelben
         * @param head az aktuális fej, lehet {@code null} az alapértelmezett tolólaphoz
         */
        private void drawSnowplowHead(Graphics2D g2, int x, int y, int cell, Head head) {
            int frontX = x + cell - Math.max(8, cell / 6);
            int centerY = y + cell / 2;

            if (head instanceof DragonHead) {
                g2.setColor(new Color(180, 40, 30));
                g2.fillRoundRect(frontX - 3, centerY - 8, cell / 3, 16, 8, 8);

                g2.setColor(new Color(255, 130, 20));
                Polygon flame = new Polygon();
                flame.addPoint(frontX + cell / 3, centerY);
                flame.addPoint(frontX + cell / 2, centerY - 10);
                flame.addPoint(frontX + cell / 2, centerY + 10);
                g2.fillPolygon(flame);

                g2.setColor(Color.YELLOW);
                g2.fillOval(frontX + cell / 3, centerY - 4, 8, 8);
                return;
            }

            if (head instanceof IcebreakerHead) {
                g2.setColor(new Color(160, 170, 180));
                Polygon spike = new Polygon();
                spike.addPoint(frontX - 4, centerY - 13);
                spike.addPoint(frontX + cell / 2, centerY);
                spike.addPoint(frontX - 4, centerY + 13);
                g2.fillPolygon(spike);

                g2.setColor(new Color(80, 90, 100));
                g2.setStroke(new BasicStroke(3f));
                g2.drawLine(frontX, centerY, frontX + cell / 2, centerY);
                return;
            }

            if (head instanceof SaltSpreaderHead) {
                g2.setColor(new Color(110, 110, 120));
                g2.fillRoundRect(frontX - 2, centerY - 7, cell / 3, 14, 8, 8);

                g2.setColor(Color.WHITE);
                for (int i = 0; i < 5; i++) {
                    g2.fillOval(frontX + cell / 3 + i * 5, centerY - 10 + (i % 3) * 7, 4, 4);
                }
                return;
            }

            if (head instanceof GravelSpreaderHead) {
                g2.setColor(new Color(120, 80, 45));
                g2.fillRoundRect(frontX - 2, centerY - 8, cell / 3, 16, 8, 8);

                g2.setColor(new Color(80, 55, 35));
                for (int i = 0; i < 6; i++) {
                    g2.fillOval(frontX + cell / 3 + i * 4, centerY - 9 + (i % 4) * 5, 5, 5);
                }
                return;
            }

            if (head instanceof SweeperHead) {
                g2.setColor(new Color(240, 170, 40));
                g2.fillOval(frontX - 2, centerY - 13, cell / 3, 26);

                g2.setColor(new Color(120, 80, 30));
                g2.setStroke(new BasicStroke(2f));
                for (int i = -10; i <= 10; i += 5) {
                    g2.drawLine(frontX + 2, centerY + i, frontX + cell / 3, centerY + i);
                }
                return;
            }

            if (head instanceof ThrowerHead) {
                g2.setColor(new Color(90, 150, 210));
                g2.fillRoundRect(frontX - 2, centerY - 8, cell / 3, 16, 8, 8);

                g2.setColor(new Color(60, 90, 130));
                g2.setStroke(new BasicStroke(4f));
                g2.drawArc(frontX + cell / 4, centerY - 22, cell / 2, 28, 20, 140);
                return;
            }

            g2.setColor(new Color(200, 200, 210));
            Polygon blade = new Polygon();
            blade.addPoint(frontX - 5, centerY - 14);
            blade.addPoint(frontX + cell / 3, centerY - 8);
            blade.addPoint(frontX + cell / 3, centerY + 8);
            blade.addPoint(frontX - 5, centerY + 14);
            g2.fillPolygon(blade);
        }

        /**
         * Kirajzolja a mozgásirány nyilat a hókotró pozíciójában.
         *
         * @param g2   a grafikus kontextus
         * @param x    a hókotró cellájának bal felső X koordinátája
         * @param y    a hókotró cellájának bal felső Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawDirectionArrow(Graphics2D g2, int x, int y, int cell) {
            if (gameController == null) return;

            int dr = gameController.getLastDirRow();
            int dc = gameController.getLastDirCol();

            int cx = x + cell / 2;
            int cy = y + cell / 2;

            int endX = cx + dc * cell / 3;
            int endY = cy + dr * cell / 3;

            g2.setColor(Color.YELLOW);
            g2.setStroke(new BasicStroke(3f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(cx, cy, endX, endY);

            Polygon arrow = new Polygon();

            if (dc > 0) {
                arrow.addPoint(endX, endY);
                arrow.addPoint(endX - 8, endY - 5);
                arrow.addPoint(endX - 8, endY + 5);
            } else if (dc < 0) {
                arrow.addPoint(endX, endY);
                arrow.addPoint(endX + 8, endY - 5);
                arrow.addPoint(endX + 8, endY + 5);
            } else if (dr > 0) {
                arrow.addPoint(endX, endY);
                arrow.addPoint(endX - 5, endY - 8);
                arrow.addPoint(endX + 5, endY - 8);
            } else {
                arrow.addPoint(endX, endY);
                arrow.addPoint(endX - 5, endY + 8);
                arrow.addPoint(endX + 5, endY + 8);
            }

            g2.fillPolygon(arrow);
        }

        /**
         * Kirajzolja a buszt a megadott pozícióba sárga karosszériával.
         *
         * @param g2   a grafikus kontextus
         * @param x    a busz cellájának bal felső X koordinátája
         * @param y    a busz cellájának bal felső Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawBus(Graphics2D g2, int x, int y, int cell) {
            int pad = Math.max(5, cell / 8);

            g2.setColor(new Color(30, 35, 45, 120));
            g2.fillOval(x + pad, y + cell - pad, cell - 2 * pad, Math.max(6, cell / 7));

            g2.setColor(new Color(245, 184, 55));
            g2.fillRoundRect(x + pad, y + cell / 4, cell - 2 * pad, cell / 2, 12, 12);

            g2.setColor(new Color(180, 225, 245));
            g2.fillRoundRect(x + pad + 5, y + cell / 3, cell - 2 * pad - 10, cell / 7, 5, 5);

            g2.setColor(new Color(25, 25, 25));
            int wheel = Math.max(6, cell / 7);
            g2.fillOval(x + pad + 4, y + cell / 2 + 10, wheel, wheel);
            g2.fillOval(x + cell - pad - wheel - 4, y + cell / 2 + 10, wheel, wheel);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(10, cell / 5)));
            g2.drawString("B", x + cell / 2 - 4, y + cell / 2 + 7);
        }

        /**
         * Kirajzolja a küldetés információs panelt a játéktér bal felső sarkába.
         *
         * @param g2 a grafikus kontextus
         */
        private void drawMissionOverlay(Graphics2D g2) {
            g2.setColor(new Color(0, 0, 0, 90));
            g2.fillRoundRect(18, 18, 250, 55, 16, 16);

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString("Küldetés:", 35, 40);
            g2.drawString("Takaríts minél több útszakaszt", 35, 60);
        }

        /**
         * Kirajzolja a jelmagyarázatot a játéktér aljára.
         *
         * @param g2 a grafikus kontextus
         */
        private void drawLegend(Graphics2D g2) {
            g2.setColor(new Color(255, 255, 255, 215));
            g2.setFont(new Font("SansSerif", Font.BOLD, 13));
            g2.drawString(
                    "Depó: sárga | Hó: fehér | Jég: kék | Autók: forgalom | P: hókotró",
                    25,
                    getHeight() - 18
            );
        }

        /**
         * Kirajzol egy ütközött sávot piros X jelöléssel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawCrashedLane(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(90, 35, 35, 220));
            g2.fillRoundRect(x + 7, y + 7, cell - 14, cell - 14, 10, 10);

            g2.setColor(Color.RED);
            g2.setStroke(new BasicStroke(3f));
            g2.drawLine(x + 10, y + 10, x + cell - 10, y + cell - 10);
            g2.drawLine(x + cell - 10, y + 10, x + 10, y + cell - 10);
        }

        /**
         * Kirajzol egy mély hóval borított útfelületet dupla hópehely jelöléssel.
         *
         * @param g2   a grafikus kontextus
         * @param x    a cella bal felső sarkának X koordinátája
         * @param y    a cella bal felső sarkának Y koordinátája
         * @param cell a cella mérete pixelben
         */
        private void drawDeepSnow(Graphics2D g2, int x, int y, int cell) {
            g2.setColor(new Color(250, 250, 255, 240));
            g2.fillRoundRect(x + 5, y + 5, cell - 10, cell - 10, 12, 12);

            g2.setColor(new Color(190, 210, 230));
            g2.setFont(new Font("SansSerif", Font.BOLD, Math.max(12, cell / 4)));
            g2.drawString("**", x + cell / 2 - 8, y + cell / 2 + 6);
        }

        /**
         * Kirajzolja a minitérképet a játéktér bal alsó sarkába a játékos és célpont pozíciójával.
         *
         * @param g2   a grafikus kontextus
         * @param map  a térkép mátrix
         * @param rows a sorok száma
         * @param cols az oszlopok száma
         */
        private void drawMiniMap(Graphics2D g2, int[][] map, int rows, int cols) {
            int miniW = 210;
            int miniH = 140;
            int x0 = 18;
            int y0 = getHeight() - miniH - 38;

            g2.setColor(new Color(0, 0, 0, 145));
            g2.fillRoundRect(x0 - 8, y0 - 8, miniW + 16, miniH + 16, 16, 16);

            double cellW = miniW / (double) cols;
            double cellH = miniH / (double) rows;

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    int tile = map[r][c];

                    if (tile == 0) {
                        g2.setColor(new Color(210, 225, 235));
                    } else if (tile == 1) {
                        g2.setColor(new Color(80, 90, 100));
                    } else if (tile == 2) {
                        g2.setColor(Color.WHITE);
                    } else if (tile == 3) {
                        g2.setColor(new Color(90, 190, 230));
                    } else if (tile == 4) {
                        g2.setColor(new Color(220, 220, 70));
                    } else if (tile == 5) {
                        g2.setColor(new Color(35, 35, 45));
                    } else if (tile == 6) {
                        g2.setColor(new Color(150, 100, 60));
                    } else {
                        g2.setColor(Color.GRAY);
                    }

                    int x = x0 + (int) Math.round(c * cellW);
                    int y = y0 + (int) Math.round(r * cellH);
                    int w = Math.max(1, (int) Math.ceil(cellW));
                    int h = Math.max(1, (int) Math.ceil(cellH));

                    g2.fillRect(x, y, w, h);
                }
            }

            int px = x0 + (int) Math.round(gameController.getPlayerCol() * cellW);
            int py = y0 + (int) Math.round(gameController.getPlayerRow() * cellH);

            g2.setColor(Color.RED);
            g2.fillOval(px - 3, py - 3, 7, 7);

            if (gameController.getTargetRow() >= 0 && gameController.getTargetCol() >= 0) {
                int tx = x0 + (int) Math.round(gameController.getTargetCol() * cellW);
                int ty = y0 + (int) Math.round(gameController.getTargetRow() * cellH);

                g2.setColor(Color.GREEN);
                g2.fillOval(tx - 3, ty - 3, 7, 7);
            }

            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 11));
            g2.drawString("MINIMAP", x0, y0 - 12);
        }
    }
}
