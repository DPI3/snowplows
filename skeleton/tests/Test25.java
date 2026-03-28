package tests;

import java.util.Scanner;

/**
 * A 25. teszteset megvalósítása: Autó célba érése teszt.
 * A teszt ellenőrzi, hogy mi történik, amikor egy civil autó sikeresen eléri a célállomását. [cite: 470]
 */
public class Test25 implements TestCase {

    /**
     * A tesztszekvencia végrehajtása.
     * Az autó behajt a csomópontba, ellenőrzi, hogy célba ért-e, majd eltűnik a forgalomból. [cite: 1155-1170, 1848-1862]
     *
     * @param scanner a bemenet olvasására szolgáló objektum
     */
    @Override
    public void run(Scanner scanner) {
        System.out.println(">>> [Skeleton].move() // Autó mozgása");
        System.out.println(">>> [Node].onVehicleEnter(c)");
        
        System.out.println(">>> [Node].checkDestination()");
        System.out.println("<<< return true");
        
        System.out.println(">>> [Node].removeFromTraffic()");
        System.out.println("[STATE] Autó inaktívvá válik, eltűnik a hálózatról");
        System.out.println("<<< return");
        
        System.out.println("<<< return (from Node.onVehicleEnter)");
        System.out.println("<<< return (from Skeleton.move)");
    }
}