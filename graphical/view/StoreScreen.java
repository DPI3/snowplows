package view;

import javax.swing.*;

import controller.StoreController;

import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StoreScreen extends JFrame{

    private StorePanel storePanel;
    private JPanel playerMoneyLabel;
    private TopPill moneyTopPill;

    public StoreScreen(StoreController controller){
        setTitle("Snowplow - Store");
        setSize(1000, 700); // Kicsit szélesebb ablak a három oszlop miatt
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Fő háttérpanel
        //BackgroundPanel mainPanel =  new BackgroundPanel("graphical/factoryite.png"); 

        BackgroundPanel mainPanel =  new BackgroundPanel("factoryite.png"); 
        mainPanel.setLayout(new BorderLayout(20, 20));
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
        playerMoneyLabel = new JPanel(new GridBagLayout());
        playerMoneyLabel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);

        // 1. Bal oldali kapszula: Pénz mennyisége és ikon
        moneyTopPill=new TopPill(Integer.toString(controller.getMoney()), 200, moneyIcon);
        playerMoneyLabel.add(moneyTopPill, gbc);
        
        // 2. Középső kapszula: STORE felirat (szélesebb)
        gbc.weightx = 2.0; 
        playerMoneyLabel.add(new TopPill("STORE", 400, null), gbc);
        
        // 3. Jobb oldali elem: CONTINUE gomb
        gbc.weightx = 1.0;
        StyledButton continueBtn = new StyledButton("CONTINUE", 180, 50, Color.decode("#EAE0D5"));
        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //screen.moneyChanged();
                setVisible(false);
            }
        });

        playerMoneyLabel.add(continueBtn, gbc);
        mainPanel.add(playerMoneyLabel, BorderLayout.NORTH);

        storePanel= new StorePanel(controller, this);
        mainPanel.add(storePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    public void refreshStore(){
        
    }

    public void updateMoney(int amount){
        moneyTopPill.setText(Integer.toString(amount));
    }

     /**
     * Felső lekerekített információs panelek (pl. STORE, Pénz)
     */
    static class TopPill extends JPanel {
        private String text;
        private Image icon;
        private Font silkscreenHeader;

        public TopPill(String text, int preferredWidth, Image icon) {
            this.text = text;
            this.icon = icon;
            setOpaque(false);
            setPreferredSize(new Dimension(preferredWidth, 50));
             loadCustomFont();
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

            g2.setFont(silkscreenHeader);
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
        private void loadCustomFont() {
        try {
            //File fontFile = new File("graphical/Silkscreen-Regular.ttf");
            File fontFile = new File("Silkscreen-Regular.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenHeader = customFont.deriveFont(Font.PLAIN, 24f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus! Alapértelmezett lesz használva.");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenHeader = fallback.deriveFont(24f);
        }
    }
}
 public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }


        SwingUtilities.invokeLater(() -> {
            StoreController sc= new StoreController();
            StoreScreen store = new StoreScreen(sc);
            sc.setStoreScreen(store);
            store.setVisible(true);
        });
    }
}
