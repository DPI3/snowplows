import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class StorePanel extends JFrame {

    private static Font silkscreenTitle;
    private static Font silkscreenHeader;
    private static Font silkscreenNormal;
    private static Font silkscreenSmall;

    public StorePanel() {
        setTitle("Snowplow - Store");
        setSize(1000, 700); // Kicsit szélesebb ablak a három oszlop miatt
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Betűtípusok betöltése és méretezése
        loadCustomFont();

        // Fő háttérpanel
        BackgroundPanel mainPanel =  new BackgroundPanel("graphical/factoryite.png"); 
        mainPanel.setLayout(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Pénz ikon betöltése előre, hogy használhassuk a fejlécben
        Image moneyIcon = null;
        try {
            moneyIcon = ImageIO.read(new File("graphical/money.png")); 
        } catch (Exception e) {
            System.err.println("Nem található a money.png!");
        }

        // --- FELSŐ SÁV (HEADER) ---
        JPanel topBarPanel = new JPanel(new GridBagLayout());
        topBarPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);

        // 1. Bal oldali kapszula: Pénz mennyisége és ikon
        topBarPanel.add(new TopPill("67", 200, moneyIcon), gbc);
        
        // 2. Középső kapszula: STORE felirat (szélesebb)
        gbc.weightx = 2.0; 
        topBarPanel.add(new TopPill("STORE", 400, null), gbc);
        
        // 3. Jobb oldali elem: CONTINUE gomb
        gbc.weightx = 1.0;
        StyledButton continueBtn = new StyledButton("CONTINUE");
        topBarPanel.add(continueBtn, gbc);

        mainPanel.add(topBarPanel, BorderLayout.NORTH);

        // --- KÖZÉPSŐ RÉSZ (HÁROM OSZLOP) ---
        JPanel columnsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        columnsPanel.setOpaque(false);

        // 1. Oszlop: MATERIAL
        StoreColumnPanel materialCol = new StoreColumnPanel("MATERIAL");
        materialCol.addItemRow("SALT");
        materialCol.addItemRow("BIOKERZIN");
        materialCol.addItemRow("stone");
        columnsPanel.add(materialCol);

        // 2. Oszlop: VEHICLE
        StoreColumnPanel vehicleCol = new StoreColumnPanel("VEHICLE");
        vehicleCol.addItemRow("SNOWPLOW");
        columnsPanel.add(vehicleCol);

        // 3. Oszlop: HEAD
        StoreColumnPanel headCol = new StoreColumnPanel("HEAD");
        headCol.addItemRow("DRAGON");
        headCol.addItemRow("SWEEPER");
        headCol.addItemRow("THROWER");
        headCol.addItemRow("ICEBREAKER");
        headCol.addItemRow("SALTSPREAD");
        columnsPanel.add(headCol);

        mainPanel.add(columnsPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void loadCustomFont() {
        try {
            File fontFile = new File("graphical/Silkscreen-Regular.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenTitle = customFont.deriveFont(Font.PLAIN, 28f);
            silkscreenHeader = customFont.deriveFont(Font.PLAIN, 24f);
            silkscreenNormal = customFont.deriveFont(Font.PLAIN, 20f);
            silkscreenSmall = customFont.deriveFont(Font.PLAIN, 14f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus! Alapértelmezett lesz használva.");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenTitle = fallback.deriveFont(28f);
            silkscreenHeader = fallback.deriveFont(24f);
            silkscreenNormal = fallback.deriveFont(20f);
            silkscreenSmall = fallback.deriveFont(14f);
        }
    }

    // --- EGYEDI KOMPONENSEK ---

    /**
     * Felső lekerekített információs panelek (pl. STORE, Pénz)
     */
    static class TopPill extends JPanel {
        private String text;
        private Image icon;

        public TopPill(String text, int preferredWidth, Image icon) {
            this.text = text;
            this.icon = icon;
            setOpaque(false);
            setPreferredSize(new Dimension(preferredWidth, 50));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Kapszula háttere (#748CAB)
            g2.setColor(new Color(116, 140, 171));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

            g2.setFont(silkscreenHeader);
            g2.setColor(Color.decode("#EAE0D5"));
            FontMetrics fm = g2.getFontMetrics();

            int gap = 10; // Távolság a szöveg és a kép között
            int textWidth = fm.stringWidth(text);
            int iconWidth = (icon != null) ? 40 : 0; 
            int iconHeight = (icon != null) ? 30 : 0; 

            // Teljes szélesség kiszámítása a középre igazításhoz
            int totalContentWidth = textWidth + (icon != null ? gap + iconWidth : 0);
            int startX = (getWidth() - totalContentWidth) / 2;
            int centerY = (getHeight() / 2);

            // Szöveg kirajzolása
            g2.drawString(text, startX, centerY + (fm.getAscent() / 2) - 2);

            // Kép kirajzolása a szöveg után
            if (icon != null) {
                int iconX = startX + textWidth + gap;
                int iconY = centerY - (iconHeight / 2);
                g2.drawImage(icon, iconX, iconY, iconWidth, iconHeight, null);
            }

            g2.dispose();
        }
    }

    /**
     * Egy áruházi oszlop panelje (áttetsző kék)
     */
    static class StoreColumnPanel extends JPanel {
        private JPanel itemsContainer;

        public StoreColumnPanel(String title) {
            setOpaque(false);
            setLayout(new BorderLayout(0, 20));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Cím
            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(silkscreenTitle);
            titleLabel.setForeground(Color.decode("#EAE0D5"));
            add(titleLabel, BorderLayout.NORTH);

            // Elemek tárolója (felülre igazítva)
            itemsContainer = new JPanel();
            itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
            itemsContainer.setOpaque(false);
            add(itemsContainer, BorderLayout.CENTER);

            // Vásárlás gomb alulra
            StyledButton buyButton = new StyledButton("BUY");
            add(buyButton, BorderLayout.SOUTH);
        }

        public void addItemRow(String itemName) {
            itemsContainer.add(new ItemRow(itemName));
            itemsContainer.add(Box.createRigidArea(new Dimension(0, 20))); // Térköz a sorok között
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // #748CAB áttetszővel
            g2.setColor(new Color(116, 140, 171, 200)); 
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            g2.dispose();
        }
    }

    /**
     * Egy sor a listában: Rózsaszín címke + Léptető (Spinner)
     */
    static class ItemRow extends JPanel {
        public ItemRow(String name) {
            setOpaque(false);
            setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
            setMaximumSize(new Dimension(400, 40));

            // Kis rózsaszín panel a névnek
            JPanel nameTag = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2.setColor(Color.decode("#EE8695"));
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                    g2.setColor(new Color(40, 40, 50, 100)); // Árnyék keret
                    g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                    g2.dispose();
                }
            };
            nameTag.setOpaque(false);
            nameTag.setPreferredSize(new Dimension(130, 35));
            nameTag.setLayout(new GridBagLayout());
            
            JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(silkscreenSmall);
            nameLabel.setForeground(Color.decode("#EAE0D5"));
            nameTag.add(nameLabel);

            // Léptető (JSpinner)
            JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
            spinner.setPreferredSize(new Dimension(60, 35));
            spinner.setFont(new Font("SansSerif", Font.BOLD, 16));

            add(nameTag);
            add(spinner);
        }
    }

    /**
     * Meglévő nagy rózsaszín gomb osztály
     */
    static class StyledButton extends JButton {
        public StyledButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setForeground(Color.decode("#EAE0D5")); 
            setFont(silkscreenNormal); 
            setPreferredSize(new Dimension(180, 50));
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) {
                g2.setColor(new Color(218, 114, 129)); 
            } else {
                g2.setColor(Color.decode("#EE8695")); 
            }
            g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 15, 15);
            g2.setColor(new Color(40, 40, 50, 150));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * Egyszerű háttér panel
     */
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (Exception e) {}
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(40, 50, 60));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            StorePanel store = new StorePanel();
            store.setVisible(true);
        });
    }
}