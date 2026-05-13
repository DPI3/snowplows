 package view;
import javax.swing.*;
import java.awt.*;
 
   /**
     * Fő háttér, ami kirajzolja a kétszínű felületet.
     */
 class MainBgPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Sötétkék/szürke bal oldal (játéktér)
            g2.setColor(new Color(36, 40, 47));
            g2.fillRect(0, 0, getWidth(), getHeight());

            // ÚJ: Menta zöld/türkiz jobb oldali panel (#8DE4D3)
            g2.setColor(Color.decode("#8DE4D3"));
            int rightWidth = 270;
            g2.fillRoundRect(getWidth() - rightWidth, 0, rightWidth + 50, getHeight(), 30, 30);

            g2.dispose();
        }
    }