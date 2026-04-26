package prototype.src;

import skeleton.src.RoadNetwork;

/**
 * A BusdriverRole a buszvezető szerepkört reprezentálja.
 * Felelős a buszok mozgatásáért, útvonalak megtervezéséért
 * és a fordulók teljesítéséért két végállomás között.
 */
public class BusdriverRole extends Role {

    /** A buszvezető által teljesített fordulók száma. */
    private int completedRounds;

    /** A buszvezető által irányított busz. */
    private Bus bus;

    /** A buszvezető neve. */
    private String name;

    /** A  BusdriverRole objektum konstruktora
     * 
     * @param name a buszvezető neve
     * @param bus a buszvezető által mozgatandó busz
    */
    public BusdriverRole(String name, Bus bus) {
        completedRounds=0;
        this.bus=bus;
    }

    /**
     * A busz aktuális helyzetéből meghatározza a cél csomópontba vezető
     * legrövidebb útvonalat, és hozzárendeli azt a buszhoz.
     *
     * @param bus a mozgatandó busz
     * @param destination a célcsomópont
     * @return a művelet eredménye
     */
    public int assignRoute(Bus bus, Node destination) {
        Route newRoute = RoadNetwork.getShortestPath(bus.getTerminal_A(), destination);
        if (newRoute == null) {
            bus.setCurrentRoute(null);
            return 0;
        }
        bus.setCurrentRoute(newRoute);
        int sumWeight=0;
        for(Lane lane: newRoute.getLanes()){
            sumWeight+=lane.getDynamicWeight();
        }
        return sumWeight;
    }

    /**
     * Növeli a teljesített fordulók számát.
     */
    public void incrementCompletedRounds() {
        this.completedRounds++;
    }

    /**
     * A buszvezető szerepkör pontszámát adja vissza, ami a teljesített fordulók számán alapul.
     *
     * @return a szerepkör pontszáma
     */
    @Override
    public int getScore() {
        return completedRounds*50;
    }
}
