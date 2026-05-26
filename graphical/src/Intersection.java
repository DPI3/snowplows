package src;

/**
 * Az Intersection osztály felel az úthálózat (RoadNetwork) csomópontjainak reprezentálásáért. 
 * Ez az osztály köti össze az egyes útszakaszokat (Road vagy Lane), és biztosítja a járművek (Vehicle) számára 
 * az áthaladást és a kanyarodást az egyik útszakaszról a másikra. Szerepet játszik a járművek útvonalkeresésében
 * (routing) is, hiszen innen kérdezhető le, hogy egy adott pontból mely további utak érhetőek el.
 */
public class Intersection extends Node {

    /**
     * Új Intersection objektum létrehozása azonosító megadásával.
     * 
     * @param id az azonosító
     */
    public Intersection(String id) {
        super(id);
    }

    /**
     * Új Intersection objektum létrehozása alapértelmezett azonosítóval.
     */
    public Intersection() {
        super("default_intersection");
    }

    /**
     * Felüldefiniált metódus a jármű érkezésére.
     * Hozzáadja a járművet a vehiclesInside listához, és kezeli az áthaladást a következő útszakaszra.
     *
     * @param vehicle a belépő jármű
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        if (vehicle != null) {
            Lane nextLane = new Lane();
            vehicle.setCurrentLane(nextLane);
        }
    }
}