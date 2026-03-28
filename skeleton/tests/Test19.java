package tests;

import java.util.Scanner;

/**
 * A 19. teszteset megvalósítása: Pontszerzés takarítással teszt.
 * A teszteset azt szimulálja, amikor a takarító pénzt kap egy sáv megtisztításáért. [cite: 450]
 */
public class Test19 implements TestCase {

    /**
     * A tesztszekvencia végrehajtása.
     * Szimulálja a hókotró irányítását, a tisztítási folyamatot, majd a pontszám frissítését. [cite: 1024-1042, 1742-1758]
     *
     * @param scanner a bemenet olvasására szolgáló objektum
     */
    @Override
    public void run(Scanner scanner) {
        System.out.println(">>> [CleanerRole].controlSnowplow(sp, l)");
        System.out.println(">>> [Snowplow].clean(l)");
        System.out.println(">>> [DragonHead].clean(l, sp)"); // Példaként sárkányfejjel [cite: 1031]
        
        System.out.println(">>> [Scoreboard].change(amount)");
        System.out.println("[STATE] Recognize cleaning, add money (modify attribute)");
        System.out.println("<<< return");
        
        System.out.println("<<< return (from DragonHead.clean)");
        System.out.println("<<< return (from Snowplow.clean)");
        System.out.println("<<< return (from CleanerRole.controlSnowplow)");
        
        System.out.println(">>> [Scoreboard].evaluate()");
        System.out.println(">>> [CleanerRole].getScore()");
        System.out.println("<<< return currentScore");
        System.out.println("<<< return (from Scoreboard.evaluate)");
    }
}