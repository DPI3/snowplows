package prototype.src;

/**
 * Az Intersection osztály egy kereszteződést reprezentál.
 */
public class Intersection extends Node {

    public Intersection(String id) {
        super(id);
    }

    public Intersection() {
        super("default_intersection");
    }

    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        if (vehicle != null) {
            Lane nextLane = new Lane();
            vehicle.setCurrentLane(nextLane);
        }
    }
}