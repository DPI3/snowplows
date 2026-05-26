package view;

import controller.AssetManager;
import java.awt.*;
import javax.swing.*;

/**
 * A bolt egy oszlopát reprezentáló panel, amely egy címet, elemsorokat
 * és egy vásárlás gombot tartalmaz.
 */
public class StoreColumnPanel extends JPanel {
    /** Az elemsorokat tartalmazó belső panel. */
    private JPanel itemsContainer;
    /** Az oszlop alsó vásárlás gombja. */
    private StyledButton buyButton;
    /** Az oszlop címkéje. */
    private JLabel titleLabel;

    /**
     * Visszaadja az oszlop vásárlás gombját.
     *
     * @return a vásárlás gomb
     */
    public StyledButton getBuyButton(){return buyButton;}

    /**
     * Visszaadja az elemsorokat tartalmazó panelt.
     *
     * @return az elemek tárolója
     */
    public JPanel getItemsContainer(){return itemsContainer;}

    /**
     * Létrehoz egy új bolt oszlopot a megadott címmel, elem tárolóval és vásárlás gombbal.
     *
     * @param title az oszlop címe
     */
    public StoreColumnPanel(String title) {
        setOpaque(false);
        setLayout(new BorderLayout(0, 20));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(AssetManager.getInstance().getFont("silkscreenTitle"));
        titleLabel.setForeground(Color.decode("#EAE0D5"));
        add(titleLabel, BorderLayout.NORTH);

        itemsContainer = new JPanel();
        itemsContainer.setLayout(new BoxLayout(itemsContainer, BoxLayout.Y_AXIS));
        itemsContainer.setOpaque(false);
        add(itemsContainer, BorderLayout.CENTER);

        buyButton = new StyledButton("BUY", 180, 50, Color.decode("#EAE0D5"));

        add(buyButton, BorderLayout.SOUTH);
    }

    /**
     * Hozzáad egy új elemsort a megadott névvel és árral az oszlophoz.
     *
     * @param itemName az elem neve
     * @param price    az elem ára
     */
    public void addItemRow(String itemName, int price) {
        ItemRow row = new ItemRow(itemName, 130, 35, price, false);

        if ("HEAD".equals(getTitle())) {
            row.setMaxAmount(1);
        }

        itemsContainer.add(row);
        itemsContainer.add(Box.createRigidArea(new Dimension(0, 20)));
    }

    /**
     * Hozzáad egy fej típusú elemsort egyedi vásárlás gombbal.
     *
     * @param itemName a fej neve
     * @param price    a fej ára
     * @return az elkészített elemsor
     */
    public ItemRow addHeadItemRow(String itemName, int price) {
        ItemRow row = ItemRow.headRow(itemName, price);
        itemsContainer.add(row);
        return row;
    }

    /**
     * Kirajzolja az oszlop áttetsző, lekerekített sarkú hátterét.
     *
     * @param g a grafikus kontextus
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(116, 140, 171, 200));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
        g2.dispose();
    }

    /**
     * Visszaadja az oszlop címét.
     *
     * @return az oszlop címe
     */
    public String getTitle() {
        return titleLabel.getText();
    }

    /**
     * Elrejti a fő vásárlás gombot az oszlop alján.
     */
    public void hideMainBuyButton() {
        buyButton.setVisible(false);
    }
}
