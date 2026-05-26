package view;

import controller.AssetManager;
import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Szürke, lekerekített sarkú információs doboz, amely a hókotró aktuális fejét mutatja
 * és egy váltó gombot tartalmaz.
 */
class GrayInfoBox extends JPanel {
    /** Az árnyék mérete pixelben. */
    private final int shadowSize = 4;
    /** Az aktuális fej nevét megjelenítő címke. */
    JLabel currentHeadLabel;
    /** A fej váltását indító gomb. */
    private StyledButton changeBtn;

    /**
     * Beállítja az aktuális fej feliratát.
     *
     * @param head az új fej neve
     */
    public void setCurrentHeadLabel(String head) {
        currentHeadLabel.setText(head);
        repaint();
        revalidate();
    }

    /**
     * Beállítja a váltó gomb eseménykezelőjét, az előző kezelőket eltávolítva.
     *
     * @param listener az új eseménykezelő
     */
    public void setChangeAction(ActionListener listener) {
        if (changeBtn != null) {
            for (ActionListener old : changeBtn.getActionListeners()) {
                changeBtn.removeActionListener(old);
            }
            changeBtn.addActionListener(listener);
        }
    }

    /**
     * Létrehoz egy új GrayInfoBox panelt a fej címkével és a váltó gombbal.
     */
    public GrayInfoBox() {
        setOpaque(false);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(220, 160));
        setMaximumSize(new Dimension(220, 160));
        setBorder(BorderFactory.createEmptyBorder(20, 0, shadowSize, shadowSize));
        setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel headLabel = createShadowedLabel("HEAD:", AssetManager.getInstance().getFont("silkscreenNormal"));
        currentHeadLabel = createShadowedLabel("", AssetManager.getInstance().getFont("silkscreenTitle"));

        changeBtn = new StyledButton("CHANGE", 160, 40, Color.decode("#E2E874"));
        changeBtn.setFont(AssetManager.getInstance().getFont("silkscreenSmall"));

        add(headLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(currentHeadLabel);
        add(Box.createVerticalGlue());
        add(changeBtn);
        add(Box.createRigidArea(new Dimension(0, 10)));
    }

    /**
     * Létrehoz egy árnyékolt szöveges címkét.
     *
     * @param text a címke szövege
     * @param font a használt betűtípus
     * @return az elkészített árnyékolt címke
     */
    private JLabel createShadowedLabel(String text, Font font) {
        JLabel label = new JLabel(text) {
            /**
             * Kirajzolja a címkét árnyékkal.
             *
             * @param g a grafikus kontextus
             */
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
                FontMetrics fm = g2.getFontMetrics(getFont());
                int y = fm.getAscent();

                g2.setColor(new Color(0, 0, 0, 80));
                g2.drawString(getText(), 2, y + 2);

                g2.setColor(getForeground());
                g2.drawString(getText(), 0, y);
                g2.dispose();
            }
        };

        label.setFont(font);
        label.setForeground(AssetManager.getInstance().getColor("TEXT_COLOR"));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    /**
     * Kirajzolja a panel hátterét lekerekített sarkokkal és árnyékkal.
     *
     * @param g a grafikus kontextus
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(AssetManager.getInstance().getColor("DARK_SHADOW"));
        g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);

        g2.setColor(Color.decode("#5A8B85"));
        g2.fillRoundRect(0, 0, getWidth() - shadowSize - 1, getHeight() - shadowSize - 1, 15, 15);

        g2.dispose();
        super.paintComponent(g);
    }
}
