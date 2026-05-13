package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class SnowplowMenu extends JFrame {

    // Egyedi betűtípus tárolása
    private static Font silkscreenFont;

    public SnowplowMenu() {
        setTitle("Snowplow");
        setSize(800, 500); // A kép arányaihoz igazodó méret
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ablak középre igazítása

        // 1. Háttér panel (kép betöltése vagy sötétszürke háttér)
        //BackgroundPanel backgroundPanel = new BackgroundPanel("graphical/pixel_art_large-3.png"); // Ide ird a képed nevét!
        
        BackgroundPanel backgroundPanel = new BackgroundPanel("pixel_art_large-3.png"); // Ide ird a képed nevét!
        System.out.println("Itt keresek: " + System.getProperty("user.dir"));
        backgroundPanel.setLayout(new GridBagLayout()); // A középre igazításhoz

        // 2. Középső áttetsző, lekerekített kékes-szürke panel
        MenuOverlayPanel menuPanel = new MenuOverlayPanel();
        
        // 3. Gombok létrehozása
        loadCustomFont();
        StyledButton btnStart = new StyledButton("Start", 180, 50, Color.decode("#EAE0D5"));
        btnStart.setFont(silkscreenFont);
        StyledButton btnLoad = new StyledButton("Load", 180, 50,Color.decode("#EAE0D5"));
        btnLoad.setFont(silkscreenFont);
        StyledButton btnSettings = new StyledButton("Settings", 180, 50,Color.decode("#EAE0D5"));
        btnSettings.setFont(silkscreenFont);
        StyledButton btnExit = new StyledButton("Exit", 180, 50, Color.decode("#EAE0D5"));
        btnExit.setFont(silkscreenFont);
        
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


/**
     * Megpróbálja betölteni a Silkscreen betűtípust a fájlrendszerből.
     */
    private void loadCustomFont() {
        try {
            // Fájlnévnek egyeznie kell a letöltött .ttf fájllal!
            //File fontFile = new File("graphical/Silkscreen-Regular.ttf");
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
     * Egyedi stílusú gomb (#EE8695 háttérrel, #EAE0D5 szöveggel).
     */
   /* static class StyledButton extends JButton {
        public StyledButton(String text) {
            super(text);

            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            
            setForeground(Color.decode("#EAE0D5")); 
            setFont(new Font("Silkscreen", Font.PLAIN, 22));
            
            
            Dimension size = new Dimension(180, 50);
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

            if (getModel().isPressed()) {
                g2.setColor(new Color(218, 114, 129)); 
            } else {
                g2.setColor(Color.decode("#EE8695"));
            }
            g2.fillRoundRect(0, 0, getWidth() - 2, getHeight() - 2, 15, 15);

            g2.dispose();
            
            super.paintComponent(g);
        }
    }*/

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