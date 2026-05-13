package view;

import javax.swing.*;

import java.awt.*;
import java.io.File;

/**
     * Sötét türkiz-szürke információs doboz a jobb oldali menüben.
     */
    class GrayInfoBox extends JPanel {
        private final int shadowSize = 4;
        JLabel currentHeadLabel;

        private static Font silkscreenTitle;
        private static Font silkscreenNormal;
        private static Font silkscreenSmall;

        // Közös színek a dizájnhoz
        private final Color TEXT_COLOR = Color.decode("#E2E874"); // Sárgás-zöldes pixel szöveg
        private final Color DARK_SHADOW = new Color(25, 25, 30);

        public void setCurrentHeadLabel(String head){
            currentHeadLabel.setText(head);
            repaint();
            revalidate();
        }

        public GrayInfoBox() {
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(220, 160));
            setMaximumSize(new Dimension(220, 160));
            setBorder(BorderFactory.createEmptyBorder(20, 0, shadowSize, shadowSize));
            setAlignmentX(Component.CENTER_ALIGNMENT);

            loadCustomFont();
            JLabel headLabel = createShadowedLabel("HEAD:", silkscreenNormal);
            currentHeadLabel = createShadowedLabel("", silkscreenTitle);
            
            StyledButton changeBtn = new StyledButton("CHANGE", 160, 40, Color.decode("#E2E874"));
           
            changeBtn.setFont(silkscreenSmall);

            add(headLabel);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(currentHeadLabel);
            add(Box.createVerticalGlue()); 
            add(changeBtn);
            add(Box.createRigidArea(new Dimension(0, 10)));
        }

        private JLabel createShadowedLabel(String text, Font font) {
            JLabel label = new JLabel(text) {
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
            label.setForeground(TEXT_COLOR);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fekete 3D árnyék
            g2.setColor(DARK_SHADOW);
            g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);

            // ÚJ: Sötétebb türkizes-szürke panel háttér (#5A8B85)
            g2.setColor(Color.decode("#5A8B85")); 
            g2.fillRoundRect(0, 0, getWidth() - shadowSize - 1, getHeight() - shadowSize - 1, 15, 15);

            g2.dispose();
            super.paintComponent(g);
        }

         private void loadCustomFont() {
        try {
            //File fontFile = new File("graphical/Silkscreen-Regular.ttf"); 
            File fontFile = new File("Silkscreen-Regular.ttf"); 
            
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenTitle = customFont.deriveFont(Font.PLAIN, 26f);
            silkscreenNormal = customFont.deriveFont(Font.PLAIN, 22f);
            silkscreenSmall = customFont.deriveFont(Font.PLAIN, 16f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus!");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenTitle = fallback.deriveFont(26f);
            silkscreenNormal = fallback.deriveFont(22f);
            silkscreenSmall = fallback.deriveFont(16f);
        }
    }
    }