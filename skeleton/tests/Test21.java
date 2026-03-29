package skeleton.tests;

import java.util.Scanner;
import skeleton.src.*;

/**
 * 21-es teszt implementációja
 * Szimulálja a Busz haladását járható útvonalon,
 * ellenőrzi a baleseteket és frissíti pozícióját.
 */
public class Test21 implements TestCase {

    /**
     * Teszt szekvencia futtatása.
     * Elindítja a Bus move függvényét.
     */
    @Override
    public void run() {
        Skeleton.printCall("Game", "tick()");
        
        Bus bus = new Bus();
        
        bus.tick(); 

        Skeleton.printReturn(""); 
    }
}