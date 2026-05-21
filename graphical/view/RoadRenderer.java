package view;

import src.Lane;
import src.Road;
import src.RoadNetwork;

import java.awt.*;

public class RoadRenderer {

    private Color roadColor;
    private Color laneColor;

    public RoadRenderer() {
        this.roadColor = new Color(80, 80, 80);
        this.laneColor = new Color(200, 200, 200);
    }

    public void renderRoads(Graphics2D g2d, RoadNetwork network) {
        if (network == null) return;
    }

    public void renderLane(Graphics2D g2d, Lane lane) {
        if (lane == null) return;
    }
}
