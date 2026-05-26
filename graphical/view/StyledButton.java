package view;

import javax.swing.*;

import controller.AssetManager;

import java.awt.*;

/**
 * Egyedi megjelenésű, 3D árnyékos gomb lekerekített sarkokkal és
 * nyomás effektussal.
 */
class StyledButton extends JButton {
    /** Az árnyék mérete pixelben. */
    private final int shadowSize = 4;

    /**
     * Létrehoz egy új stílusos gombot a megadott szöveggel, mérettel és szövegszínnel.
     *
     * @param text      a gomb felirata
     * @param width     a gomb szélessége pixelben
     * @param height    a gomb magassága pixelben
     * @param textColor a szöveg színe
     */
    public StyledButton(String text, int width, int height, Color textColor) {
        super(text);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);

        setBorder(BorderFactory.createEmptyBorder(0, 0, shadowSize, shadowSize));
        setForeground(textColor);
        setFont(AssetManager.getInstance().getFont("silkscreenNormal"));

        Dimension size = new Dimension(width, height);
        setPreferredSize(size);
        setMaximumSize(size);
        setMinimumSize(size);

        setAlignmentX(Component.CENTER_ALIGNMENT);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Kirajzolja a gombot árnyékkal, lekerekített háttérrel, kerettel és középre igazított szöveggel.
     *
     * @param g a grafikus kontextus
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(AssetManager.getInstance().getColor("DARK_SHADOW"));
        g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);

        if (getModel().isPressed()) g2.setColor(new Color(218, 114, 129));
        else g2.setColor(AssetManager.getInstance().getColor("PINK_COLOR"));
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
