/**
 * A Workplace osztaly egy munkahely csomopontot reprezental a halozatban.
 *
 * A munkahely a szemelygepkocsik egyik celallomasa, ahol a jarmuvek
 * forgalmi esemenyei feldolgozhatok.
 */
public class Workplace extends Node{
    
    /**
     * Workplace peldany letrehozasa.
     *
     * @param id a csomopont egyedi azonositoja
     */
    public Workplace(String id){
        super(id);
    }

    /**
     * Kezeli a jarmu munkahely csomopontba erkezesenek esemenyet.
     *
     * @param vehicle az erkezo jarmu
     */
    @Override
    public void onVehicleEnter(Vehicle vehicle){

    }
}