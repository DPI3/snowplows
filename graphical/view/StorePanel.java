package view;

import javax.swing.*;

import src.Store;
import src.SweeperHead;
import src.ThrowerHead;
import src.CleanerRole;
import src.DragonHead;
import src.GravelSpreaderHead;
import src.IcebreakerHead;
import src.SaltSpreaderHead;
import src.Buyable;

import java.awt.*;
import java.io.File;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StorePanel extends JFrame {

    private static Font silkscreenTitle;
    private static Font silkscreenHeader;
    private static Font silkscreenNormal;
    private static Font silkscreenSmall;

    private TopPill moneyTopPill;
    private Store store;
    private GameScreen screen;


    public void updateMoney(){
        CleanerRole c=(CleanerRole)screen.getRole();
        moneyTopPill.setText(Integer.toString(c.getMoney()));
    }

    public StorePanel(GameScreen screen, Store store) {
        this.screen=screen;
        this.store = store;
        setTitle("Snowplow - Store");
        setSize(1000, 700); // Kicsit szélesebb ablak a három oszlop miatt
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Betűtípusok betöltése és méretezése
        loadCustomFont();

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
        JPanel topBarPanel = new JPanel(new GridBagLayout());
        topBarPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);

        // 1. Bal oldali kapszula: Pénz mennyisége és ikon
        moneyTopPill=new TopPill("0", 200, moneyIcon);
        topBarPanel.add(moneyTopPill, gbc);
        
        // 2. Középső kapszula: STORE felirat (szélesebb)
        gbc.weightx = 2.0; 
        topBarPanel.add(new TopPill("STORE", 400, null), gbc);
        
        // 3. Jobb oldali elem: CONTINUE gomb
        gbc.weightx = 1.0;
        StyledButton continueBtn = new StyledButton("CONTINUE", 180, 50, Color.decode("#EAE0D5"));
        continueBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                screen.moneyChanged();
                setVisible(false);
            }
        });
        topBarPanel.add(continueBtn, gbc);

        mainPanel.add(topBarPanel, BorderLayout.NORTH);

        // --- KÖZÉPSŐ RÉSZ (HÁROM OSZLOP) ---
        JPanel columnsPanel = new JPanel(new GridLayout(1, 3, 30, 0));
        columnsPanel.setOpaque(false);

        // 1. Oszlop: MATERIAL
        StoreColumnPanel materialCol = new StoreColumnPanel("MATERIAL");
        materialCol.addItemRow("SALT");
        materialCol.addItemRow("BIOKERZIN");
        materialCol.addItemRow("GRAVEL");
        materialCol.getBuyButton().addActionListener(e -> {
               java.util.List<String> selectedItems = new java.util.ArrayList<>();
                for (Component c : materialCol.getItemsContainer().getComponents()) {
                    if (c instanceof ItemRow row) {
                        int amount = row.getAmount();
                        for(int i=0; i<amount;i++){
                            selectedItems.add(row.getItemName());
                        }
                        
                    }
                }
                if (selectedItems.isEmpty()) return;

                for (String p : selectedItems) {
                    // anyagot venni
                }

                updateMoney();
            });

        columnsPanel.add(materialCol);

        // 2. Oszlop: VEHICLE
        StoreColumnPanel vehicleCol = new StoreColumnPanel("VEHICLE");
        vehicleCol.addItemRow("SNOWPLOW");
        vehicleCol.getBuyButton().addActionListener(e -> {
               int sumAmount=0;
                for (Component c : vehicleCol.getItemsContainer().getComponents()) {
                    if (c instanceof ItemRow row) {
                        int amount = row.getAmount();
                        for(int i=0; i<amount;i++){
                            sumAmount+=amount;
                        }
                        
                    }
                }
                
                //sumAmount db hókotró vásárlása
                updateMoney();
            });
        columnsPanel.add(vehicleCol);

        // 3. Oszlop: HEAD
        StoreColumnPanel headCol = new StoreColumnPanel("HEAD");
        headCol.addItemRow("DRAGON");
        headCol.addItemRow("SWEEPER");
        headCol.addItemRow("THROWER");
        headCol.addItemRow("ICEBREAKER");
        headCol.addItemRow("SALTSPREAD");
        headCol.addItemRow("GRAVELSPREAD");
        headCol.getBuyButton().addActionListener(e -> {
                java.util.List<Buyable> selectedItems = new java.util.ArrayList<>();
                for (Component c : headCol.getItemsContainer().getComponents()) {
                    if (c instanceof ItemRow row) {
                        int amount = row.getAmount();
                        for(int i=0; i<amount;i++){
                            selectedItems.add(convertToHead(row.getItemName()));
                        }
                        
                    }
                }
                if (selectedItems.isEmpty()) return;
                for (Buyable p : selectedItems) {
                    store.buy((CleanerRole)screen.getRole(), p);
                }
                updateMoney();
            });
            
        columnsPanel.add(headCol);

        mainPanel.add(columnsPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void loadCustomFont() {
        try {
            //File fontFile = new File("graphical/Silkscreen-Regular.ttf");
            File fontFile = new File("Silkscreen-Regular.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);
            
            silkscreenTitle = customFont.deriveFont(Font.PLAIN, 28f);
            silkscreenHeader = customFont.deriveFont(Font.PLAIN, 24f);
            silkscreenNormal = customFont.deriveFont(Font.PLAIN, 20f);
            silkscreenSmall = customFont.deriveFont(Font.PLAIN, 14f);
        } catch (Exception e) {
            System.err.println("Nem található a Silkscreen betűtípus! Alapértelmezett lesz használva.");
            Font fallback = new Font("SansSerif", Font.BOLD, 20);
            silkscreenTitle = fallback.deriveFont(28f);
            silkscreenHeader = fallback.deriveFont(24f);
            silkscreenNormal = fallback.deriveFont(20f);
            silkscreenSmall = fallback.deriveFont(14f);
        }
    }

    // --- EGYEDI KOMPONENSEK ---

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
    }

    public Buyable convertToHead(String name){

        if (name.equals("GRAVELSPREAD")) return new GravelSpreaderHead();
    if (name.equals("SALTSPREAD")) return new SaltSpreaderHead();
    if (name.equals("ICEBREAKER")) return new IcebreakerHead();
    if (name.equals("THROWER")) return new ThrowerHead();
    if (name.equals("SWEEPER")) return new SweeperHead();
    if (name.equals("DRAGON")) return new DragonHead();
        return null;
    }

    public static void main(String[] args) {
        /*try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            StorePanel store = new StorePanel(null);
            store.setVisible(true);
        });*/
    }
}