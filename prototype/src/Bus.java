package src;

/**
 * A Bus osztály egy városi buszt reprezentál.
 */
public class Bus extends Vehicle {

    private Terminal terminalA;
    private Terminal terminalB;
    private int immobileTime;
    private String location = "úton";

    public Bus(String id, Lane lane, double speed, Terminal a, Terminal b) {
        super(id, lane, speed);
        this.terminalA = a;
        this.terminalB = b;
    }

    public Terminal getTerminal_A() { return terminalA; }
    public Terminal getTerminal_B() { return terminalB; }
    public int getImmobileTime()    { return immobileTime; }
    public String getLocation()     { return location; }
    public void setLocation(String location) { this.location = location; }

    @Override
    public void tick() {
        if (currentLane == null || currentRoute == null) return;
        if (currentLane.isPassable()) {
            immobileTime = 0;
            move();
        } else {
            immobileTime++;
        }
    }
}
