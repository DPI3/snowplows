package skeleton.src;

public class ThinSnow implements LaneState {
    @Override
    public boolean isPassable() {
        Skeleton.printCall("ThinSnow", "isPassable()");
        Skeleton.printReturn("true");
        return true; 
    }

    @Override
    public double getDynamicWeight() {
        Skeleton.printCall("ThinSnow", "getDynamicWeight()");
        Skeleton.printReturn("1.5");
        return 1.5;
    }

    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        Skeleton.printCall("ThinSnow", "handleWeatherChange(snowAmount)");
        Skeleton.printReturn("this");
        return this;
    }
}