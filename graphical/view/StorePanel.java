package view;

import javax.swing.*;

import controller.StoreController;

import java.awt.*;


public class StorePanel extends JPanel {

    //private TopPill moneyTopPill;


    public void updateMoney(){
        
    }

    private void buy(StoreColumnPanel itemCol, StoreController controller){
        if ("HEAD".equals(itemCol.getTitle())) {
            int headAmount = 0;

            for (Component c : itemCol.getItemsContainer().getComponents()) {
                if (c instanceof ItemRow) {
                    headAmount += ((ItemRow) c).getAmount();
                }
            }

            if (headAmount > 1) {
                JOptionPane.showMessageDialog(this, "Egyszerre csak 1 fejet vehetsz.");
                return;
            }
        }

        java.util.List<String> selectedItems = new java.util.ArrayList<>();
                for (Component c : itemCol.getItemsContainer().getComponents()) {
                    if (c instanceof ItemRow) {
                        int amount = ((ItemRow) c).getAmount();
                        for(int i=0; i<amount;i++){
                            selectedItems.add(((ItemRow) c).getItemName());
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
                    if (c instanceof ItemRow) {
                        ((ItemRow) c).ClearSpinner();
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
}