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
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához (a járhatósági döntéshez szükséges)
     */
    @Override
    public void run(Scanner scanner) {
        // Inicializálás
        Vehicle vehicle = new Car();
        Lane lane2 = new Lane();


        // A sávváltási folyamat indítása a szekvenciadiagramnak megfelelően
        vehicle.changeLane(lane2);
    }
}