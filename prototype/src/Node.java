package src;

/**
 * Absztrakt alaposztály az úthálózat összes csatlakozási pontjához.
 */
public abstract class Node {
    
    /** A csomópont egyedi azonosítója. */
    protected String id;

    /** 
     * Új Node objektum létrehozása egyedi azonosítóval.
     * 
     * @param id az egyedi azonosító
     */
    public Node(String id) {
        this.id = id;
    }

    /**
     * Visszaadja a csomópont egyedi azonosítóját.
     * 
     * @return egyedi azonosító
     */
    public String getId() {
        return this.id;
    }

    /**
     * Kezeli azt az eseményt, amikor egy jármű belép ebbe a csomópontba.
     * Az alaposztályban üres; a leszármazottak (Intersection, Terminal, stb.) felüldefiniálják.
     * 
     * @param vehicle a belépő jármű
     */
    public void onVehicleEnter(Vehicle vehicle) {
    }
}