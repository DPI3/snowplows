package view;

import controller.AssetManager;
import java.awt.*;
import javax.swing.*;


public class StoreColumnPanel extends JPanel {
        private JPanel itemsContainer;
        private StyledButton buyButton;
        private JLabel titleLabel;

        public StyledButton getBuyButton(){return buyButton;}
        public JPanel getItemsContainer(){return itemsContainer;}


        public StoreColumnPanel(String title) {
            setOpaque(false);
            setLayout(new BorderLayout(0, 20));
            setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            // Cím
            titleLabel = new JLabel(title, SwingConstants.CENTER);
            titleLabel.setFont(AssetManager.getInstance().getFont("silkscreenTitle"));
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

        public void addItemRow(String itemName, int price) {
            ItemRow row = new ItemRow(itemName, 130, 35, price, false);

            if ("HEAD".equals(getTitle())) {
                row.setMaxAmount(1);
            }

            itemsContainer.add(row);
            itemsContainer.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        public ItemRow addHeadItemRow(String itemName, int price) {
            ItemRow row = ItemRow.headRow(itemName, price);
            itemsContainer.add(row);
            return row;
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

        public String getTitle() {
            return titleLabel.getText();
        }

        public void hideMainBuyButton() {
            buyButton.setVisible(false);
        }
}
