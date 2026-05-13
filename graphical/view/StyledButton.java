package view;
import javax.swing.*;
import java.awt.*;
import java.io.File;

    /**
     * 3D árnyékos gomb.
     */
    class StyledButton extends JButton {
        private final int shadowSize = 4;

        private static Font silkscreenNormal;

        private final Color PINK_COLOR = Color.decode("#EE8695");
        private final Color DARK_SHADOW = new Color(25, 25, 30);

        public StyledButton(String text, int width, int height, Color textColor) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            
            setBorder(BorderFactory.createEmptyBorder(0, 0, shadowSize, shadowSize));
            loadCustomFont();
            setForeground(textColor); 
            setFont(silkscreenNormal); 
            
            Dimension size = new Dimension(width, height);
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

            g2.setColor(DARK_SHADOW); 
            g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);

            if (getModel().isPressed()) g2.setColor(new Color(218, 114, 129));
            else g2.setColor(PINK_COLOR);
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

        private void loadCustomFont() {
        try {
            //File fontFile = new File("graphical/Silkscreen-Regular.ttf"); 
            File fontFile = new File("Silkscreen-Regular.ttf"); 
            
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenNormal = customFont.deriveFont(Font.PLAIN, 22f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus!");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenNormal = fallback.deriveFont(22f);
        }
    }
    }