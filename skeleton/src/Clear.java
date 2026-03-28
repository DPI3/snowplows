package skeleton.src;

/**
 * Represents the clear, snow-free state of a lane.
 */
public class Clear implements LaneState {
    @Override
    public boolean isPassable() {
        Skeleton.printCall("Clear", "isPassable()");
        Skeleton.printReturn("true");
        return true;
    }

    @Override
    public double getDynamicWeight() {
        return 1.0;
    }

    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        return this;
    }
}