package src;

/**
 * A Car osztály egy személyautót reprezentál.
 */
public class Car extends Vehicle {

    private final Residence residence;
    private final Workplace workplace;
    private Road currentRoad;
    private final double defaultSpeed;
    private String location = "úton";

    public Car(String id, Lane lane, double speed, Residence res, Workplace work) {
        super(id, lane, speed);
        this.residence = res;
        this.workplace = work;
        this.defaultSpeed = speed;
    }

    @Override
    public boolean changeLane(Lane targetLane) {
        if (targetLane == null || !targetLane.isPassable()) return false;
        this.currentLane = targetLane;
        this.currentRoad = targetLane.getParentRoad();
        this.positionOnLane = 0.0;
        this.speed = defaultSpeed;
        return true;
    }

    public void stopAndWait() { this.speed = 0.0; }
    public void resume()      { this.speed = defaultSpeed; }

    public Residence getResidence()  { return residence; }
    public Workplace getWorkplace()  { return workplace; }
    public double getDefaultSpeed()  { return defaultSpeed; }
    public String getLocation()      { return location; }
    public void setLocation(String location) { this.location = location; }
}
