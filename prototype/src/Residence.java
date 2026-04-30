package prototype.src;

/**
 * A Residence osztály egy lakóhely típusú csomópontot reprezentál.
 */
public class Residence extends Node {

    public Residence(String id) {
        super(id);
    }

    public Residence() {
        super("default_residence");
    }

    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        if (vehicle != null) {
            vehicle.setCurrentNode(this);
        }
    }
}