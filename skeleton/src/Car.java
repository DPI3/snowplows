package skeleton.src;
import java.util.Scanner;

/**
 * A Car osztály egy személygépkocsit reprezentál a rendszerben.
 *
 * A Car a Vehicle osztályból származik, és rendelkezik
 * kiindulási (lakóhely) és cél (munkahely) csomóponttal.
 *
 * A jármű a két pont között közlekedik, és a legrövidebb
 * járható útvonalat próbálja követni.
 */
public class Car extends Vehicle {
    
    /**
     * A jármű kiindulási pontja (lakóhely).
     */
    private Node residence;

    /**
     * A jármű célállomása (munkahely).
     */
    private Node workplace;

    /**
     * A jármű aktuális útvonala.
     */
    private Route currentRoute;

    public Car() {
        super();
    }

    /**
     * Konstruktor a Car objektum létrehozásához.
     *
     * @param id azonosító
     * @param currentLane aktuális sáv
     * @param positionOnLane pozíció
     * @param speed sebesség
     * @param residence lakóhely
     * @param workplace munkahely
     * @param currentRoute aktuális útvonal
     */
    public Car(String id, Lane currentLane, double positionOnLane, double speed,
               Node residence, Node workplace, Route currentRoute) {
        super(id, currentLane, positionOnLane, speed);
        this.residence = residence;
        this.workplace = workplace;
        this.currentRoute = currentRoute;
    }

    @Override
    public void move(Scanner scanner) {
        // 1. Naplózzuk a belépést
        Skeleton.printCall("Car", "move()");

        // 2. Szimuláljuk a sáv lekérését
        Skeleton.printCall("Route", "getNextLane(cl)");
        Skeleton.printReturn("nl");

        // 3. Döntési pont: itt kéri be a program az "1"-est
        int input = Skeleton.requestInput(scanner, "A sáv járható? (1: Igen, 2: Nem)");

        if (input == 1) {
            Skeleton.printCall("Lane", "isPassable()");
            Skeleton.printReturn("true");

            Skeleton.printCall("Car", "setCurrentLane(nl)");
            Skeleton.printReturn("");

            Skeleton.printCall("Car", "setPositionOnLane(newPosition)");
            Skeleton.printReturn("");
        } else {
            // Itt kezelheted a 24-es tesztet (elakadás)
            Skeleton.printCall("Lane", "isPassable()");
            Skeleton.printReturn("false");
        }

        // 4. Naplózzuk a move() visszatérését
        Skeleton.printReturn("");
    }
}