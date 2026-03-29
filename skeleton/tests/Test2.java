package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * 2. teszteset : Havazás hatása az útállapotra teszt
 * A teszteset ellenőrzi, hogy a Weather osztály havazás eseménye 
 * hogyan rontja le egy adott sáv állapotát.
 */
public class Test2 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Inicializálja az időjáráskezelőt, az úthálózatot és egy konkrét sávot. 
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához (a hómennyiség eldöntéséhez)
     */
    @Override
    public void run(Scanner scanner) {


        // Szükséges objektumok példányosítása
        Weather weather = new Weather();
        RoadNetwork roadNetwork = new RoadNetwork();
        Road road = new Road();
        Lane lane = new Lane();

        // Az objektumhierarchia felépítése: Network -> Road -> Lane
        road.addLane(lane);
        roadNetwork.addRoadSection(road);
        
        
       // A havazási folyamat indítása a szekvenciadiagram alapján
        weather.snowfallTick(road);
    }
}
