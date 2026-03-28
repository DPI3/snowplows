package tests;

import java.util.Scanner;

/**
 * A 23. teszteset megvalósítása: Autó haladása járható sávban teszt.
 * A teszt ellenőrzi, hogy a Car képes-e az útvonalon következő sávba lépni, ha az járható állapotban van. [cite: 460]
 */
public class Test23 implements TestCase {

    /**
     * A tesztszekvencia végrehajtása.
     * Szimulálja, ahogy a civil autó lekéri a következő sávot, ellenőrzi a járhatóságot, majd rálép a sávra. [cite: 1113-1129, 1821-1834]
     *
     * @param scanner a bemenet olvasására szolgáló objektum
     */
    @Override
    public void run(Scanner scanner) {
        System.out.println(">>> [Skeleton].tick()");
        System.out.println(">>> [Car].move()");
        
        System.out.println(">>> [Route].getNextLane(cl)");
        System.out.println("<<< return nl");
        
        System.out.println(">>> [Lane].isPassable()");
        System.out.println("<<< return true");
        
        System.out.println(">>> [Car].setCurrentLane(nl)");
        System.out.println("[STATE] Car.currentLane = nl");
        System.out.println("<<< return");
        
        System.out.println(">>> [Car].setPositionOnLane(newPosition)");
        System.out.println("[STATE] position frissítve");
        System.out.println("<<< return");
        
        System.out.println("<<< return (from Car.move)");
        System.out.println("<<< return (from Skeleton.tick)");
    }
}