package view;

import controller.AssetManager;
import java.awt.*;
import javax.swing.*;

/**
     * Sötét türkiz-szürke információs doboz a jobb oldali menüben.
     */
    class GrayInfoBox extends JPanel {
        private final int shadowSize = 4;
        JLabel currentHeadLabel;

        private JButton changeButton;

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

            JLabel headLabel = createShadowedLabel("HEAD:", AssetManager.getInstance().getFont("silkscreenNormal"));
            currentHeadLabel = createShadowedLabel("", AssetManager.getInstance().getFont("silkscreenTitle"));
            
            changeButton = new StyledButton("CHANGE", 160, 40, Color.decode("#E2E874"));

            changeButton.setFont(AssetManager.getInstance().getFont("silkscreenSmall"));

            add(headLabel);
            add(Box.createRigidArea(new Dimension(0, 5)));
            add(currentHeadLabel);
            add(Box.createVerticalGlue()); 
            add(changeButton);
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
            label.setForeground(AssetManager.getInstance().getColor("TEXT_COLOR"));
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            return label;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Fekete 3D árnyék
            g2.setColor(AssetManager.getInstance().getColor("DARK_SHADOW"));
            g2.fillRoundRect(shadowSize, shadowSize, getWidth() - shadowSize, getHeight() - shadowSize, 15, 15);

            // ÚJ: Sötétebb türkizes-szürke panel háttér (#5A8B85)
            g2.setColor(Color.decode("#5A8B85")); 
            g2.fillRoundRect(0, 0, getWidth() - shadowSize - 1, getHeight() - shadowSize - 1, 15, 15);

            g2.dispose();
            super.paintComponent(g);
        }

        public void setChangeAction(java.awt.event.ActionListener listener) {
            changeButton.addActionListener(listener);
        }
    }