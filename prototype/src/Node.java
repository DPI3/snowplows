package skeleton.src;

/**
 * Alaposztály az úthálózat összes csatlakozási pontjához.
 */
public class Node {
    protected String id;

    public Node() {
        Skeleton.printCall("Node", "Node()");
        Skeleton.printReturn("");
    }

    public Node(String id) {
        this.id = id;
    }

    public String getId() {
        Skeleton.printCall("Node", "getId()");
        Skeleton.printReturn(this.id);
        return this.id;
    }

    /**
     * Kezeli azt az eseményt, amikor egy jármű belép ebbe a csomópontba.
     */
    public void onVehicleEnter(Vehicle vehicle) {
        // Az UML szerint a paraméter neve "vehicle"
        Skeleton.printCall("Node", "onVehicleEnter(vehicle)");
        Skeleton.printReturn("");
    }
}