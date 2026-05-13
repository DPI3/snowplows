package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;


public class StoreColumnPanel extends JPanel {
        private JPanel itemsContainer;
        private StyledButton buyButton;
        private Font silkscreenTitle;

        public StyledButton getBuyButton(){return buyButton;}
        public JPanel getItemsContainer(){return itemsContainer;}


        public StoreColumnPanel(String title) {
            setOpaque(false);
            setLayout(new BorderLayout(0, 20));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            loadCustomFont();

            // Cím
            JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(silkscreenTitle);
            titleLabel.setForeground(Color.decode("#EAE0D5"));
            add(titleLabel, BorderLayout.NORTH);

            // Elemek tárolója (felülre igazítva)
            itemsContainer = new JPanel();
            itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
            itemsContainer.setOpaque(false);
            add(itemsContainer, BorderLayout.CENTER);

            // Vásárlás gomb alulra
            buyButton = new StyledButton("BUY", 180, 50, Color.decode("#EAE0D5"));
            
            add(buyButton, BorderLayout.SOUTH);
        }

        public void addItemRow(String itemName) {
            itemsContainer.add(new ItemRow(itemName));
            itemsContainer.add(Box.createRigidArea(new Dimension(0, 20))); // Térköz a sorok között
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            // #748CAB áttetszővel
            g2.setColor(new Color(116, 140, 171, 200)); 
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            g2.dispose();
        }

        private void loadCustomFont() {
        try {
            //File fontFile = new File("graphical/Silkscreen-Regular.ttf");
            File fontFile = new File("Silkscreen-Regular.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenTitle = customFont.deriveFont(Font.PLAIN, 28f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus! Alapértelmezett lesz használva.");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenTitle = fallback.deriveFont(28f);
        }
    }
    
}
