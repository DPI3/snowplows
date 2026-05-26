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

    /** A buszvezető neve. */
    private String name;

    /** A buszvezető által használt úthálózat. */
    private RoadNetwork roadNetwork;

    /** A buszvezető pénze. */
    private int money;

    /** A buszvezető pontszáma. */
    private int score;

    /**
     * Buszvezető objektum inicializálása úthálózat megadásával.
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
     * Buszvezető objektum inicializálása úthálózat nélkül.
     *
     * @param name a buszvezető neve
     * @param bus a buszvezető által irányított busz
     */
    public BusdriverRole(String name, Bus bus) {
        this(name, bus, null);
    }

    /**
     * Buszvezető objektum inicializálása pénzzel.
     *
     * @param name a buszvezető neve
     * @param bus a buszvezető által irányított busz
     * @param money a buszvezető kezdő pénze
     */
    public BusdriverRole(String name, Bus bus, int money) {
        this(name, bus, null);
        this.money = money;
    }

    /**
     * Buszvezető objektum inicializálása pénzzel és pontszámmal.
     *
     * @param name a buszvezető neve
     * @param bus a buszvezető által irányított busz
     * @param money a buszvezető kezdő pénze
     * @param score a buszvezető kezdő pontszáma
     */
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
     *
     * @param bus a busz, amelyhez az útvonalat hozzárendeljük
     * @param destination a cél csomópont
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
     * Növeli a teljesített fordulók számát és a pontszámot 50-nel.
     */
    public void incrementCompletedRounds() {
        this.completedRounds++;
        this.score+=50;
    }

    /**
     * Visszaadja a teljesített fordulók számát.
     *
     * @return a teljesített fordulók száma
     */
    public int getCompletedRounds() {
        return completedRounds;
    }

    /**
     * Visszaadja a buszvezető nevét.
     *
     * @return a buszvezető neve
     */
    public String getName() {
        return name;
    }

    /**
     * Visszaadja a buszvezető pénzét.
     *
     * @return a buszvezető pénze
     */
    public int getMoney() {
        return money;
    }

    /**
     * Növeli a buszvezető pénzét a megadott összeggel.
     *
     * @param amount a növelés mennyisége
     */
    public void increaseMoney(int amount) {
        this.money += amount;
    }

    /**
     * Csökkenti a buszvezető pénzét a megadott összeggel.
     *
     * @param amount a csökkenés mennyisége
     */
    public void decreaseMoney(int amount) {
        this.money -= amount;
    }

    /**
     * Visszaadja a buszvezető pontszámát.
     *
     * @return a buszvezető pontszáma
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
     * Csökkenti a buszvezető pontszámát a megadott összeggel.
     * A pontszám nem csökkenhet 0 alá.
     *
     * @param amount a csökkenés mennyisége
     */
    public void decreaseScore(int amount) {
        this.score = Math.max(0, this.score - amount);
    }

    /**
     * Módosítja a buszvezető pénzét a megadott összeggel.
     * A pénz nem csökkenhet 0 alá.
     *
     * @param amount a módosítás mennyisége (pozitív vagy negatív)
     */
    public void changeMoney(int amount) {
        this.money += amount;

        if (this.money < 0) {
            this.money = 0;
        }
    }
}
