package skeleton.src;
import org.w3c.dom.Node;

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
}