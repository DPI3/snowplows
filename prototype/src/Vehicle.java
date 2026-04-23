package prototype.src;

/**
 * A Vehicle absztrakt osztály a rendszerben szereplő összes jármű közös ősosztálya.
 * Felelős az alapvető mozgási logikáért és az állapotok nyilvántartásáért.
 */
public abstract class Vehicle {

    /** A jármű egyedi azonosítója */
    protected final String id;

    /** Az aktuális sáv, amelyen a jármű halad */
    protected Lane currentLane;

    /** A jármű aktuális útvonala */
    protected Route currentRoute;

    /** A jármű pozíciója a sáv mentén */
    protected double positionOnLane;

    /** A jármű aktuális sebessége */
    protected double speed;

    /**
     * Konstruktor
     */
    protected Vehicle(String id, Lane currentLane, double speed) {
        this.id = id;
        this.currentLane = currentLane;
        this.speed = speed;
        this.positionOnLane = 0.0;
    }

    /**
     * A jármű előremozgatása a sávon.
     * Ha eléri a sáv végét, a következő Node kezeli.
     */
    public void move() {
        if (currentLane == null) return;

        positionOnLane += speed;

        if (positionOnLane >= currentLane.getLength()) {
            Node target = currentLane.getDestination();
            positionOnLane = 0.0;

            if (target != null) {
                target.onVehicleEnter(this);
            }
        }
    }

    /**
     * Egy szimulációs tick végrehajtása.
     * Alapértelmezésben csak mozog.
     */
    public void tick() {
        move();
    }

    /**
     * Sávváltás megkísérlése.
     * @return true ha sikeres
     */
    public boolean changeLane(Lane targetLane) {
        if (targetLane == null || !targetLane.isPassable()) {
            return false;
        }

        this.currentLane = targetLane;
        updatePositionOn(targetLane);
        return true;
    }

    /**
     * Pozíció frissítése egy új sávon
     */
    public void updatePositionOn(Lane lane) {
        this.currentLane = lane;
        this.positionOnLane = 0.0;
    }

    /** Getterek */
    public Lane getCurrentLane() { return currentLane; }
    public double getSpeed() { return speed; }
    public Route getCurrentRoute() { return currentRoute; }

    /** Setterek */
    public void setCurrentLane(Lane lane) { this.currentLane = lane; }
    public void setCurrentRoute(Route route) { this.currentRoute = route; }
    public void setSpeed(double speed) { this.speed = Math.max(0, speed); }
}