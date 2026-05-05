package src;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameController implements KeyListener {
    private final Game game;
    private final MapPanel mapPanel;
    private JLabel statusLabel;

    private Vehicle selectedVehicle;
    private Lane selectedLane;

    public GameController(Game game, MapPanel mapPanel) {
        this.game = game;
        this.mapPanel = mapPanel;
        mapPanel.setController(this);
    }

    public void setStatusLabel(JLabel label) {
        this.statusLabel = label;
    }

    private void setStatus(String msg) {
        if (statusLabel != null) {
            statusLabel.setText(msg);
        }
    }

    public void onVehicleSelected(Vehicle v) {
        this.selectedVehicle = v;
        setStatus("Kivalasztva: " + v.getId() + " (" + v.getClass().getSimpleName() + ")");
    }

    public void onLaneSelected(Lane lane) {
        this.selectedLane = lane;
        String stateName = lane.getLaneState() != null ? lane.getLaneState().getClass().getSimpleName() : "?";
        setStatus("Sav: " + lane.getName() + " [" + stateName + "]");

        if (selectedVehicle != null) {
            moveVehicleToLane(selectedVehicle, lane);
        }
    }

    private void moveVehicleToLane(Vehicle v, Lane lane) {
        if (!lane.isPassable()) {
            setStatus("A sav nem jarhato! (" + lane.getName() + ")");
            return;
        }
        v.setCurrentLane(lane);
        setStatus(v.getId() + " athelyezve: " + lane.getName());
        game.notifyObservers();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
            case KeyEvent.VK_N:
                if (!game.isOver()) {
                    game.tick();
                    setStatus("Kor: " + game.getCurrentRound());
                } else {
                    setStatus("A jatek veget ert!");
                }
                break;

            case KeyEvent.VK_T:
                if (selectedVehicle instanceof Snowplow) {
                    Snowplow sp = (Snowplow) selectedVehicle;
                    Lane lane = sp.getCurrentLane();
                    if (lane != null) {
                        sp.clean(lane);
                        setStatus("Takaritas: " + lane.getName());
                        game.notifyObservers();
                    }
                }
                break;

            case KeyEvent.VK_H:
                if (selectedVehicle instanceof Snowplow) {
                    showHeadChangeDialog((Snowplow) selectedVehicle);
                }
                break;

            case KeyEvent.VK_B:
                showStoreDialog();
                break;

            case KeyEvent.VK_ESCAPE:
                selectedVehicle = null;
                selectedLane = null;
                mapPanel.setSelectedVehicle(null);
                setStatus("Kivalasztas torolve");
                break;
        }
    }

    private void showHeadChangeDialog(Snowplow sp) {
        String[] options = {"SweeperHead", "ThrowerHead", "IcebreakerHead", "SaltSpreaderHead", "DragonHead", "GravelSpreaderHead"};
        String choice = (String) JOptionPane.showInputDialog(
                mapPanel, "Valassz fejet:", "Kotrofej csere",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == null) return;

        Head newHead = null;
        switch (choice) {
            case "SweeperHead": newHead = new SweeperHead(); break;
            case "ThrowerHead": newHead = new ThrowerHead(); break;
            case "IcebreakerHead": newHead = new IcebreakerHead(); break;
            case "SaltSpreaderHead": newHead = new SaltSpreaderHead(); break;
            case "DragonHead": newHead = new DragonHead(); break;
            case "GravelSpreaderHead": newHead = new GravelSpreaderHead(); break;
        }

        if (newHead != null) {
            sp.changeHead(newHead);
            setStatus("Fej cserelve: " + choice);
            game.notifyObservers();
        }
    }

    private void showStoreDialog() {
        String[] options = {"So (10$)", "Biokerozin (15$)", "Zuzalek (8$)", "SweeperHead (30$)", "ThrowerHead (40$)", "IcebreakerHead (50$)", "Megse"};
        String choice = (String) JOptionPane.showInputDialog(
                mapPanel, "Mit vasarolsz?", "Bolt",
                JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == null || choice.equals("Megse")) return;

        for (Player p : game.getPlayers()) {
            for (int i = 0; i < 1; i++) {
                if (p.getSumPoints() > 0 || true) {
                    setStatus("Vasarlas: " + choice);
                    game.notifyObservers();
                    return;
                }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}
}
