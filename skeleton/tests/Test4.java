package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * 4. teszteset: Jármű behajtása kereszteződésbe teszt
 * A teszteset ellenőrzi, hogy a jármű megfelelően halad át egy csomóponton.
 */
public class Test4 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Inicializálja a járművet és a kereszteződést, majd összekapcsolja őket, 
     * hogy szimulálja a jármű mozgását a csomópont felé.
     *
     */
    @Override
    public void run() {

        // Inicializálunk egy konkrét járművet (pl. Car) és kereszteződést
        Vehicle vehicle = new Car(); 
         Intersection intersection = new Intersection();
         intersection.onVehicleEnter(vehicle);
       
        
        // A mozgási folyamat indítása a szekvenciadiagram alapján
        //vehicle.move();
    }
}