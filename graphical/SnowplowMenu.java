import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SnowplowMenu extends JFrame {

    // Egyedi betűtípus tárolása
    private static Font silkscreenFont;

    public SnowplowMenu() {
        setTitle("Snowplow");
        setSize(800, 500); // A kép arányaihoz igazodó méret
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ablak középre igazítása

        // 1. Háttér panel (kép betöltése vagy sötétszürke háttér)
        BackgroundPanel backgroundPanel = new BackgroundPanel("pixel_art_large-3.png"); // Ide ird a képed nevét!
        backgroundPanel.setLayout(new GridBagLayout()); // A középre igazításhoz

        // 2. Középső áttetsző, lekerekített kékes-szürke panel
        MenuOverlayPanel menuPanel = new MenuOverlayPanel();
        
        // 3. Gombok létrehozása
        StyledButton btnStart = new StyledButton("Start");
        StyledButton btnLoad = new StyledButton("Load");
        StyledButton btnSettings = new StyledButton("Settings");
        StyledButton btnExit = new StyledButton("Exit");

        // Gombok funkciói
        btnStart.addActionListener(e -> JOptionPane.showMessageDialog(this, "Start funkció helye"));
        btnLoad.addActionListener(e -> JOptionPane.showMessageDialog(this, "Load funkció helye"));
        btnSettings.addActionListener(e -> JOptionPane.showMessageDialog(this, "Settings funkció helye"));
        btnExit.addActionListener(e -> System.exit(0)); // Kilépés

        // Gombok hozzáadása a menü panelhez térközökkel
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(btnStart);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        menuPanel.add(btnLoad);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        menuPanel.add(btnSettings);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        menuPanel.add(btnExit);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Menü hozzáadása a háttérhez (GridBagLayout középre teszi)
        backgroundPanel.add(menuPanel);

        // Fő panel beállítása
        setContentPane(backgroundPanel);
    }

    // --- EGYEDI KOMPONENSEK ---

/**
     * Megpróbálja betölteni a Silkscreen betűtípust a fájlrendszerből.
     */
    private void loadCustomFont() {
        try {
            // Fájlnévnek egyeznie kell a letöltött .ttf fájllal!
            File fontFile = new File("Silkscreen-Regular.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            // Beállítjuk a kívánt méretet (pl. 24 pixel)
            silkscreenFont = customFont.deriveFont(Font.PLAIN, 24f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen-Regular.ttf. Alapértelmezett betűtípus lesz használva.");
            // Fallback (ha nincs meg a fájl)
            silkscreenFont = new Font("SansSerif", Font.BOLD, 22);
        }
    }

    /**
     * Háttérképet kirajzoló panel. Ha nem találja a képet, sötétszürke lesz.
     */
/**
     * Animált hóesést és háttérképet kirajzoló panel.
     */
    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;
        private List<Snowflake> snowflakes;
        private Timer animationTimer;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = ImageIO.read(new File(imagePath));
            } catch (Exception e) {
                System.err.println("Nem található a háttérkép: " + imagePath + " (Alapértelmezett háttér lesz használva)");
            }

            // Hópelyhek inicializálása
            snowflakes = new ArrayList<>();
            Random rand = new Random();
            for (int i = 0; i < 1500; i++) { // 150 hópehely a képernyőn
                snowflakes.add(new Snowflake(rand.nextInt(800), rand.nextInt(500)));
            }

            // Animációs időzítő (kb. 30 FPS)
            animationTimer = new Timer(30, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Képernyő méreteinek lekérése a határokhoz
                    int width = getWidth() > 0 ? getWidth() : 800;
                    int height = getHeight() > 0 ? getHeight() : 500;

                    // Hópelyhek pozíciójának frissítése
                    for (Snowflake flake : snowflakes) {
                        flake.update(width, height);
                    }
                    // Panel újrarajzolásának kérése
                    repaint();
                }
            });
            animationTimer.start(); // Animáció indítása
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            // 1. Háttérkép kirajzolása (ez van leghátul)
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(40, 50, 60)); // Fallback háttér
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            // 2. Hópelyhek kirajzolása (a háttérkép felett, de a menü alatt)
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(new Color(255, 255, 255, 200)); // Félig áttetsző fehér
            
            for (Snowflake flake : snowflakes) {
                // fillRect-et használunk fillOval helyett a "pixel-art" hatás miatt
                g2.fillRect(flake.x, flake.y, flake.size, flake.size);
            }
            g2.dispose();
        }

        /**
         * Belső osztály egyetlen hópehely adatainak tárolására.
         */
        private class Snowflake {
            int x, y;
            int size;
            int speed;
            int swayOffset; // Oldalirányú kilengés
            int swayCounter;

            public Snowflake(int startX, int startY) {
                Random rand = new Random();
                this.x = startX;
                this.y = startY;
                this.size = rand.nextInt(3) + 2; // 2 és 4 pixel közötti méret
                this.speed = rand.nextInt(3) + 1; // Eltérő esési sebességek
                this.swayCounter = rand.nextInt(100);
            }

            public void update(int screenWidth, int screenHeight) {
                y += speed; // Lefelé esik
                
                // Finom oldalirányú mozgás szinuszgörbe alapján
                swayCounter++;
                swayOffset = (int) (Math.sin(swayCounter * 0.05) * 2);
                x += swayOffset;

                // Ha kiesik alul, újraindul felülről, véletlenszerű X koordinátával
                if (y > screenHeight) {
                    y = -size;
                    Random rand = new Random();
                    x = rand.nextInt(screenWidth);
                    speed = rand.nextInt(3) + 1; // Új sebesség, hogy változatos maradjon
                }
            }
        }
    }

    /**
     * Félig áttetsző, lekerekített sarkú panel (#748CAB színnel).
     */
    static class MenuOverlayPanel extends JPanel {
        public MenuOverlayPanel() {
            setOpaque(false); // Fontos, hogy a Swing ne rajzolja ki a szögletes hátteret
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Elemek függőleges elrendezése
            setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60)); // Belső margók
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // #748CAB (Kékes-szürke) RGB értéke: 116, 140, 171. Hozzáadunk egy kis átlátszóságot (200).
            g2.setColor(new Color(116, 140, 171, 200)); 
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Lekerekítés
            
            g2.dispose();
            super.paintComponent(g); // A gombok kirajzolása ezen a panelen
        }
    }

    /**
     * Egyedi stílusú gomb (#EE8695 háttérrel, #EAE0D5 szöveggel).
     */
    static class StyledButton extends JButton {
        public StyledButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            
            // #EAE0D5 (Bézs/Törtfehér) szövegszín
            setForeground(Color.decode("#EAE0D5")); 
            setFont(new Font("Silkscreen", Font.PLAIN, 22)); // Kicsit nagyobb, sima betűtípus
            setAlignmentX(Component.CENTER_ALIGNMENT); // Középre igazítás a BoxLayout-ban
            
            Dimension size = new Dimension(180, 50);
            setPreferredSize(size);
            setMaximumSize(size);
            setMinimumSize(size);
            
            // Kurzor megváltoztatása, ha ráviszik az egeret
            setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Gomb háttere (#EE8695 Rózsaszínes-piros)
            if (getModel().isPressed()) {
                // Kicsit sötétebb árnyalat lenyomva
                g2.setColor(new Color(218, 114, 129)); 
            } else {
                g2.setColor(Color.decode("#EE8695")); // Normál szín
            }
            g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 15, 15);

            // Gomb vékony sötét kerete az árnyék/térhatás miatt
            //g2.setColor(new Color(40, 40, 50, 150));
            //g2.setStroke(new BasicStroke(1.5f));
            //g2.drawRoundRect(0, 0, getWidth() - 3, getHeight() - 3, 15, 15);

            g2.dispose();
            
            // Szöveg kirajzolásának meghívása
            super.paintComponent(g);
        }
    }

    public static void main(String[] args) {
        // Look and Feel beállítása
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            SnowplowMenu menu = new SnowplowMenu();
            menu.setVisible(true);
        });
    }
}