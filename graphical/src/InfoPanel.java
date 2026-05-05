package src;

import javax.swing.*;
import java.awt.*;

public class InfoPanel extends JPanel implements ModelObserver {
    private final Game game;
    private final MapPanel mapPanel;

    private final JTextArea infoArea;
    private final JLabel titleLabel;

    public InfoPanel(Game game, MapPanel mapPanel) {
        this.game = game;
        this.mapPanel = mapPanel;
        game.addObserver(this);

        setPreferredSize(new Dimension(260, 600));
        setLayout(new BorderLayout(5, 5));
        setBackground(new Color(40, 44, 52));
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        titleLabel = new JLabel("Informacio");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        add(titleLabel, BorderLayout.NORTH);

        infoArea = new JTextArea();
        infoArea.setEditable(false);
        infoArea.setFont(new Font("Monospaced", Font.PLAIN, 11));
        infoArea.setBackground(new Color(50, 54, 62));
        infoArea.setForeground(new Color(200, 210, 220));
        infoArea.setLineWrap(true);
        infoArea.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(infoArea);
        scroll.setBorder(null);
        add(scroll, BorderLayout.CENTER);

        updateInfo();
    }

    private void updateInfo() {
        StringBuilder sb = new StringBuilder();

        sb.append("=== Jatek allapot ===\n");
        sb.append("Kor: ").append(game.getCurrentRound()).append(" / ").append(game.getMaxRound()).append("\n");
        sb.append("Jarmuvek: ").append(game.getVehicles().size()).append("\n");
        sb.append("Jatekosok: ").append(game.getPlayers().size()).append("\n");
        sb.append("Havazas: ").append(game.getWeather() != null ? game.getWeather().getSnowIntensity() : 0).append("\n\n");

        Vehicle selV = mapPanel.getSelectedVehicle();
        if (selV != null) {
            sb.append("=== Kivalasztott jarmu ===\n");
            sb.append("ID: ").append(selV.getId()).append("\n");
            sb.append("Tipus: ");
            if (selV instanceof Snowplow) {
                Snowplow sp = (Snowplow) selV;
                sb.append("Hokotro\n");
                sb.append("Fej: ").append(sp.getCurrentHead() != null ? sp.getCurrentHead().getClass().getSimpleName() : "nincs").append("\n");
                sb.append("So: ").append(sp.getSaltStock()).append("\n");
                sb.append("Biokerozin: ").append(sp.getBiokeroseneStock()).append("\n");
                sb.append("Zuzalek: ").append(sp.getGravelStock()).append("\n");
            } else if (selV instanceof Bus) {
                Bus bus = (Bus) selV;
                sb.append("Busz\n");
                sb.append("Helyzet: ").append(bus.getLocation()).append("\n");
                sb.append("Mozgaskeptelen: ").append(bus.getImmobileTime()).append(" kor\n");
            } else if (selV instanceof Car) {
                Car car = (Car) selV;
                sb.append("Auto\n");
                sb.append("Helyzet: ").append(car.getLocation()).append("\n");
            }
            sb.append("Sebesseg: ").append(selV.getSpeed()).append("\n");
            sb.append("Sav: ").append(selV.getCurrentLane() != null ? selV.getCurrentLane().getName() : "nincs").append("\n\n");
        }

        Lane selL = mapPanel.getSelectedLane();
        if (selL != null) {
            sb.append("=== Kivalasztott sav ===\n");
            sb.append("Nev: ").append(selL.getName()).append("\n");
            sb.append("Allapot: ").append(selL.getLaneState() != null ? selL.getLaneState().getClass().getSimpleName() : "?").append("\n");
            sb.append("Jarhato: ").append(selL.isPassable() ? "igen" : "nem").append("\n");
            sb.append("Ho: ").append(String.format("%.1f", selL.getSnowThickness())).append("\n");
            sb.append("Jeg: ").append(String.format("%.1f", selL.getIceThickness())).append("\n");
            sb.append("Zuzalek: ").append(String.format("%.1f", selL.getGravelThickness())).append("\n");
            sb.append("Baleset: ").append(selL.hasAccident() ? "IGEN" : "nem").append("\n\n");
        }

        sb.append("=== Jatekosok ===\n");
        for (Player p : game.getPlayers()) {
            sb.append(p.getName()).append(": ").append(p.getSumPoints()).append(" pont\n");
        }

        infoArea.setText(sb.toString());
    }

    @Override
    public void onModelChanged() {
        SwingUtilities.invokeLater(this::updateInfo);
    }
}
