package skeleton.src;

/**
 * Represents the state where the lane is covered in deep snow and is impassable.
 */
public class DeepSnow implements LaneState {

    @Override
    public boolean isPassable() {
        Skeleton.printCall("DeepSnow", "isPassable()");
        // A mély hóban elakadnak az autók, tehát ez false
        Skeleton.printReturn("false");
        return false; 
    }

    @Override
    public double getDynamicWeight() {
        Skeleton.printCall("DeepSnow", "getDynamicWeight()");
        // Nagyon nehéz (vagy lehetetlen) haladás, magas súly
        Skeleton.printReturn("10.0");
        return 10.0;
    }

    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        Skeleton.printCall("DeepSnow", "handleWeatherChange(snowAmount)");
        // Marad mély hó, vagy ha sokat esik, még mélyebb
        Skeleton.printReturn("this");
        return this;
    }
}