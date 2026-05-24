package src;

/**
 * A Car osztály a városban lakóhely és munkahely között közlekedő személyautót reprezentálja.
 * Az autó célja, hogy mindig a legrövidebb járható útvonalon jusson el a célállomására.
 * Az útvonaltervezést a RoadNetwork végzi, míg a Car saját feladata a kijelölt útvonal követése és az
 * aktuális közlekedési helyzethez való alkalmazkodás. Az autó reagálhat az utak járhatatlanná válására, és
 * szükség esetén újratervezés kezdeményezhető.
 */
public class Car extends Vehicle {

    /** A járműhöz tartozó lakóhely. */
    private final Residence residence;

    /** A járműhöz tartozó munkahely. */
    private final Workplace workplace;

    /** Az aktuális útszakasz. */
    private Road currentRoad;

    /** Az alapértelmezett sebesség. */
    private final double defaultSpeed;

    /** Az autó állapota. */
    private String location = "úton";

    private RoadNetwork roadNetwork;
    private Node currentTarget;

    /**
     * Létrehozza a Car objektumot a megadott paraméterekkel.
     *
     * @param id az autó azonosítója.
     * @param lane az aktuális sáv, amelyen az autó halad.
     * @param speed az autó sebessége.
     * @param res az autóhoz tartozó lakóhely.
     * @param work az autóhoz tartozó munkahely.
     */
    public Car(String id, Lane lane, double speed, Residence res, Workplace work) {
        super(id, lane, speed);
        this.residence = res;
        this.workplace = work;
        this.defaultSpeed = speed;
    }

  
    /**
     * Az autó sávváltása a feltételek ellenőrzésével.
     */
    @Override
    public boolean changeLane(Lane targetLane) {
        if (targetLane == null || !targetLane.isPassable()) return false;
        this.currentLane = targetLane;
        this.currentRoad = targetLane.getParentRoad();
        this.positionOnLane = 0.0;
        this.speed = defaultSpeed;
        return true;
    }

    /**
     * Az autó várakozását szimulálja, az autó megáll (sebesség 0-ra csökken).
     */
    public void stopAndWait() { this.speed = 0.0; }

    /**
     * Aktuális sebesség állítása alapértelmezettre.
     */
    public void resume()      { this.speed = defaultSpeed; }

     /**
     * Visszaadja az autóhoz tartozó lakóhelyet.
     *
     * @return az autóhoz tartozó lakóhely
     */
    public Residence getResidence()  { return residence; }

     /**
     * Visszaadja az autóhoz tartozó munkahelyet.
     *
     * @return az autóhoz tartozó munkahely
     */
    public Workplace getWorkplace()  { return workplace; }

     /**
     * Visszaadja az autó alapértelmezett sebességét.
     *
     * @return az autó alapértelmezett sebessége
     */
    public double getDefaultSpeed()  { return defaultSpeed; }

    /**
     * Az autó aktuális állapotának lekérdezése szöveges formában
     * 
     * @return az aktuális állapot
     */
    public String getLocation()      { return location; }

    /**
     * Az autó állapotának beállítása
     * 
     * @param location az új állapot
     */
    public void setLocation(String location) { this.location = location; }

    public void setRoadNetwork(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    public void setCurrentTarget(Node currentTarget) {
        this.currentTarget = currentTarget;
    }

    @Override
    public void tick() {
        if (currentLane == null) return;

        if (!currentLane.isPassable()) {
            if (tryAdjacentLane()) {
                resume();
            } else {
                stopAndWait();
            }
            return;
        }

        resume();
        move();

        if (currentRoute != null) {
            Lane next = currentRoute.getNextLane(currentLane);

            if (next != null && !next.isPassable()) {
                replanRoute();
            }
        }
    }

    private boolean tryAdjacentLane() {
        Road road = currentLane.getParentRoad();
        if (road == null) return false;

        Lane left = road.getAdjacentLane(currentLane, -1);
        Lane right = road.getAdjacentLane(currentLane, 1);

        if (left != null && left.isPassable()) {
            return changeLane(left);
        }

        if (right != null && right.isPassable()) {
            return changeLane(right);
        }

        return false;
    }

    private void replanRoute() {
        if (roadNetwork == null || currentTarget == null || currentLane.getDestination() == null) {
            return;
        }

        Route newRoute = roadNetwork.getShortestPath(currentLane.getDestination(), currentTarget);

        if (newRoute != null && !newRoute.getLanes().isEmpty()) {
            setCurrentRoute(newRoute);
        }
    }
}
