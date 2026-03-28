package tests;

import java.util.Scanner;

/**
 * A 20. teszteset megvalósítása: Busz útvonalhoz rendelése teszt.
 * Azt vizsgálja, hogy a BusDriverRole sikeresen hozzá tud-e rendelni egy célállomást
 * és a legrövidebb útvonalat a buszhoz. [cite: 456]
 */
public class Test20 implements TestCase {

    /**
     * A tesztszekvencia végrehajtása.
     * Meghívja az assignRoute metódust, lekéri a legrövidebb utat, beállítja a busz útvonalát, majd elindítja. [cite: 1043-1055, 1759-1770]
     *
     * @param scanner a bemenet olvasására szolgáló objektum
     */
    @Override
    public void run(Scanner scanner) {
        System.out.println(">>> [BusDriverRole].assignRoute(b, dest)");
        
        System.out.println(">>> [RoadNetwork].getShortestPath(from, dest)");
        System.out.println("<<< return r");
        
        System.out.println(">>> [Bus].setCurrentRoute(r)");
        System.out.println("[STATE] Bus.currentRoute = r");
        System.out.println("<<< return");
        
        System.out.println("<<< return (from assignRoute)");
        
        System.out.println(">>> [Game].tick()");
        System.out.println(">>> [Bus].move()");
        System.out.println("<<< return");
        System.out.println("<<< return");
    }
}