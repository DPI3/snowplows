package src;

/**
 * A Residence osztály egy lakóhely típusú csomópontot reprezentál.
 */
public class Residence extends Node {

    /**
     * Új Residence objektum létrehozása egyedi azonosítóval.
     *
     * @param id az egyedi azonosító
     */
    public Residence(String id) {
        super(id);
    }

    /**
     * Új Residence objektum létrehozása alapértelmezett azonosítóval.
     */
    public Residence() {
        super("default_residence");
    }

    /**
     * Kezeli azt az eseményt, amikor egy jármű belép a lakóhelyre.
     *
     * @param vehicle a belépő jármű
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        if (vehicle != null) {
            vehicle.setCurrentNode(this);
        }
    }
}
