package skeleton.src;

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
    private Residence residence;

    /**
     * A jármű célállomása (munkahely).
     */
    private Workplace workplace;

    /**
     * A jármű aktuális útvonala.
     */
    private Route currentRoute;

    /**
     * Üres konstruktor a skeleton célokra.
     */
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
               Residence residence, Workplace workplace, Route currentRoute) {
        super(id, currentLane, positionOnLane, speed);
        this.residence = residence;
        this.workplace = workplace;
        this.currentRoute = currentRoute;
    }

    /**
     * A személyautó mozgását hajtja végre.
     *
     * Skeleton implementációban csak a metódushívásokat és az állapotváltozásokat naplózza.
     */
    @Override
    public void move() {
        Skeleton.printCall("Car", "move()");
        Skeleton.printState("A személyautó előrehalad az aktuális sávban.");
        Skeleton.printReturn("");
    }
}