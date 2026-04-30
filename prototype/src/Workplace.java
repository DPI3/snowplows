package src;

/**
 * A Workplace osztály egy munkahely csomópontot reprezentál.
 */
public class Workplace extends Node {

    public Workplace() {
        super("default_workplace");
    }

    public Workplace(String id) {
        super(id);
    }

    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        if (vehicle != null) {
            vehicle.setCurrentNode(this);

            // opcionális: cél elérése
            vehicle.setArrived(true);
        }
    }
}