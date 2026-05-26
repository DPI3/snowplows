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

public class StoreScreen extends JFrame{

    private StorePanel storePanel;
    private ScreenController screenController;
    private JPanel playerMoneyLabel;
    private TopPill moneyTopPill;

    private TopPill saltStockPill;
    private TopPill bioStockPill;
    private TopPill gravelStockPill;

    public StoreScreen(StoreController storeController, CleanerRole role) {
        setTitle("Snowplow - Store");
        setSize(1300, 760); // Kicsit szélesebb ablak a három oszlop miatt
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fő háttérpanel
        //BackgroundPanel mainPanel =  new BackgroundPanel("graphical/factoryite.png"); 

        BackgroundPanel mainPanel =  new BackgroundPanel("factoryite.png"); 
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Pénz ikon betöltése előre, hogy használhassuk a fejlécben
        Image moneyIcon = null;
        try {
            //moneyIcon = ImageIO.read(new File("graphical/money.png")); 
            moneyIcon = ImageIO.read(new File("money.png")); 
        } catch (Exception e) {
            System.err.println("Nem található a money.png!");
        }

        // --- FELSŐ SÁV (HEADER) ---
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

    public void setScreenController(ScreenController screenController){
        this.screenController=screenController;
    }

    public void updateMoney(int amount){
        moneyTopPill.setText(Integer.toString(amount));
    }

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
     * Felső lekerekített információs panelek (pl. STORE, Pénz)
     */
    static class TopPill extends JPanel {
        private String text;
        private Image icon;

        public TopPill(String text, int preferredWidth, Image icon) {
            this.text = text;
            this.icon = icon;
            setOpaque(false);
            setPreferredSize(new Dimension(preferredWidth, 50));
        }

         public void setText(String newText) {
            this.text = newText;
            repaint();
            revalidate();
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Kapszula háttere (#748CAB)
            g2.setColor(new Color(116, 140, 171));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);

            g2.setFont(AssetManager.getInstance().getFont("silkscreenHeader"));
            g2.setColor(Color.decode("#EAE0D5"));
            FontMetrics fm = g2.getFontMetrics();

            int gap = 10; // Távolság a szöveg és a kép között
            int textWidth = fm.stringWidth(text);
            int iconWidth = (icon != null) ? 40 : 0; 
            int iconHeight = (icon != null) ? 30 : 0; 

            // Teljes szélesség kiszámítása a középre igazításhoz
            int totalContentWidth = textWidth + (icon != null ? gap + iconWidth : 0);
            int startX = (getWidth() - totalContentWidth) / 2;
            int centerY = (getHeight() / 2);

            // Szöveg kirajzolása
            g2.drawString(text, startX, centerY + (fm.getAscent() / 2) - 2);

            // Kép kirajzolása a szöveg után
            if (icon != null) {
                int iconX = startX + textWidth + gap;
                int iconY = centerY - (iconHeight / 2);
                g2.drawImage(icon, iconX, iconY, iconWidth, iconHeight, null);
            }

            g2.dispose();
        }
        
}
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
            /*StoreController sc= new StoreController();
            StoreScreen store = new StoreScreen(sc);
            sc.setStoreScreen(store);
            store.setVisible(true);*/
        });
    }
}
