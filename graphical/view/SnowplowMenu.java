package view;

import controller.AssetManager;
import controller.ScreenController;
import java.awt.*;
import javax.swing.*;

public class SnowplowMenu extends JFrame {


    public SnowplowMenu(ScreenController screenController) {
        setTitle("Snowplow");
        setSize(800, 500); // A kép arányaihoz igazodó méret
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Ablak középre igazítása

        // 1. Háttér panel (kép betöltése vagy sötétszürke háttér)
        //BackgroundPanel backgroundPanel = new BackgroundPanel("graphical/pixel_art_large-3.png"); // Ide ird a képed nevét!
        
        BackgroundPanel backgroundPanel = new BackgroundPanel("pixel_art_large-3.png");
        backgroundPanel.setLayout(new GridBagLayout()); // A középre igazításhoz

        // 2. Középső áttetsző, lekerekített kékes-szürke panel
        MenuOverlayPanel menuPanel = new MenuOverlayPanel();
        
        // 3. Gombok létrehozása
        StyledButton btnStart = new StyledButton("Start", 180, 50, Color.decode("#EAE0D5"));
        btnStart.setFont(AssetManager.getInstance().getFont("silkscreenHeader"));
        StyledButton btnSettings = new StyledButton("Settings", 180, 50,Color.decode("#EAE0D5"));
        btnSettings.setFont(AssetManager.getInstance().getFont("silkscreenHeader"));
        StyledButton btnExit = new StyledButton("Exit", 180, 50, Color.decode("#EAE0D5"));
        btnExit.setFont(AssetManager.getInstance().getFont("silkscreenHeader"));
        
        // Gombok funkciói
        btnStart.addActionListener(e -> screenController.showGame());
        btnSettings.addActionListener(e -> screenController.showSettings());
        btnExit.addActionListener(e -> System.exit(0)); // Kilépés

        // Gombok hozzáadása a menü panelhez térközökkel
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(btnStart);
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

    public static void main(String[] args) {
        // Look and Feel beállítása
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            //SnowplowMenu menu = new SnowplowMenu();
            //menu.setVisible(true);
        });
    }
}