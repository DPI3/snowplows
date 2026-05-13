package view;

import javax.swing.*;

import controller.StoreController;

import java.awt.*;


public class StorePanel extends JPanel {


    //private static Font silkscreenTitle;
    //private static Font silkscreenHeader;
    //private static Font silkscreenNormal;
    //private static Font silkscreenSmall;

    //private TopPill moneyTopPill;


    public void updateMoney(){
        
    }

    private void buy(StoreColumnPanel itemCol, StoreController controller){
        java.util.List<String> selectedItems = new java.util.ArrayList<>();
                for (Component c : itemCol.getItemsContainer().getComponents()) {
                    if (c instanceof ItemRow row) {
                        int amount = row.getAmount();
                        for(int i=0; i<amount;i++){
                            selectedItems.add(row.getItemName());
                        }
                    }
                }
                if (selectedItems.isEmpty()) return;

                boolean success=true;
                for (String p : selectedItems) {
                    success=controller.buyItem(p);
                    if(!success)    break;
                }


                for (Component c : itemCol.getItemsContainer().getComponents()) {
                    if (c instanceof ItemRow row) {
                        row.ClearSpinner();
                    }
                }
    }

    public StorePanel(StoreController controller, StoreScreen parent) {
        
        setLayout(new GridLayout(1, 3, 30, 0));
        setOpaque(false);

        // 1. Oszlop: MATERIAL
        StoreColumnPanel materialCol = new StoreColumnPanel("MATERIAL");
        materialCol.addItemRow("SALT");
        materialCol.addItemRow("BIOKEROZIN");
        materialCol.addItemRow("GRAVEL");
        materialCol.getBuyButton().addActionListener(e -> {buy(materialCol, controller);});

        add(materialCol);

        // 2. Oszlop: VEHICLE
        StoreColumnPanel vehicleCol = new StoreColumnPanel("VEHICLE");
        vehicleCol.addItemRow("SNOWPLOW");
        vehicleCol.getBuyButton().addActionListener(e -> {buy(vehicleCol, controller);});
        add(vehicleCol);

        // 3. Oszlop: HEAD
        StoreColumnPanel headCol = new StoreColumnPanel("HEAD");
        headCol.addItemRow("DRAGON");
        headCol.addItemRow("SWEEPER");
        headCol.addItemRow("THROWER");
        headCol.addItemRow("ICEBREAKER");
        headCol.addItemRow("SALTSPREAD");
        headCol.addItemRow("GRAVELSPREAD");
        headCol.getBuyButton().addActionListener(e -> {buy(headCol, controller);});
            
        add(headCol);

        
    }

    /*private void loadCustomFont() {
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
    }*/
}