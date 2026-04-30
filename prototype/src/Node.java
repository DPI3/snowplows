package src;

/**
 * Absztrakt alaposztály az úthálózat összes csatlakozási pontjához.
 */
public abstract class Node {
    
    protected String id;

    public Node(String id) {
        this.id = id;
    }

    /**
     * Visszaadja a csomópont egyedi azonosítóját.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Kezeli azt az eseményt, amikor egy jármű belép ebbe a csomópontba.
     * Az alaposztályban üres; a leszármazottak (Intersection, Terminal, stb.) felüldefiniálják.
     */
    public void onVehicleEnter(Vehicle vehicle) {
        // Alapértelmezetten üres
    }
}