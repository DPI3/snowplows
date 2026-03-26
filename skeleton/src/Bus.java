public class Bus extends Vehicle {
    private Node terminalA;
    private Node terminalB;
    private int immobileTime;
    private Route currentRoute;

    public Bus(String id, Lane currentLane, double positionOnLane, double speed,
               Node terminalA, Node terminalB, int immobileTime, Route currentRoute) {
        super(id, currentLane, positionOnLane, speed);
        this.terminalA = terminalA;
        this.terminalB = terminalB;
        this.immobileTime = immobileTime;
        this.currentRoute = currentRoute;
    }

    @Override
    public void tick() {
    }
}