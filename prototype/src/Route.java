package src;

import java.util.ArrayList;
import java.util.List;

/**
 * Egy kiszámított útvonalat képvisel az úthálózaton keresztül.
 */
public class Route {

    private String name;
    private int reward;
    private List<Lane> lanes = new ArrayList<>();

    public Route() {}

    public Route(String name) {
        this.name = name;
    }

    public Route(String name, int reward) {
        this.name = name;
        this.reward = reward;
    }

    public String getName()   { return name; }
    public int    getReward() { return reward; }

    public Lane getNextLane(Lane curr) {
        int idx = lanes.indexOf(curr);
        if (idx == -1 || idx == lanes.size() - 1) return null;
        return lanes.get(idx + 1);
    }

    public double getLength() {
        double sum = 0;
        for (Lane lane : lanes) sum += lane.getLength();
        return sum;
    }

    public List<Lane> getLanes() { return lanes; }

    public void addLane(Lane lane) { this.lanes.add(lane); }
}
