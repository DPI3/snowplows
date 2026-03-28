package skeleton.src;

/**
 * A BusdriverRole a buszvezető szerepkört reprezentálja.
 * Felelős a buszok mozgatásáért, útvonalak megtervezéséért
 * és a fordulók teljesítéséért két végállomás között.
 */
public class BusdriverRole extends Role {

    /** A buszvezető által teljesített fordulók száma. */
    private int completedRounds;

    public BusdriverRole() {}

    /**
     * A busz aktuális helyzetéből meghatározza a cél csomópontba vezető
     * legrövidebb útvonalat, és hozzárendeli azt a buszhoz.
     *
     * @param bus a mozgatandó busz
     * @param destination a célcsomópont
     * @return a művelet eredménye
     */
    public int assignRoute(Bus bus, Node destination) {
        return 0;
    }

    /**
     * A buszvezető szerepkör pontszámát adja vissza, ami a teljesített fordulók számán alapul.
     *
     * @return a szerepkör pontszáma
     */
    @Override
    public int getScore() {
        return 0;
    }
}
