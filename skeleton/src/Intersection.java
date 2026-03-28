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

    /**
     * Kezeli a jarmu keresztezodesbe erkezesenek esemenyet.
     *
     * @param vehicle az erkezo jarmu
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle){

    }
}