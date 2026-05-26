package view;

import controller.AssetManager;
import controller.ScreenController;
import java.awt.*;
import javax.swing.*;

/**
 * A játék főmenüje, amely Start, Settings és Exit gombokat tartalmaz
 * animált havas háttérrel és áttetsző menüpanellel.
 */
public class SnowplowMenu extends JFrame {

    /**
     * Létrehozza a főmenü ablakot a háttérpanellel, az áttetsző menüvel
     * és a navigációs gombokkal.
     *
     * @param screenController a képernyőváltásokat kezelő vezérlő
     */
    public SnowplowMenu(ScreenController screenController) {
        setTitle("Snowplow");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel backgroundPanel = new BackgroundPanel("pixel_art_large-3.png");
        backgroundPanel.setLayout(new GridBagLayout());

        MenuOverlayPanel menuPanel = new MenuOverlayPanel();

        StyledButton btnStart = new StyledButton("Start", 180, 50, Color.decode("#EAE0D5"));
        btnStart.setFont(AssetManager.getInstance().getFont("silkscreenHeader"));
        StyledButton btnSettings = new StyledButton("Settings", 180, 50,Color.decode("#EAE0D5"));
        btnSettings.setFont(AssetManager.getInstance().getFont("silkscreenHeader"));
        StyledButton btnExit = new StyledButton("Exit", 180, 50, Color.decode("#EAE0D5"));
        btnExit.setFont(AssetManager.getInstance().getFont("silkscreenHeader"));

        btnStart.addActionListener(e -> screenController.showGame());
        btnSettings.addActionListener(e -> screenController.showSettings());
        btnExit.addActionListener(e -> System.exit(0));

        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        menuPanel.add(btnStart);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        menuPanel.add(btnSettings);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        menuPanel.add(btnExit);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        backgroundPanel.add(menuPanel);

        setContentPane(backgroundPanel);
    }

    /**
     * Az alkalmazás belépési pontja, amely beállítja a megjelenést és elindítja a menüt.
     *
     * @param args a parancssori argumentumok
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
        });
    }
}
