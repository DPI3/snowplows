package skeleton.src;

/**
 * Represents the state where the lane is covered in broken ice.
 */
public class Brokenice implements LaneState {

    @Override
    public boolean isPassable() {
        Skeleton.printCall("Brokenice", "isPassable()");
        // Itt kell egy return! Tegyük fel, hogy a tört jég alapból járható.
        Skeleton.printReturn("true");
        return true; 
    }

    @Override
    public double getDynamicWeight() {
        Skeleton.printCall("Brokenice", "getDynamicWeight()");
        // Itt is kell egy return! A tört jég nehezíti a haladást (pl. 2.0 súly).
        Skeleton.printReturn("2.0");
        return 2.0;
    }

    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        Skeleton.printCall("Brokenice", "handleWeatherChange(snowAmount)");
        // Itt is kell egy return! Visszaadjuk saját magát vagy egy új állapotot.
        Skeleton.printReturn("this");
        return this;
    }
}