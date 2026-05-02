package src;

/**
 * A Workplace osztály egy munkahely csomópontot reprezentál.
 */
public class Workplace extends Node {

    /** 
     * Új Workplace objektum létrehozása alapértelmezett azonosítóval.
     * 
     */
    public Workplace() {
        super("default_workplace");
    }

    /** 
     * Új Workplace objektum létrehozása egyedi azonosítóval.
     * 
     * @param id az egyedi azonosító
     */
    public Workplace(String id) {
        super(id);
    }

    /**
     * Kezeli azt az eseményt, amikor egy jármű belép.
     * 
     * @param vehicle a belépő jármű
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        if (vehicle != null) {
            vehicle.setCurrentNode(this);

            vehicle.setArrived(true);
        }
    }
}