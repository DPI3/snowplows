package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

    /**
     * Háttérképet kirajzoló panel. Ha nem találja a képet, sötétszürke lesz.
     */
/**
     * Animált hóesést és háttérképet kirajzoló panel.
     */
    public class BackgroundPanel extends JPanel {
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
    }
