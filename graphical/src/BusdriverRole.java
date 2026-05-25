package src;

/**
 * A BusdriverRole a buszvezető szerepkört reprezentálja.
 * A buszvezető felelős a buszok mozgatásáért, útvonalak megtervezéséért és a 
 * fordulók teljesítéséért két végállomás között. 
 * A szerepkör pontszáma a sikeresen teljesített fordulókból adódik.
 */
public class BusdriverRole extends Role {

    /** A buszvezető által teljesített fordulók száma. */
    private int completedRounds;

    /** A buszvezető által irányított busz. */
    private Bus bus;

    /** A buszvezető által irányított busz. */
    private String name;

    /** A buszvezető által használt úthálózat. */
    private RoadNetwork roadNetwork;

    /** A buszvezető pénze. */
    private int money;

    /** A buszvezető pontszáma. */
    private int score;

    /**
     * Buszvezető objektum inicializálása úthálózat megadásával
     * 
     * @param name a buszvezető neve
     * @param bus a buszvezető által irányított busz
     * @param roadNetwork a buszvezető által használt úthálózat
     */
    public BusdriverRole(String name, Bus bus, RoadNetwork roadNetwork) {
        completedRounds = 0;
        this.name = name;
        this.bus = bus;
        this.roadNetwork = roadNetwork;
        this.money = 0;
        this.score = 0;
    }

    /**
     * Buszvezető objektum inicializálása
     * 
     * @param name a buszvezető neve
     * @param bus a buszvezető által irányított busz
     */
    public BusdriverRole(String name, Bus bus) {
        this(name, bus, null);
    }

    /** Tesztkörnyezethez: pénzzel inicializált konstruktor. */
    public BusdriverRole(String name, Bus bus, int money) {
        this(name, bus, null);
        this.money = money;
    }

    /** Tesztkörnyezethez: pénzzel és pontszámmal inicializált konstruktor. */
    public BusdriverRole(String name, Bus bus, int money, int score) {
        this(name, bus, null);
        this.money = money;
        this.score = score;
    }

    /**
     * A busz aktuális állapotából a cél csomópontba megtalálja a legrövidebb útvonalat.
     * A metódus a RoadNetwork osztály getShortestPath() függvényét használja.
     * Az útvonal kiszámítása után a metódus beállítja a busz currentRoute attribútumát, hogy a jármű a
     * következő szimulációs ciklusban ezen az útvonalon haladhasson tovább. 
     * A metódus visszatérési értéke a megtalált útvonal teljes hossza (összegzett súly), vagy 0, ha nem 
     * található járható útvonal.
     * 
     * @param bus
     * @param destination
     * @return az összegzett súly, vagy 0, ha nem található járható útvonal
     */
    public int assignRoute(Bus bus, Node destination) {
        if (bus == null || destination == null) return 0;
        if (roadNetwork == null) return 0;
        Route newRoute = roadNetwork.getShortestPath(bus.getTerminal_A(), destination);
        if (newRoute == null) {
            bus.setCurrentRoute(null);
            return 0;
        }
        bus.setCurrentRoute(newRoute);
        int sumWeight = 0;
        for (Lane lane : newRoute.getLanes()) {
            sumWeight += lane.getDynamicWeight();
        }
        return sumWeight;
    }

    /**
     * Növeli a teljesített fordulók és a pont számát.
     */
    public void incrementCompletedRounds() {
        this.completedRounds++;
        this.score+=50;
    }

    /**
     * Megadja a teljesített fordulók számát.
     * 
     * @return teljesített fordulók száma
     */
    public int getCompletedRounds() {
        return completedRounds;
    }

    /**
     * Visszaadja a buszvezető nevét.
     * 
     * @return  buszvezető neve
     */
    public String getName() {
        return name;
    }

     /**
     * Visszaadja a buszvezető pénzét.
     * 
     * @return  buszvezető pénze
     */
    public int getMoney() {
        return money;
    }

     /**
     * Növeli a buszvezető pénzét.
     * 
     * @param amount a növelés mennyisége
     */
    public void increaseMoney(int amount) {
        this.money += amount;
    }

     /**
     * Csökkenti a buszvezető pénzét.
     * 
     * @param amount a csökkenés mennyisége
     */
    public void decreaseMoney(int amount) {
        this.money -= amount;
    }

     /**
     * Visszaadja a buszvezető pontszámát.
     * 
     * @return  buszvezető pontszáma
     */
    @Override
    public int getScore() {
        return score;
    }

     /**
     * Beállítja a buszvezető pontszámát.
     * 
     * @param score a buszvezető új pontszáma
     */
    public void setScore(int score) {
        this.score = score;
    }

    
     /**
     * Csökkenti a buszvezető pontszámát.
     * 
     * @param amount a csökkenés mennyisége
     */
    public void decreaseScore(int amount) {
        this.score = Math.max(0, this.score - amount);
    }

    public void changeMoney(int amount) {
        this.money += amount;

        if (this.money < 0) {
            this.money = 0;
        }
    }
}
