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

    ;

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
}
