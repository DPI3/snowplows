public abstract class Vehicle {
    protected String id;
    protected Lane currentLane;
    protected double positionOnLane;
    protected double speed;

    public Vehicle(String id, Lane currentLane, double positionOnLane, double speed) {
        this.id = id;
        this.currentLane = currentLane;
        this.positionOnLane = positionOnLane;
        this.speed = speed;

    }

    protected void move() {
    }

    public void tick() {
    }
}