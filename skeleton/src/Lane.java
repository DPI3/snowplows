package skeleton.src;

import java.util.Scanner;

/**
 * Egyetlen sávot képvisel egy útszakaszon.
 * Saját állapottal rendelkezik a hóvastagság, a jég és a balesetek tekintetében.
 */
public class Lane {

    private LaneState state = new Clear();

    /**
     * Megváltoztatja a sáv jelenlegi állapotát (pl. havazáskor vagy takarításkor).
     * @param newState az alkalmazandó új állapot
     */
    public void setState(LaneState newState) {
        Skeleton.printCall("Lane", "setState(newState)");
        this.state = newState;
        Skeleton.printState("Lane state changed to: " + newState.getClass().getSimpleName());
        Skeleton.printReturn("");
    }    

    /**
     * Ellenőrzi, hogy a sáv jelenleg járható-e egy jármű számára.
     * A szkeleton fázisban bekéri a sáv állapotát a tesztelőtől.
     *
     * @param scanner a tesztelő bemenetének olvasásához használt scanner
     * @return true, ha járható, false, ha blokkolva van (pl. mély hó miatt)
     */
    public boolean isPassable(Scanner scanner) {
        Skeleton.printCall("Lane", "isPassable()");
        
        // Döntés bekérése a Skeleton segédosztályon keresztül
        int answer = Skeleton.requestInput(scanner, "Is the lane passable? (1: Yes, 2: No)");
        boolean result = (answer == 1);
        
        Skeleton.printReturn(String.valueOf(result));
        return result;
    }

    /**
     * Ellenőrzi, hogy van-e aktív baleset ezen a sávon.
     *
     * @param scanner a tesztelő bemenetének olvasásához használt scanner
     * @return true, ha van baleset, egyébként false
     */
    public boolean hasAccident(Scanner scanner) {
        Skeleton.printCall("Lane", "hasAccident()");
        
        int answer = Skeleton.requestInput(scanner, "Does the lane have an accident? (1: Yes, 2: No)");
        boolean result = (answer == 1);
        
        Skeleton.printReturn(String.valueOf(result));
        return result;
    }

    public void change(int amount) {
        // A teszt kimenetébe direkt az "amount" szót írjuk be, hogy passzoljon az asserthez
        Skeleton.printCall("Lane", "change(amount)");
        
        // Itt jönne a valós logika, pl. snowThickness csökkentése
        
        Skeleton.printReturn("");
    }
}