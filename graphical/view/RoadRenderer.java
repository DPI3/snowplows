package view;

import src.Lane;
import src.Road;
import src.RoadNetwork;

import java.awt.*;

/**
 * Az úthálózat és sávok grafikus megjelenítéséért felelős osztály.
 */
public class RoadRenderer {

    /** Az utak rajzolásához használt szín. */
    private Color roadColor;
    /** A sávok rajzolásához használt szín. */
    private Color laneColor;

    /**
     * Létrehoz egy új úthálózat renderelőt alapértelmezett színekkel.
     */
    public RoadRenderer() {
        this.roadColor = new Color(80, 80, 80);
        this.laneColor = new Color(200, 200, 200);
    }

    /**
     * Kirajzolja a teljes úthálózatot a megadott grafikus kontextusra.
     *
     * @param g2d     a grafikus kontextus
     * @param network a megjelenítendő úthálózat
     */
    public void renderRoads(Graphics2D g2d, RoadNetwork network) {
        if (network == null) return;
    }

    /**
     * Kirajzol egyetlen sávot a megadott grafikus kontextusra.
     *
     * @param g2d  a grafikus kontextus
     * @param lane a megjelenítendő sáv
     */
    public void renderLane(Graphics2D g2d, Lane lane) {
        if (lane == null) return;
    }
}
