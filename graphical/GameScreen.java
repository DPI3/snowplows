import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.imageio.ImageIO;

public class GameScreen extends JFrame {

    private static Font silkscreenTitle;
    private static Font silkscreenNormal;
    private static Font silkscreenSmall;

    // Közös színek a dizájnhoz
    private final Color TEXT_COLOR = Color.decode("#E2E874"); // Sárgás-zöldes pixel szöveg
    private final Color PINK_COLOR = Color.decode("#EE8695");
    private final Color DARK_SHADOW = new Color(25, 25, 30);

    public GameScreen() {
        setTitle("Snowplow - Game Screen");
        setSize(1000, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        loadCustomFont();

        // Fő háttér panel, ami megrajzolja a sötét bal oldalt és a menta zöld jobb oldalt
        MainBgPanel mainBg = new MainBgPanel();
        mainBg.setLayout(new BorderLayout());

        // Pénz ikon betöltése
        Image moneyIcon = null;
        try {
            // Cseréld ki a saját elérési útvonaladra, ha szükséges!
            moneyIcon = ImageIO.read(new File("money.png")); 
        } catch (Exception e) {
            System.err.println("Nem található a money.png!");
        }

        // --- FELSŐ SÁV (TOP BAR) ---
        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        topBar.setOpaque(false);
        
        topBar.add(new TopPill("KÖR: 420", 220, null));
        topBar.add(new TopPill("SNOWPLOW MODE", 380, null));
        topBar.add(new TopPill("67", 160, moneyIcon));
        
        mainBg.add(topBar, BorderLayout.NORTH);

        // --- JOBB OLDALI SÁV (RIGHT SIDEBAR) ---
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(260, 0));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 20, 20));

        // 1. STORE gomb
        StyledButton storeBtn = new StyledButton("STORE", 200, 55);
        rightPanel.add(storeBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // 2. Szürke "HEAD" doboz (Most már türkizes-szürke)
        rightPanel.add(new GrayInfoBox());
        rightPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // 3. SETTINGS és MENU gombok
        StyledButton settingsBtn = new StyledButton("SETTINGS", 200, 55);
        rightPanel.add(settingsBtn);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        StyledButton menuBtn = new StyledButton("MENU", 200, 55);
        rightPanel.add(menuBtn);

        mainBg.add(rightPanel, BorderLayout.EAST);

        setContentPane(mainBg);
    }

    private void loadCustomFont() {
        try {
            File fontFile = new File("Silkscreen-Regular.ttf"); 
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenTitle = customFont.deriveFont(Font.PLAIN, 26f);
            silkscreenNormal = customFont.deriveFont(Font.PLAIN, 22f);
            silkscreenSmall = customFont.deriveFont(Font.PLAIN, 16f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus!");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenTitle = fallback.deriveFont(26f);
            silkscreenNormal = fallback.deriveFont(22f);
            silkscreenSmall = fallback.deriveFont(16f);
        }
    }

    // --- EGYEDI KOMPONENSEK ---

    /**
     * Fő háttér, ami kirajzolja a kétszínű felületet.
     */
    class MainBgPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Sötétkék/szürke bal oldal (játéktér)
            g2.setColor(new Color(36, 40, 47));
            g2.fillRect(0, 0, getWidth(), getHeight());

            // ÚJ: Menta zöld/türkiz jobb oldali panel (#8DE4D3)
            g2.setColor(Color.decode("#8DE4D3"));
            int rightWidth = 270;
            g2.fillRoundRect(getWidth() - rightWidth, 0, rightWidth + 50, getHeight(), 30, 30);

            g2.dispose();
        }
    }

    /**
     * A felső sáv "lelógó" rózsaszín kapszulái.
     */
    class TopPill extends JPanel {
        private String text;
        private Image icon;

        public TopPill(String text, int width, Image icon) {
            this.text = text;
            this.icon = icon;
            setPreferredSize(new Dimension(width, 50));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(PINK_COLOR);
            g2.fillRoundRect(0, -20, getWidth(), getHeight() + 20, 30, 30);

            g2.setColor(new Color(0, 0, 0, 50));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(0, -20, getWidth() - 1, getHeight() + 19, 30, 30);

            g2.setFont(silkscreenTitle);
            FontMetrics fm = g2.getFontMetrics();

            int gap = 10; 
            int textWidth = fm.stringWidth(text);
            int iconWidth = (icon != null) ? 40 : 0; 
            int iconHeight = (icon != null) ? 30 : 0; 

            int totalContentWidth = textWidth + (icon != null ? gap + iconWidth : 0);
            int startX = (getWidth() - totalContentWidth) / 2;
            int centerY = (getHeight() / 2);

            // Szöveg árnyék
            g2.setColor(new Color(0, 0, 0, 60));
            g2.drawString(text, startX + 2, centerY + (fm.getAscent() / 2) - 2 + 2);

            // Fő szöveg
            g2.setColor(TEXT_COLOR);
            g2.drawString(text, startX, centerY + (fm.getAscent() / 2) - 2);

            // Ikon
            if (icon != null) {
                int iconX = startX + textWidth + gap;
                int iconY = centerY - (iconHeight / 2);
                g2.drawImage(icon, iconX, iconY, iconWidth, iconHeight, null);
            }

            g2.dispose();
        }
    }

    /**
     * Sötét türkiz-szürke információs doboz a jobb oldali menüben.
     */
    class GrayInfoBox extends JPanel {
        private final int shadowSize = 4;

        public GrayInfoBox() {
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(220, 160));
            setMaximumSize(new Dimension(220, 160));
            setBorder(BorderFactory.createEmptyBorder(20, 0, shadowSize, shadowSize));
            setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel headLabel = createShadowedLabel("HEAD:", silkscreenNormal);
            JLabel sweeperLabel = createShadowedLabel("SWEEPER", silkscreenTitle);
            
            StyledButton changeBtn = new StyledButton("CHANGE", 160, 40);
            changeBtn.setFont(silkscreenSmall);

            add(headLabel);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(sweeperLabel);
            add(Box.createVerticalGlue()); 
            add(changeBtn);
            add(Box.createRigidArea(new Dimension(0, 10)));
        }

        private JLabel createShadowedLabel(String text, Font font) {
            JLabel label = new JLabel(text) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                    FontMetrics fm = g2.getFontMetrics(getFont());
                    int y = fm.getAscent(); 
                    
                    g2.setColor(new Color(0, 0, 0, 80));
                    g2.drawString(getText(), 2, y + 2); 
                    
                    g2.setColor(getForeground());
                    g2.drawString(getText(), 0, y);
                    g2.dispose();
                }
            };
            label.setFont(font);
            label.setForeground(TEXT_COLOR);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fekete 3D árnyék
            g2.setColor(DARK_SHADOW);
            g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);

            // ÚJ: Sötétebb türkizes-szürke panel háttér (#5A8B85)
            g2.setColor(Color.decode("#5A8B85")); 
            g2.fillRoundRect(0, 0, getWidth() - shadowSize - 1, getHeight() - shadowSize - 1, 15, 15);

            g2.dispose();
            super.paintComponent(g);
        }
    }

    /**
     * 3D árnyékos gomb.
     */
    class StyledButton extends JButton {
        private final int shadowSize = 4;

        public StyledButton(String text, int width, int height) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            
            setBorder(BorderFactory.createEmptyBorder(0, 0, shadowSize, shadowSize));
            setForeground(TEXT_COLOR); 
            setFont(silkscreenNormal); 
            
            Dimension size = new Dimension(width, height);
            setPreferredSize(size);
            setMaximumSize(size);
            setMinimumSize(size);
            
            setAlignmentX(Component.CENTER_ALIGNMENT);
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(DARK_SHADOW); 
            g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);

            if (getModel().isPressed()) g2.setColor(new Color(218, 114, 129));
            else g2.setColor(PINK_COLOR);
            g2.fillRoundRect(0, 0, getWidth() - shadowSize - 1, getHeight() - shadowSize - 1, 15, 15);

            g2.setColor(new Color(40, 40, 50, 100));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawRoundRect(0, 0, getWidth() - shadowSize - 2, getHeight() - shadowSize - 2, 15, 15);

            FontMetrics fm = g2.getFontMetrics(getFont());
            int textWidth = fm.stringWidth(getText());
            int x = (getWidth() - textWidth - shadowSize) / 2;
            int y = (getHeight() - fm.getHeight() - shadowSize) / 2 + fm.getAscent();

            g2.setColor(new Color(0, 0, 0, 60));
            g2.drawString(getText(), x + 2, y + 2);

            g2.setColor(getForeground());
            g2.drawString(getText(), x, y);

            g2.dispose();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            GameScreen screen = new GameScreen();
            screen.setVisible(true);
        });
    }
}