package view;

import javax.swing.*;
import java.awt.*;

import controller.ScreenController;

public class SettingsScreen extends JFrame{

    public boolean canAfford(int price){
        return true;
    }

    public SettingsScreen(ScreenController screenController) {
        setTitle("Settings");
        setSize(800, 500); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 
        BackgroundPanel backgroundPanel = new BackgroundPanel("pixel_art_large-3.png"); 
        backgroundPanel.setLayout(new BorderLayout());

        JPanel overlayPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setColor(new Color(0, 0, 0, 130)); 
                
                g2d.fillRect(0, 0, getWidth(), getHeight());
                g2d.dispose();
            }
        };
        overlayPanel.setOpaque(false); 
        overlayPanel.setLayout(new GridLayout(4, 1));
        overlayPanel.setBorder(BorderFactory.createEmptyBorder(50, 40, 0, 40)); 
        ItemRow playerCount= new ItemRow("Játékosok száma: ", 250, 50);
        overlayPanel.add(playerCount);
        ItemRow maxRound = new ItemRow("Játék hossza körökben: ", 250, 50);
        overlayPanel.add(maxRound);
        ItemRow carCount= new ItemRow("Autók száma: ", 250, 50);
        overlayPanel.add(carCount);
        
        StyledButton okButton = new StyledButton("Mentés", 120, 50, Color.decode("#EAE0D5"));
        okButton.addActionListener(
            (e) -> {
                screenController.getGameController().setPlayerCount(playerCount.getAmount());
                screenController.getGameController().setMaxRound(maxRound.getAmount());
                screenController.getGameController().setCarCount(carCount.getAmount());
                screenController.showMenu();
            }
        );

        StyledButton cancelButton = new StyledButton("Mégse", 120, 50, Color.decode("#EAE0D5"));
        cancelButton.addActionListener(e -> screenController.showMenu());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        buttonPanel.add(okButton);
        buttonPanel.add(cancelButton);
        buttonPanel.setOpaque(false);
        
        overlayPanel.add(buttonPanel);
        backgroundPanel.add(overlayPanel, BorderLayout.CENTER);
        setContentPane(backgroundPanel);
    }

}
