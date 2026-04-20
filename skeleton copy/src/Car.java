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
        Skeleton.printCall("Car", "Car()");
        Skeleton.printReturn("");
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
     * Beállítja a személyautó aktuális útvonalát.
     * @param route a beállítandó útvonal
     */
    public void setCurrentRoute(Route route) {
        this.currentRoute = route;
    }


    /**
     * A személyautó mozgását hajtja végre.
     *
     * Skeleton implementációban csak a metódushívásokat és az állapotváltozásokat naplózza.
     */
    /**
     * Egy szimulációs lépést hajt végre az autón.
     */
    @Override
    public void tick() {
        Skeleton.printCall("Car", "tick()");
        this.move();
        Skeleton.printReturn("");
    }

    /**
     * A személyautó mozgását hajtja végre.
     * Lekérdezi a következő sávot, és ha az járhatatlan, megáll.
     */
    @Override
    public void move() {
        Skeleton.printCall("Car", "move()");

        if (this.currentRoute == null) {

            if (this.workplace != null) {
                this.workplace.onVehicleEnter(this);
            }
            Skeleton.printState("IDLE (Destination reached)");
            Skeleton.printReturn("");
            return; 
        }

        Skeleton.printCall("Route", "getNextLane(cl)");
        Skeleton.printReturn("nl");

        int passableInput = Skeleton.requestInput("A sáv járható? (1: Igen, 2: Nem)");
        
        if (passableInput == 1) {
            Skeleton.printCall("Lane", "isPassable()");
            Skeleton.printReturn("true");
            Skeleton.printState("update position");
        } else {
            Skeleton.printCall("Lane", "isPassable()");
            Skeleton.printReturn("false");
            
            this.stopAndWait();
        }

        Skeleton.printReturn("");
    }

    /**
     * Az autó megáll és várakozik, mert az előtte lévő út járhatatlan.
     */
    public void stopAndWait() {
        Skeleton.printCall("Car", "stopAndWait()");
        
        this.speed = 0; // A sebességet nullázzuk
        Skeleton.printState("Speed set to 0, position remains");
        
        Skeleton.printReturn("");
    }

    @Override 
        public boolean changeLane(Lane targetLane) {
        Skeleton.printCall("Car", "changeLane(targetLane)");

        boolean passable = targetLane.isPassable();

        if (passable) {
            // 2. Beállítjuk az új sávot
            currentLane= targetLane;
            Skeleton.printState("currentLane = targetLane");

            // 3. Frissítjük a pozíciót
            updatePositionOn(targetLane);
            Skeleton.printState("position updated on targetLane");

            Skeleton.printReturn("true");
            return true;
        }

        Skeleton.printReturn("false");
        return false;
    }

}