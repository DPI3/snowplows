package src;

/**
 * A Vehicle absztrakt osztály a rendszerben szereplő összes jármű közös ősosztálya.
 * Felelős az alapvető mozgási logikáért és az állapotok nyilvántartásáért.
 */
public abstract class Vehicle {

    /** A jármű egyedi azonosítója. */
    protected final String id;

    /** Az aktuális sáv, amelyen a jármű halad. */
        protected Lane currentLane;

    /** A jármű aktuális útvonala. */
    protected Route currentRoute;

    /** A jármű pozíciója a sáv mentén. */
    protected double positionOnLane;

    /** A jármű aktuális sebessége. */
    protected double speed;

    /** A jármű aktuális csomópontja. */
    private Node currentNode;

    /** Jelzi, hogy a jármű megérkezett-e a céljához. */
    private boolean arrived;

    /**
     * Beállítja a jármű aktuális csomópontját.
     *
     * @param node az új csomópont
     */
    public void setCurrentNode(Node node) {
        this.currentNode = node;
    }

    /**
     * Beállítja a jármű aktuális sávját.
     *
     * @param lane az új sáv
     */
    public void setCurrentLane(Lane lane) {
        this.currentLane = lane;
    }

    /**
     * Beállítja, hogy a jármű megérkezett-e.
     *
     * @param arrived true, ha a jármű megérkezett
     */
    public void setArrived(boolean arrived) {
        this.arrived = arrived;
    }

    /**
     * Jármű konstruktora.
     *
     * @param id a jármű egyedi azonosítója
     * @param currentLane a kezdő sáv
     * @param speed a jármű sebessége
     */
    protected Vehicle(String id, Lane currentLane, double speed) {
        this.id = id;
        this.currentLane = currentLane;
        this.speed = speed;
        this.positionOnLane = 0.0;
    }

    /**
     * A jármű előremozgatása a sávon.
     * Ha eléri a sáv végét, a következő csomópont kezeli a továbbhaladást.
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
     * Egy szimulációs lépés végrehajtása.
     * Alapértelmezésben csak a mozgást hajtja végre.
     */
    public void tick() {
        move();
    }

    /**
     * Sávváltás megkísérlése a megadott célsávra.
     *
     * @param targetLane a célsáv
     * @return true, ha a sávváltás sikeres volt; false egyébként
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
     * Pozíció frissítése egy új sávon, a pozíciót nullára állítja.
     *
     * @param lane az új sáv
     */
    public void updatePositionOn(Lane lane) {
        this.currentLane = lane;
        this.positionOnLane = 0.0;
    }

    /**
     * Visszaadja a jármű egyedi azonosítóját.
     *
     * @return az azonosító
     */
    public String getId() { return id; }

    /**
     * Visszaadja a jármű aktuális sávját.
     *
     * @return az aktuális sáv
     */
    public Lane getCurrentLane() { return currentLane; }

    /**
     * Visszaadja a jármű aktuális sebességét.
     *
     * @return a sebesség
     */
    public double getSpeed() { return speed; }

    /**
     * Visszaadja a jármű aktuális útvonalát.
     *
     * @return az aktuális útvonal
     */
    public Route getCurrentRoute() { return currentRoute; }

    /**
     * Beállítja a jármű aktuális útvonalát.
     *
     * @param route az új útvonal
     */
    public void setCurrentRoute(Route route) { this.currentRoute = route; }

    /**
     * Beállítja a jármű sebességét. A sebesség nem lehet negatív.
     *
     * @param speed az új sebesség
     */
    public void setSpeed(double speed) { this.speed = Math.max(0, speed); }
}
