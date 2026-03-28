/**
 * A Terminal osztaly egy vegallomas csomopontot reprezental a halozatban.
 *
 * A vegallomas a buszok egyik kulcsfontossagu allomasa,
 * ahol az erkezeshez kapcsolodo forgalmi esemenyek kezelhetok.
 */
public class Terminal extends Node{
    
    /**
     * Terminal peldany letrehozasa.
     *
     * @param id a csomopont egyedi azonositoja
     */
    public Terminal(String id){
        super(id);
    }

    /**
     * Kezeli a jarmu vegallomasra erkezesenek esemenyet.
     *
     * @param vehicle az erkezo jarmu
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle){

    }
}