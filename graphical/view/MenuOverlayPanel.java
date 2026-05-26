package view;

import javax.swing.*;
import java.awt.*;

/**
 * Félig áttetsző, lekerekített sarkú menü panel, amely kékes-szürke háttérrel
 * jeleníti meg a menüelemeket függőleges elrendezésben.
 */
public class MenuOverlayPanel extends JPanel {

    /**
     * Létrehoz egy új áttetsző menü panelt függőleges elrendezéssel és belső margókkal.
     */
    public MenuOverlayPanel() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
    }

    /**
     * Kirajzolja a félig áttetsző, lekerekített sarkú hátteret.
     *
     * @param g a grafikus kontextus
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(116, 140, 171, 200));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        g2.dispose();
        super.paintComponent(g);
    }
}
