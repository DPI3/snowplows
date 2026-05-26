package view;

import javax.swing.*;

import controller.AssetManager;

import java.awt.*;

/**
 * A felső sávban megjelenő, lekerekített rózsaszín kapszula alakú panel,
 * amely szöveget és opcionálisan ikont jelenít meg.
 */
class TopPill extends JPanel {
    /** A kapszulában megjelenített szöveg. */
    private String text;
    /** A kapszulában megjelenített opcionális ikon. */
    private Image icon;

    /**
     * Beállítja a kapszula szövegét és újrarajzolja a panelt.
     *
     * @param newText az új megjelenítendő szöveg
     */
    public void setText(String newText) {
        this.text = newText;
        repaint();
        revalidate();
    }

    /**
     * Létrehoz egy új kapszula panelt a megadott szöveggel, szélességgel és ikonnal.
     *
     * @param text  a megjelenítendő szöveg
     * @param width a kapszula szélessége pixelben
     * @param icon  az opcionális ikon kép, lehet {@code null}
     */
    public TopPill(String text, int width, Image icon) {
        this.text = text;
        this.icon = icon;
        setPreferredSize(new Dimension(width, 50));
        setOpaque(false);
    }

    /**
     * Kirajzolja a kapszula hátteret, az árnyékolt szöveget és az opcionális ikont.
     *
     * @param g a grafikus kontextus
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(AssetManager.getInstance().getColor("PINK_COLOR"));
        g2.fillRoundRect(0, -20, getWidth(), getHeight() + 20, 30, 30);

        g2.setColor(new Color(0, 0, 0, 50));
        g2.setStroke(new BasicStroke(2f));
        g2.drawRoundRect(0, -20, getWidth() - 1, getHeight() + 19, 30, 30);

        g2.setFont(AssetManager.getInstance().getFont("silkscreenTitle"));
        FontMetrics fm = g2.getFontMetrics();

        int gap = 10;
        int textWidth = fm.stringWidth(text);
        int iconWidth = (icon != null) ? 40 : 0;
        int iconHeight = (icon != null) ? 30 : 0;

        int totalContentWidth = textWidth + (icon != null ? gap + iconWidth : 0);
        int startX = (getWidth() - totalContentWidth) / 2;
        int centerY = (getHeight() / 2);

        g2.setColor(new Color(0, 0, 0, 60));
        g2.drawString(text, startX + 2, centerY + (fm.getAscent() / 2) - 2 + 2);

        g2.setColor(AssetManager.getInstance().getColor("TEXT_COLOR"));
        g2.drawString(text, startX, centerY + (fm.getAscent() / 2) - 2);

        if (icon != null) {
            int iconX = startX + textWidth + gap;
            int iconY = centerY - (iconHeight / 2);
            g2.drawImage(icon, iconX, iconY, iconWidth, iconHeight, null);
        }

        g2.dispose();
    }

}
