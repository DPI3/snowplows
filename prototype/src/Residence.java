package skeleton.src;
/**
 * A Residence osztaly egy lakohely csomopontot reprezental a halozatban.
 *
 * A lakohely a szemelygepkocsik egyik kiindulasi pontja,
 * ahol a jarmuvekhez kapcsolodo erkezesi esemenyek kezelhetok.
 */
public class Residence extends Node{
    
    /**
     * Residence peldany letrehozasa.
     *
     * @param id a csomopont egyedi azonositoja
     */
    public Residence(String id){
        super(id);
    }

    /**
     * Kezeli a jarmu lakohely csomopontba erkezesenek esemenyet.
     *
     * @param vehicle az erkezo jarmu
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle){

    }
}