public class Car extends Vehicle {
    private Node residence;
    private Node workplace;
    private Route currentRoute;

    public Car(String id, Lane currentLane, double positionOnLane, double speed,
               Node residence, Node workplace, Route currentRoute) {
        super(id, currentLane, positionOnLane, speed);
        this.residence = residence;
        this.workplace = workplace;
        this.currentRoute = currentRoute;
    }
}