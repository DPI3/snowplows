package skeleton.src;
/**
 * Az Intersection osztaly egy keresztezodes csomopontot reprezental.
 *
 * A keresztezodes olyan forgalmi pont, ahol tobb utszakasz talalkozik,
 * es a jarmuvek athaladasa esemenykezelessel kovetheto.
 */
public class Intersection extends Node{
    
    /**
     * Intersection peldany letrehozasa.
     *
     * @param id a csomopont egyedi azonositoja
     */
    public Intersection(String id){
        super(id);
    }

    public Intersection() {
        super("default_intersection");
    }

    /**
     * Kezeli a jarmu keresztezodesbe erkezesenek esemenyet.
     *
     * @param vehicle az erkezo jarmu
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        Skeleton.printCall("Node", "onVehicleEnter(vehicle)");
        vehicle.setCurrentLane(new Lane()); //Ideiglenes, a logika még nincsen kész
        Skeleton.printReturn("");
    }
}