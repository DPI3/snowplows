package view;

import javax.swing.*;
import java.awt.*;
 /**
     * Félig áttetsző, lekerekített sarkú panel (#748CAB színnel).
     */
    public class MenuOverlayPanel extends JPanel {
        public MenuOverlayPanel() {
            setOpaque(false); // Fontos, hogy a Swing ne rajzolja ki a szögletes hátteret
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Elemek függőleges elrendezése
            setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60)); // Belső margók
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            
            // #748CAB (Kékes-szürke) RGB értéke: 116, 140, 171. Hozzáadunk egy kis átlátszóságot (200).
            g2.setColor(new Color(116, 140, 171, 200)); 
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Lekerekítés
            
            g2.dispose();
            super.paintComponent(g); // A gombok kirajzolása ezen a panelen
        }
    }