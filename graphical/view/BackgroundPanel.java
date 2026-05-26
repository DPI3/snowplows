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
 * Animált hóesést és háttérképet kirajzoló panel.
 * Ha nem találja a megadott képet, sötétszürke hátteret használ.
 */
public class BackgroundPanel extends JPanel {
    /** A háttérként megjelenített kép. */
    private Image backgroundImage;
    /** A képernyőn megjelenő hópelyhek listája. */
    private List<Snowflake> snowflakes;
    /** Az animációt vezérlő időzítő. */
    private Timer animationTimer;

    /**
     * Létrehoz egy új háttérpanelt a megadott képpel és hóesés animációval.
     *
     * @param imagePath a háttérkép fájl elérési útja
     */
    public BackgroundPanel(String imagePath) {
        try {
            backgroundImage = ImageIO.read(new File(imagePath));
        } catch (Exception e) {
            System.err.println("Nem található a háttérkép: " + imagePath + " (Alapértelmezett háttér lesz használva)");
        }

        snowflakes = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 1500; i++) {
            snowflakes.add(new Snowflake(rand.nextInt(800), rand.nextInt(500)));
        }

        animationTimer = new Timer(30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int width = getWidth() > 0 ? getWidth() : 800;
                int height = getHeight() > 0 ? getHeight() : 500;

                for (Snowflake flake : snowflakes) {
                    flake.update(width, height);
                }
                repaint();
            }
        });
        animationTimer.start();
    }

    /**
     * Kirajzolja a háttérképet és a hópelyheket.
     *
     * @param g a grafikus kontextus
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            g.setColor(new Color(40, 50, 60));
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(new Color(255, 255, 255, 200));

        for (Snowflake flake : snowflakes) {
            g2.fillRect(flake.x, flake.y, flake.size, flake.size);
        }
        g2.dispose();
    }
}
