package view;

import controller.AssetManager;
import controller.ScreenController;
import controller.StoreController;
import src.CleanerRole;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * A bolt képernyő, amely megjeleníti a vásárolható elemeket, a játékos pénzét
 * és a hókotró készleteit.
 */
public class StoreScreen extends JFrame{

    /** A bolt panel, amely az oszlopokat tartalmazza. */
    private StorePanel storePanel;
    /** A képernyőváltásokat kezelő vezérlő. */
    private ScreenController screenController;
    /** A játékos pénzét megjelenítő panel. */
    private JPanel playerMoneyLabel;
    /** A pénzösszeget megjelenítő kapszula. */
    private TopPill moneyTopPill;

    /** A só készletet megjelenítő kapszula. */
    private TopPill saltStockPill;
    /** A biokerozin készletet megjelenítő kapszula. */
    private TopPill bioStockPill;
    /** A kavics készletet megjelenítő kapszula. */
    private TopPill gravelStockPill;

    /**
     * Létrehozza a bolt képernyőt a fejléccel, bolt panellel és készlet kijelzőkkel.
     *
     * @param storeController a bolt vezérlő
     * @param role            a játékos tisztító szerepe
     */
    public StoreScreen(StoreController storeController, CleanerRole role) {
        setTitle("Snowplow - Store");
        setSize(1300, 760);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        BackgroundPanel mainPanel =  new BackgroundPanel("factoryite.png");
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Image moneyIcon = null;
        try {
            moneyIcon = ImageIO.read(new File("money.png"));
        } catch (Exception e) {
            System.err.println("Nem található a money.png!");
        }

        playerMoneyLabel = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 0));
        playerMoneyLabel.setOpaque(false);

        moneyTopPill = new TopPill(Integer.toString(2000), 160, moneyIcon);
        TopPill storeTitlePill = new TopPill("STORE", 260, null);

        saltStockPill = new TopPill("SALT: 0%", 130, null);
        bioStockPill = new TopPill("BIO: 0%", 130, null);
        gravelStockPill = new TopPill("GRAVEL: 0%", 150, null);

        StyledButton continueBtn = new StyledButton("CONTINUE", 180, 50, Color.decode("#EAE0D5"));
        continueBtn.addActionListener(e -> screenController.showGame());

        playerMoneyLabel.add(moneyTopPill);
        playerMoneyLabel.add(storeTitlePill);
        playerMoneyLabel.add(saltStockPill);
        playerMoneyLabel.add(bioStockPill);
        playerMoneyLabel.add(gravelStockPill);
        playerMoneyLabel.add(continueBtn);

        mainPanel.add(playerMoneyLabel, BorderLayout.NORTH);
        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               screenController.showGame();
            }
        });

        storePanel= new StorePanel(storeController, this);
        mainPanel.add(storePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        updateStock(role.getSnowplow());
    }

    /**
     * Beállítja a képernyőváltásokat kezelő vezérlőt.
     *
     * @param screenController a képernyővezérlő
     */
    public void setScreenController(ScreenController screenController){
        this.screenController=screenController;
    }

    /**
     * Frissíti a pénz kijelzőt a megadott összeggel.
     *
     * @param amount a megjelenítendő pénzösszeg
     */
    public void updateMoney(int amount){
        moneyTopPill.setText(Integer.toString(amount));
    }

    /**
     * Frissíti a készlet kapszulákat a hókotró aktuális készleteivel.
     *
     * @param snowplow a hókotró, amelyből a készletadatok származnak
     */
    public void updateStock(src.Snowplow snowplow) {
        if (snowplow == null) return;

        if (saltStockPill != null) {
            saltStockPill.setText("SALT: " + snowplow.getSaltStock() + "%");
        }

        if (bioStockPill != null) {
            bioStockPill.setText("BIO: " + snowplow.getBiokeroseneStock() + "%");
        }

        if (gravelStockPill != null) {
            gravelStockPill.setText("GRAVEL: " + snowplow.getGravelStock() + "%");
        }
    }

    /**
     * Felső lekerekített információs kapszula panel, amely szöveget
     * és opcionálisan ikont jelenít meg a bolt képernyőn.
     */
    static class TopPill extends JPanel {
        /** A kapszulában megjelenített szöveg. */
        private String text;
        /** A kapszulában megjelenített opcionális ikon. */
        private Image icon;

        /**
         * Létrehoz egy új kapszula panelt a megadott szöveggel, szélességgel és ikonnal.
         *
         * @param text           a megjelenítendő szöveg
         * @param preferredWidth a kapszula kívánt szélessége
         * @param icon           az opcionális ikon kép, lehet {@code null}
         */
        public TopPill(String text, int preferredWidth, Image icon) {
            this.text = text;
            this.icon = icon;
            setOpaque(false);
            setPreferredSize(new Dimension(preferredWidth, 50));
        }

        /**
         * Beállítja a kapszula szövegét és újrarajzolja a panelt.
         *
         * @param newText az új megjelenítendő szöveg
         */
        public void setText(String newText) {
            this.text = newText;
            repaint();
            revalidate();
        }

        /**
         * Kirajzolja a kapszula hátteret, a szöveget és az opcionális ikont.
         *
         * @param g a grafikus kontextus
         */
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(new Color(116, 140, 171));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

            g2.setFont(AssetManager.getInstance().getFont("silkscreenHeader"));
            g2.setColor(Color.decode("#EAE0D5"));
            FontMetrics fm = g2.getFontMetrics();

            int gap = 10;
            int textWidth = fm.stringWidth(text);
            int iconWidth = (icon != null) ? 40 : 0;
            int iconHeight = (icon != null) ? 30 : 0;

            int totalContentWidth = textWidth + (icon != null ? gap + iconWidth : 0);
            int startX = (getWidth() - totalContentWidth) / 2;
            int centerY = (getHeight() / 2);

            g2.drawString(text, startX, centerY + (fm.getAscent() / 2) - 2);

            if (icon != null) {
                int iconX = startX + textWidth + gap;
                int iconY = centerY - (iconHeight / 2);
                g2.drawImage(icon, iconX, iconY, iconWidth, iconHeight, null);
            }

            g2.dispose();
        }

    }

    /**
     * Az alkalmazás belépési pontja, amely beállítja a megjelenést.
     *
     * @param args a parancssori argumentumok
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
        });
    }
}
