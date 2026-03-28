package tests;

import java.util.Scanner;

/**
 * A 21. teszteset megvalósítása: Busz közlekedés teszt.
 * A busz a kijelölt útvonal mentén halad, figyelembe véve az út állapotát, az esetleges akadályokat és sávváltásokat. [cite: 458]
 */
public class Test21 implements TestCase {

    /**
     * A tesztszekvencia végrehajtása.
     * A tesztelés során a busz haladását szimuláljuk, ahol a sáv járható (passable == true). [cite: 1056-1093, 1771-1797]
     *
     * @param scanner a bemenet olvasására szolgáló objektum
     */
    @Override
    public void run(Scanner scanner) {
        System.out.println(">>> [Game].tick()");
        System.out.println(">>> [Bus].move()");
        
        System.out.println(">>> [Route].getNextLane(currentLane)");
        System.out.println("<<< return nextLane");
        
        System.out.println(">>> [Lane].isPassable()");
        System.out.println("<<< return true"); 
        
        System.out.println("[STATE] update position");
        
        System.out.println(">>> [Lane].hasAccident()");
        System.out.println("<<< return false");
        
        System.out.println("<<< return (from Bus.move)");
        System.out.println("<<< return (from Game.tick)");
    }
}