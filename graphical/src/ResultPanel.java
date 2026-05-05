package src;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {
    private Runnable onBackToMenu;

    public ResultPanel(Game game) {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 34, 42));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Jatek vege!");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(255, 215, 0));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        add(title, gbc);

        StringBuilder sb = new StringBuilder("<html><center>");
        sb.append("<br><b>Eredmenyek:</b><br><br>");

        if (game.getPlayers().isEmpty()) {
            sb.append("Nincsenek jatekosok.<br>");
        } else {
            int rank = 1;
            for (Player p : game.getPlayers()) {
                sb.append(rank).append(". ").append(p.getName()).append(": ")
                  .append(p.getSumPoints()).append(" pont<br>");
                rank++;
            }
        }

        sb.append("<br>Korok: ").append(game.getCurrentRound()).append(" / ").append(game.getMaxRound());
        sb.append("</center></html>");

        JLabel results = new JLabel(sb.toString());
        results.setFont(new Font("SansSerif", Font.PLAIN, 16));
        results.setForeground(new Color(200, 210, 230));
        results.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        add(results, gbc);

        JButton backButton = new JButton("Vissza a menube");
        backButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        backButton.setBackground(new Color(0, 100, 180));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(200, 40));
        backButton.addActionListener(e -> {
            if (onBackToMenu != null) onBackToMenu.run();
        });
        gbc.gridy = 2;
        add(backButton, gbc);
    }

    public void setOnBackToMenu(Runnable r) {
        this.onBackToMenu = r;
    }
}
