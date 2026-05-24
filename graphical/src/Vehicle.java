package src;

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

    private Node currentNode;
    private boolean arrived;

    public void setCurrentNode(Node node) {
        this.currentNode = node;
    }

    public void setCurrentLane(Lane lane) {
        this.currentLane = lane;
    }

    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

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
        if (!currentLane.isPassable()) return;

        positionOnLane += speed;

        if (positionOnLane >= currentLane.getLength()) {
            positionOnLane = 0.0;

            if (currentRoute != null) {
                Lane nextLane = currentRoute.getNextLane(currentLane);

                if (nextLane != null && nextLane.isPassable()) {
                    currentLane = nextLane;
                    return;
                }
            }

            Node target = currentLane.getDestination();
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
    public String getId() { return id; }
    public Lane getCurrentLane() { return currentLane; }
    public double getSpeed() { return speed; }
    public Route getCurrentRoute() { return currentRoute; }

    /** Setterek */
    public void setCurrentRoute(Route route) { this.currentRoute = route; }
    public void setSpeed(double speed) { this.speed = Math.max(0, speed); }
}