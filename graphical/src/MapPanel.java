package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class MapPanel extends JPanel implements ModelObserver {
    private final Game game;
    private final Map<Node, Point> nodePositions = new HashMap<>();
    private Lane selectedLane;
    private Vehicle selectedVehicle;
    private GameController controller;

    public MapPanel(Game game) {
        this.game = game;
        game.addObserver(this);
        setBackground(new Color(200, 220, 240));
        setPreferredSize(new Dimension(800, 600));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setNodePosition(Node node, int x, int y) {
        nodePositions.put(node, new Point(x, y));
    }

    public Lane getSelectedLane() { return selectedLane; }
    public Vehicle getSelectedVehicle() { return selectedVehicle; }

    public void setSelectedVehicle(Vehicle v) {
        this.selectedVehicle = v;
        repaint();
    }

    private void handleClick(int mx, int my) {
        selectedVehicle = null;
        selectedLane = null;

        for (Vehicle v : game.getVehicles()) {
            Point p = getVehiclePosition(v);
            if (p != null && Math.abs(p.x - mx) < 15 && Math.abs(p.y - my) < 15) {
                selectedVehicle = v;
                if (controller != null) controller.onVehicleSelected(v);
                repaint();
                return;
            }
        }

        for (Road road : game.getRoadNetwork().getRoads()) {
            for (Lane lane : road.getLanes()) {
                if (isClickOnLane(lane, mx, my)) {
                    selectedLane = lane;
                    if (controller != null) controller.onLaneSelected(lane);
                    repaint();
                    return;
                }
            }
        }
        repaint();
    }

    private boolean isClickOnLane(Lane lane, int mx, int my) {
        Point src = nodePositions.get(lane.getSource());
        Point dst = nodePositions.get(lane.getDestination());
        if (src == null || dst == null) return false;

        double dx = dst.x - src.x;
        double dy = dst.y - src.y;
        double len = Math.sqrt(dx * dx + dy * dy);
        if (len < 1) return false;

        double t = ((mx - src.x) * dx + (my - src.y) * dy) / (len * len);
        t = Math.max(0, Math.min(1, t));

        double closestX = src.x + t * dx;
        double closestY = src.y + t * dy;

        double dist = Math.sqrt((mx - closestX) * (mx - closestX) + (my - closestY) * (my - closestY));
        return dist < 10;
    }

    private Point getVehiclePosition(Vehicle v) {
        Lane lane = v.getCurrentLane();
        if (lane == null) return null;

        Point src = nodePositions.get(lane.getSource());
        Point dst = nodePositions.get(lane.getDestination());
        if (src == null || dst == null) return null;

        double ratio = v.getSpeed() > 0 ? 0.5 : 0.5;
        int x = (int)(src.x + (dst.x - src.x) * ratio);
        int y = (int)(src.y + (dst.y - src.y) * ratio);
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawRoads(g2);
        drawNodes(g2);
        drawVehicles(g2);
        drawHUD(g2);
    }

    private void drawRoads(Graphics2D g2) {
        for (Road road : game.getRoadNetwork().getRoads()) {
            for (int i = 0; i < road.getLanes().size(); i++) {
                Lane lane = road.getLanes().get(i);
                Point src = nodePositions.get(lane.getSource());
                Point dst = nodePositions.get(lane.getDestination());
                if (src == null || dst == null) continue;

                int offset = (i - road.getLanes().size() / 2) * 6;
                double dx = dst.x - src.x;
                double dy = dst.y - src.y;
                double len = Math.sqrt(dx * dx + dy * dy);
                int ox = (len > 0) ? (int)(-dy / len * offset) : 0;
                int oy = (len > 0) ? (int)(dx / len * offset) : 0;

                g2.setStroke(new BasicStroke(4));
                g2.setColor(getLaneColor(lane));
                g2.drawLine(src.x + ox, src.y + oy, dst.x + ox, dst.y + oy);

                if (lane == selectedLane) {
                    g2.setStroke(new BasicStroke(6));
                    g2.setColor(new Color(255, 215, 0, 120));
                    g2.drawLine(src.x + ox, src.y + oy, dst.x + ox, dst.y + oy);
                }

                if (lane.hasAccident()) {
                    int midX = (src.x + dst.x) / 2 + ox;
                    int midY = (src.y + dst.y) / 2 + oy;
                    g2.setColor(Color.RED);
                    g2.setFont(new Font("SansSerif", Font.BOLD, 14));
                    g2.drawString("!", midX - 3, midY + 5);
                }

                if (road instanceof Tunnel) {
                    g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10, new float[]{5, 5}, 0));
                    g2.setColor(new Color(100, 100, 100));
                    g2.drawLine(src.x + ox, src.y + oy, dst.x + ox, dst.y + oy);
                }
            }
        }
    }

    private Color getLaneColor(Lane lane) {
        LaneState state = lane.getLaneState();
        if (state instanceof Clear) return new Color(80, 80, 80);
        if (state instanceof ThinSnow) return new Color(180, 200, 220);
        if (state instanceof DeepSnow) return new Color(240, 240, 255);
        if (state instanceof IceSheet) return new Color(100, 180, 255);
        if (state instanceof BrokenIce) return new Color(150, 200, 230);
        if (state instanceof Gravel) return new Color(160, 140, 100);
        return Color.GRAY;
    }

    private void drawNodes(Graphics2D g2) {
        for (Map.Entry<Node, Point> entry : nodePositions.entrySet()) {
            Node node = entry.getKey();
            Point p = entry.getValue();
            int r = 12;

            if (node instanceof Terminal) {
                g2.setColor(new Color(0, 120, 200));
                g2.fillRect(p.x - r, p.y - r, r * 2, r * 2);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                g2.drawString("T", p.x - 4, p.y + 4);
            } else if (node instanceof Residence) {
                g2.setColor(new Color(0, 160, 0));
                g2.fillOval(p.x - r, p.y - r, r * 2, r * 2);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                g2.drawString("R", p.x - 4, p.y + 4);
            } else if (node instanceof Workplace) {
                g2.setColor(new Color(180, 100, 0));
                g2.fillOval(p.x - r, p.y - r, r * 2, r * 2);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                g2.drawString("W", p.x - 4, p.y + 4);
            } else {
                g2.setColor(new Color(60, 60, 60));
                g2.fillOval(p.x - 8, p.y - 8, 16, 16);
            }

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 9));
            g2.drawString(node.getId(), p.x - 10, p.y - r - 3);
        }
    }

    private void drawVehicles(Graphics2D g2) {
        for (Vehicle v : game.getVehicles()) {
            Point p = getVehiclePosition(v);
            if (p == null) continue;

            Color color;
            String label;
            if (v instanceof Snowplow) {
                color = new Color(255, 165, 0);
                label = "SP";
            } else if (v instanceof Bus) {
                color = new Color(200, 50, 50);
                label = "BU";
            } else {
                color = new Color(100, 100, 200);
                label = "CA";
            }

            if (v == selectedVehicle) {
                g2.setColor(Color.YELLOW);
                g2.fillOval(p.x - 14, p.y - 14, 28, 28);
            }

            g2.setColor(color);
            g2.fillRect(p.x - 10, p.y - 8, 20, 16);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 9));
            g2.drawString(label, p.x - 7, p.y + 4);

            g2.setColor(Color.BLACK);
            g2.setFont(new Font("SansSerif", Font.PLAIN, 8));
            g2.drawString(v.getId(), p.x - 10, p.y + 20);
        }
    }

    private void drawHUD(Graphics2D g2) {
        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRoundRect(5, 5, 200, 25, 8, 8);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("SansSerif", Font.BOLD, 12));
        g2.drawString("Kor: " + game.getCurrentRound() + " / " + game.getMaxRound(), 12, 22);
    }

    @Override
    public void onModelChanged() {
        repaint();
    }
}
