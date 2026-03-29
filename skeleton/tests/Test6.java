package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * 6. teszteset: Hókotró haladása teszt
 * A teszteset ellenőrzi, hogy a hókotró helyesen halad Lane‑eken.
 */
public class Test6 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Inicializálja a hókotró objektumot, majd elindítja a mozgási folyamatot.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához (nem használt)
     */
    @Override
    public void run(Scanner scanner) {
        // Inicializálunk egy hókotrót
        Snowplow snowplow = new Snowplow();
        
        //utak hozzáadása??

        // A mozgási folyamat indítása a szekvenciadiagram alapján
        snowplow.move();
    }
}