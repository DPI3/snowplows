package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * 5. teszteset: Jármű sávváltása teszt
 * A teszteset ellenőrzi, hogy a jármű helyesen vált sávot, ha a szomszédos Lane járható.
 */
public class Test5 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Inicializálja a járművet és a célsávot (Lane).
     *
     */
    @Override
    public void run() {
        // Inicializálás
        Vehicle vehicle = new Car();
        Lane targetLane = new Lane();


        // A sávváltási folyamat indítása a szekvenciadiagramnak megfelelően
        vehicle.changeLane(targetLane);
    }
}