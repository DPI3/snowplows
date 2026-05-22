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

        public ItemRow(String name, int preferredWidth, int preferredHeight) {
            this.name = name;
            setOpaque(false);
            setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
            setMaximumSize(new Dimension(400, 40));
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

            // Léptető (JSpinner)
            spinner = new JSpinner(new SpinnerNumberModel(5, 0, 99, 1));
            spinner.setPreferredSize(new Dimension(60, 35));
            spinner.setFont(new Font("SansSerif", Font.BOLD, 16));

            add(nameTag);
            add(spinner);
        }

        public ItemRow(String name){
            this(name, 130, 35);
        }

        public int getAmount() {
            return (int) spinner.getValue();
        }

        public String getItemName() {
            return name;
        }

        public void ClearSpinner(){
            spinner.setValue(0);
        }
    }