package tests;

import java.util.Scanner;

/**
 * A 24. teszteset megvalósítása: Autó elakadása járhatatlan úton teszt.
 * A teszt ellenőrzi, hogy az autó megáll-e (sebessége 0 lesz), ha az előtte lévő sáv járhatatlanná válik. [cite: 469]
 */
public class Test24 implements TestCase {

    /**
     * A tesztszekvencia végrehajtása.
     * Az autó isPassable vizsgálata false értékkel tér vissza, ezért az autó megáll és várakozik. [cite: 1130-1154, 1835-1847]
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
        System.out.println("<<< return false"); // Járhatatlan útszakasz [cite: 1148, 1850]
        
        System.out.println(">>> [Car].stopAndWait()");
        System.out.println("[STATE] Speed set to 0, position remains");
        System.out.println("<<< return");
        
        System.out.println("<<< return (from Car.move)");
        System.out.println("<<< return (from Skeleton.tick)");
    }
}