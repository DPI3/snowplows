package skeleton.src;

/**
 * Absztrakt alaposztály az úthálózat összes csatlakozási pontjához (kereszteződések, 
 * végállomások, munkahelyek, lakóhelyek).
 */
public abstract class Node {
    protected String id;

    public Node(String id) {
        this.id = id;
    }

    /**
     * Kezeli azt az eseményt, amikor egy jármű belép ebbe a csomópontba.
     * A specifikus viselkedést az alosztályok valósítják meg.
     *
     * @param vehicle a csomópontba belépő jármű
     */
    public void onVehicleEnter(Vehicle vehicle) {
        Skeleton.printCall("Node", "onVehicleEnter(vehicle)");
        Skeleton.printReturn("");
    }

    /**
     * Ellenőrzi, hogy ez a csomópont-e a jármű végső úti célja.
     * A szkeleton fázisban alapértelmezés szerint true-t ad vissza a sikeres megérkezés teszteléséhez.
     *
     * @return true, ha a csomópont a célállomás, egyébként false
     */
    public boolean checkDestination() {
        Skeleton.printCall("Node", "checkDestination()");
        Skeleton.printReturn("true");
        return true;
    }
    
    /**
     * Eltávolítja a járművet az aktív forgalomból, amint eléri a célját.
     */
    public void removeFromTraffic() {
        Skeleton.printCall("Node", "removeFromTraffic()");
        Skeleton.printState("Vehicle becomes inactive, disappears from the network");
        Skeleton.printReturn("");
    }
}