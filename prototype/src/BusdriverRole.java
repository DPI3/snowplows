package src;

/**
 * A BusdriverRole a buszvezető szerepkört reprezentálja.
 */
public class BusdriverRole extends Role {

    private int completedRounds;
    private Bus bus;
    private String name;
    private RoadNetwork roadNetwork;
    private int money;
    private int score;

    public BusdriverRole(String name, Bus bus, RoadNetwork roadNetwork) {
        completedRounds = 0;
        this.name = name;
        this.bus = bus;
        this.roadNetwork = roadNetwork;
        this.money = 0;
        this.score = 0;
    }

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

    public void incrementCompletedRounds() {
        this.completedRounds++;
    }

    public int getCompletedRounds() {
        return completedRounds;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public void increaseMoney(int amount) {
        this.money += amount;
    }

    public void decreaseMoney(int amount) {
        this.money -= amount;
    }

    @Override
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void decreaseScore(int amount) {
        this.score = Math.max(0, this.score - amount);
    }
}
