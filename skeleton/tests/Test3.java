package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * 3. teszteset: 3. Alagút időjárás-mentessége teszt.
 * A teszteset ellenőrzi, hogy a havazás (Weather.snowfallTick())
 * nincs hatással az alagút (Tunnel) típusú utakra.
 */
public class Test3 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Inicializálja az időjáráskezelőt és az úthálózatot, amelyben egy 
     * alagút objektum található. A teszt során elindul a havazási ciklus, 
     * és ellenőrizzük, hogy az alagút havazik-e.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához (nem használt)
     */
    @Override
    public void run(Scanner scanner) {
        // Inicializálás
        Weather weather = new Weather();
        RoadNetwork roadNetwork = new RoadNetwork();
        Tunnel tunnel = new Tunnel();
        
        // Az objektumhierarchia felépítése: RoadNetwork -> Tunnel???

        // A szekvencia indítása a diagram szerint
        weather.snowfallTick(roadNetwork);
    }
}