package prototype.src;

/**
 * A Terminal osztály egy végállomás típusú csomópontot reprezentál.
 */
public class Terminal extends Node {

    public Terminal(String id) {
        super(id);
    }

    public Terminal() {
        super("default_terminal");
    }

    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        if (vehicle != null) {
            vehicle.setCurrentNode(this);
        }
    }
}