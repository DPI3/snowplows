package tests;

import java.util.Scanner;

/**
 * A 22. teszteset megvalósítása: Busz forduló teljesítése teszt.
 * A teszt ellenőrzi, hogy a busz helyesen növeli a fordulószámot, amikor eléri a végállomást, és új útvonalat kap visszafelé. [cite: 459]
 */
public class Test22 implements TestCase {

    /**
     * A tesztszekvencia végrehajtása.
     * A busz eléri a terminált, a BusDriverRole fordulószáma nő, majd új utat kap. [cite: 1094-1112, 1798-1820]
     *
     * @param scanner a bemenet olvasására szolgáló objektum
     */
    @Override
    public void run(Scanner scanner) {
        System.out.println(">>> [Bus].move()");
        System.out.println(">>> [Bus].checkTerminalReached()");
        System.out.println("<<< return true");
        
        System.out.println(">>> [BusDriverRole].incrementCompletedRounds()");
        System.out.println("[STATE] completedRounds++");
        System.out.println("<<< return");
        
        System.out.println(">>> [RoadNetwork].getShortestPath(terminalB, terminalA)");
        System.out.println("<<< return r");
        
        System.out.println(">>> [Bus].setCurrentRoute(r)");
        System.out.println("[STATE] Bus.currentRoute = r");
        System.out.println("<<< return");
        
        System.out.println(">>> [Bus].move() // Elindulás a visszaúton");
        System.out.println("<<< return");
        
        System.out.println("<<< return (from initial move)");
    }
}