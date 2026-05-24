package view;

import controller.AssetManager;
import java.awt.*;
import javax.swing.*;

/**
 * Egy sor a listában: Rózsaszín címke + Léptető (Spinner)
 */
public class ItemRow extends JPanel {
    private String name;
    private JSpinner spinner;
    private JLabel priceLabel;
    private StyledButton buyButton;

    public ItemRow(String name, int preferredWidth, int preferredHeight, int price, boolean ownBuyButton) {
        this.name = name;
        setOpaque(false);
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        setMaximumSize(new Dimension(400, 40));

        if (price >= 0) {
            JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 3, 0));
            pricePanel.setOpaque(false);

            JLabel priceText = new JLabel(String.valueOf(price));
            priceText.setFont(new Font("SansSerif", Font.BOLD, 14));
            priceText.setForeground(Color.BLACK);

            Image moneyImage = new ImageIcon("money.png").getImage();
            JLabel moneyIcon = new JLabel(new ImageIcon(
                moneyImage.getScaledInstance(28, 22, Image.SCALE_SMOOTH)
            ));

            pricePanel.add(priceText);
            pricePanel.add(moneyIcon);
            add(pricePanel);
        }

        // Kis rózsaszín panel a névnek
        JPanel nameTag = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.decode("#EE8695"));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                g2.setColor(new Color(40, 40, 50, 100)); // Árnyék keret
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
                g2.dispose();
            }
        };
        nameTag.setOpaque(false);
        nameTag.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        nameTag.setLayout(new GridBagLayout());
        
        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(AssetManager.getInstance().getFont("silkscreenSmall"));
        nameLabel.setForeground(Color.decode("#EAE0D5"));
        nameTag.add(nameLabel);

        add(nameTag);

        if (ownBuyButton) {
            buyButton = new StyledButton("BUY", 100, 45, Color.decode("#EAE0D5"));
            add(buyButton);
        } else {
            spinner = new JSpinner(new SpinnerNumberModel(0, 0, 99, 1));
            spinner.setPreferredSize(new Dimension(60, 35));
            spinner.setFont(new Font("SansSerif", Font.BOLD, 16));
            add(spinner);
        }
    }

    public ItemRow(String name, int preferredWidth, int preferredHeight) {
        this(name, preferredWidth, preferredHeight, -1, false);
    }

    public ItemRow(String name){
        this(name, 130, 35, -1, false);
    }

    public int getAmount() {
        if (spinner == null) return 0;
        return (int) spinner.getValue();
    }

    public String getItemName() {
        return name;
    }

    public void ClearSpinner(){
        spinner.setValue(0);
    }

    public void setMaxAmount(int max) {
        spinner.setModel(new SpinnerNumberModel(0, 0, max, 1));
    }

    public StyledButton getBuyButton() {
        return buyButton;
    }

    public void markBought() {
        if (buyButton != null) {
            buyButton.setText("BOUGHT");
            buyButton.setEnabled(false);
        }
    }

    public static ItemRow headRow(String name, int price) {
        return new ItemRow(name, 130, 35, price, true);
    }
}