package skeleton.src;

/**
 * A BusdriverRole a buszvezető szerepkört reprezentálja.
 * Felelős a buszok mozgatásáért, útvonalak megtervezéséért
 * és a fordulók teljesítéséért két végállomás között.
 */
public class BusdriverRole extends Role {

    /** A buszvezető által teljesített fordulók száma. */
    private int completedRounds;

    public BusdriverRole() {
        Skeleton.printCall("BusdriverRole", "BusdriverRole()");
        Bus bus= new Bus();
        Skeleton.printReturn("");
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
        Skeleton.printCall("BusdriverRole", "assignRoute(bus, destination)");

        Skeleton.printReturn("");
        return 0;
    }

    /**
     * A buszvezető szerepkör pontszámát adja vissza, ami a teljesített fordulók számán alapul.
     *
     * @return a szerepkör pontszáma
     */
    @Override
    public int getScore() {
        Skeleton.printCall("BusdriverRole", "getScore()");
        Skeleton.printReturn("busDriverScore");
        return completedRounds*10; //ideiglenes
    }
}
