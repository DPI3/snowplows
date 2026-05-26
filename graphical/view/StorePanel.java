package view;

import controller.StoreController;
import java.awt.*;
import javax.swing.*;

/**
 * A bolt fő panele, amely három oszlopban jeleníti meg az anyagokat,
 * járműveket és fejeket vásárlási lehetőséggel.
 */
public class StorePanel extends JPanel {

    /**
     * Frissíti a pénz kijelzőt.
     */
    public void updateMoney(){

    }

    /**
     * Megvásárolja az adott oszlopban kiválasztott elemeket a vezérlőn keresztül,
     * majd törli a léptetők értékeit.
     *
     * @param itemCol    a bolt oszlop panel, amelyből a kiválasztott elemeket olvassa
     * @param controller a bolt vezérlő, amelyen keresztül a vásárlás történik
     */
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

    /**
     * Létrehozza a bolt panelt három oszloppal: anyagok, járművek és fejek.
     *
     * @param controller a bolt vezérlő
     * @param parent     a szülő bolt képernyő
     */
    public StorePanel(StoreController controller, StoreScreen parent) {

        setLayout(new GridLayout(1, 3, 30, 0));
        setOpaque(false);

        StoreColumnPanel materialCol = new StoreColumnPanel("MATERIAL");
        materialCol.addItemRow("SALT", controller.getItemPrice("SALT"));
        materialCol.addItemRow("BIOKEROZIN", controller.getItemPrice("BIOKEROZIN"));
        materialCol.addItemRow("GRAVEL", controller.getItemPrice("GRAVEL"));
        materialCol.getBuyButton().addActionListener(e -> {buy(materialCol, controller);});

        add(materialCol);

        StoreColumnPanel vehicleCol = new StoreColumnPanel("VEHICLE");
        vehicleCol.addItemRow("SNOWPLOW", controller.getItemPrice("SNOWPLOW"));
        vehicleCol.getBuyButton().addActionListener(e -> {buy(vehicleCol, controller);});
        add(vehicleCol);

        StoreColumnPanel headCol = new StoreColumnPanel("HEAD");
        headCol.hideMainBuyButton();
        addHeadRow(headCol, controller, "DRAGON");
        addHeadRow(headCol, controller, "SWEEPER");
        addHeadRow(headCol, controller, "THROWER");
        addHeadRow(headCol, controller, "ICEBREAKER");
        addHeadRow(headCol, controller, "SALTSPREAD");
        addHeadRow(headCol, controller, "GRAVELSPREAD");

        add(headCol);


    }

    /**
     * Hozzáad egy fej elemsort az adott oszlophoz a vásárlás kezelésével.
     *
     * @param headCol    a fejek oszlopa
     * @param controller a bolt vezérlő
     * @param itemId     a fej azonosítója
     */
    private void addHeadRow(StoreColumnPanel headCol, StoreController controller, String itemId) {
        ItemRow row = headCol.addHeadItemRow(itemId, controller.getItemPrice(itemId));

        row.getBuyButton().addActionListener(e -> {
            if (controller.buyItem(itemId)) {
                row.markBought();
            }
        });
    }
}
