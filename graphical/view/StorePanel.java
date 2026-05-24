package view;

import controller.StoreController;
import java.awt.*;
import javax.swing.*;


public class StorePanel extends JPanel {

    //private TopPill moneyTopPill;


    public void updateMoney(){
        
    }

    private void buy(StoreColumnPanel itemCol, StoreController controller){
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
        materialCol.addItemRow("SALT", controller.getItemPrice("SALT"));
        materialCol.addItemRow("BIOKEROZIN", controller.getItemPrice("BIOKEROZIN"));
        materialCol.addItemRow("GRAVEL", controller.getItemPrice("GRAVEL"));
        materialCol.getBuyButton().addActionListener(e -> {buy(materialCol, controller);});

        add(materialCol);

        // 2. Oszlop: VEHICLE
        StoreColumnPanel vehicleCol = new StoreColumnPanel("VEHICLE");
        vehicleCol.addItemRow("SNOWPLOW", controller.getItemPrice("SNOWPLOW"));
        vehicleCol.getBuyButton().addActionListener(e -> {buy(vehicleCol, controller);});
        add(vehicleCol);

        // 3. Oszlop: HEAD
        StoreColumnPanel headCol = new StoreColumnPanel("HEAD");
        headCol.hideMainBuyButton();
        addHeadRow(headCol, controller, "DRAGON");
        addHeadRow(headCol, controller, "SWEEPER");
        addHeadRow(headCol, controller, "THROWER");
        addHeadRow(headCol, controller, "ICEBREAKER");
        addHeadRow(headCol, controller, "SALTSPREAD");
        addHeadRow(headCol, controller, "GRAVELSPREAD");
        headCol.getBuyButton().addActionListener(e -> {buy(headCol, controller);});
            
        add(headCol);

        
    }

    private void addHeadRow(StoreColumnPanel headCol, StoreController controller, String itemId) {
        ItemRow row = headCol.addHeadItemRow(itemId, controller.getItemPrice(itemId));

        row.getBuyButton().addActionListener(e -> {
            if (controller.buyItem(itemId)) {
                row.markBought();
            }
        });
    }
}