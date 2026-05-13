package view;

import javax.swing.*;

import java.awt.*;
import java.io.File;

    /**
     * A felső sáv "lelógó" rózsaszín kapszulái.
     */
    class TopPill extends JPanel {
        private String text;
        private Image icon;

        private final Color TEXT_COLOR = Color.decode("#E2E874"); // Sárgás-zöldes pixel szöveg
        private final Color PINK_COLOR = Color.decode("#EE8695");
        private static Font silkscreenTitle;

        public void setText(String newText) {
            this.text = newText;
            repaint();
            revalidate();
        }

        public TopPill(String text, int width, Image icon) {
            this.text = text;
            this.icon = icon;
            loadCustomFont();
            setPreferredSize(new Dimension(width, 50));
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(PINK_COLOR);
            g2.fillRoundRect(0, -20, getWidth(), getHeight() + 20, 30, 30);

            g2.setColor(new Color(0, 0, 0, 50));
            g2.setStroke(new BasicStroke(2f));
            g2.drawRoundRect(0, -20, getWidth() - 1, getHeight() + 19, 30, 30);

            g2.setFont(silkscreenTitle);
            FontMetrics fm = g2.getFontMetrics();

            int gap = 10; 
            int textWidth = fm.stringWidth(text);
            int iconWidth = (icon != null) ? 40 : 0; 
            int iconHeight = (icon != null) ? 30 : 0; 

            int totalContentWidth = textWidth + (icon != null ? gap + iconWidth : 0);
            int startX = (getWidth() - totalContentWidth) / 2;
            int centerY = (getHeight() / 2);

            // Szöveg árnyék
            g2.setColor(new Color(0, 0, 0, 60));
            g2.drawString(text, startX + 2, centerY + (fm.getAscent() / 2) - 2 + 2);

            // Fő szöveg
            g2.setColor(TEXT_COLOR);
            g2.drawString(text, startX, centerY + (fm.getAscent() / 2) - 2);

            // Ikon
            if (icon != null) {
                int iconX = startX + textWidth + gap;
                int iconY = centerY - (iconHeight / 2);
                g2.drawImage(icon, iconX, iconY, iconWidth, iconHeight, null);
            }

            g2.dispose();
        }

        private void loadCustomFont() {
        try {
            //File fontFile = new File("graphical/Silkscreen-Regular.ttf"); 
            File fontFile = new File("Silkscreen-Regular.ttf"); 

            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenTitle = customFont.deriveFont(Font.PLAIN, 26f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus!");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenTitle = fallback.deriveFont(26f);
        }
    }
    }