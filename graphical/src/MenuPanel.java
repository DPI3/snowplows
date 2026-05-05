package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MenuPanel extends JPanel {
    private final JButton startButton;
    private final JButton exitButton;
    private final JSpinner roundSpinner;
    private final JSpinner vehicleSpinner;
    private final JSpinner playerSpinner;

    private Runnable onStartGame;

    public MenuPanel() {
        setLayout(new GridBagLayout());
        setBackground(new Color(30, 34, 42));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Zuzmaravaros - Hokotro jatek");
        title.setFont(new Font("SansSerif", Font.BOLD, 24));
        title.setForeground(new Color(200, 220, 255));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        JLabel subtitle = new JLabel("Strategiai szimulacio");
        subtitle.setFont(new Font("SansSerif", Font.ITALIC, 14));
        subtitle.setForeground(new Color(150, 170, 200));
        subtitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1;
        add(subtitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel roundLabel = createLabel("Korok szama:");
        add(roundLabel, gbc);
        gbc.gridx = 1;
        roundSpinner = new JSpinner(new SpinnerNumberModel(10, 3, 100, 1));
        styleSpinner(roundSpinner);
        add(roundSpinner, gbc);

        gbc.gridy = 4;
        gbc.gridx = 0;
        JLabel vehicleLabel = createLabel("Jarmuvek szama:");
        add(vehicleLabel, gbc);
        gbc.gridx = 1;
        vehicleSpinner = new JSpinner(new SpinnerNumberModel(4, 1, 20, 1));
        styleSpinner(vehicleSpinner);
        add(vehicleSpinner, gbc);

        gbc.gridy = 5;
        gbc.gridx = 0;
        JLabel playerLabel = createLabel("Jatekosok szama:");
        add(playerLabel, gbc);
        gbc.gridx = 1;
        playerSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 6, 1));
        styleSpinner(playerSpinner);
        add(playerSpinner, gbc);

        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        startButton = createButton("Jatek inditasa", new Color(0, 140, 60));
        startButton.addActionListener(e -> {
            if (onStartGame != null) onStartGame.run();
        });
        add(startButton, gbc);

        gbc.gridy = 8;
        exitButton = createButton("Kilepes", new Color(160, 40, 40));
        exitButton.addActionListener(e -> System.exit(0));
        add(exitButton, gbc);

        gbc.gridy = 10;
        JLabel controls = new JLabel("<html><center>Vezerles:<br>" +
                "SPACE/N = Kovetkezo kor | T = Takaritas<br>" +
                "H = Fej csere | B = Bolt<br>" +
                "ESC = Kivalasztas torlese<br>" +
                "Kattints jarmure vagy savra!</center></html>");
        controls.setFont(new Font("SansSerif", Font.PLAIN, 11));
        controls.setForeground(new Color(140, 160, 180));
        controls.setHorizontalAlignment(SwingConstants.CENTER);
        add(controls, gbc);
    }

    public void setOnStartGame(Runnable r) {
        this.onStartGame = r;
    }

    public int getRounds() { return (int) roundSpinner.getValue(); }
    public int getVehicleCount() { return (int) vehicleSpinner.getValue(); }
    public int getPlayerCount() { return (int) playerSpinner.getValue(); }

    private JLabel createLabel(String text) {
        JLabel l = new JLabel(text);
        l.setForeground(new Color(180, 190, 210));
        l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        return l;
    }

    private JButton createButton(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("SansSerif", Font.BOLD, 14));
        b.setBackground(bg);
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setPreferredSize(new Dimension(220, 40));
        return b;
    }

    private void styleSpinner(JSpinner spinner) {
        spinner.setFont(new Font("SansSerif", Font.PLAIN, 13));
        spinner.setPreferredSize(new Dimension(80, 28));
    }
}
