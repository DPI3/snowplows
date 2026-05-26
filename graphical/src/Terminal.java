package src;

/**
 * A Terminal osztály egy végállomás típusú csomópontot reprezentál.
 */
public class Terminal extends Node {

    /**
     * Új Terminal objektum létrehozása egyedi azonosítóval.
     *
     * @param id az egyedi azonosító
     */
    public Terminal(String id) {
        super(id);
    }

    /**
     * Új Terminal objektum létrehozása alapértelmezett azonosítóval.
     */
    public Terminal() {
        super("default_terminal");
    }

    /**
     * Kezeli azt az eseményt, amikor egy jármű belép a végállomásra.
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
