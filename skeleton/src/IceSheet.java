package skeleton.src;

/**
 * Represents a lane covered in a sheet of ice.
 */
public class IceSheet implements LaneState {

    @Override
    public boolean isPassable() {
        Skeleton.printCall("IceSheet", "isPassable()");
        // A jégpáncél alapvetően járható, de veszélyes
        Skeleton.printReturn("true");
        return true; 
    }

    @Override
    public double getDynamicWeight() {
        Skeleton.printCall("IceSheet", "getDynamicWeight()");
        // A jég növeli az út "súlyát" (lassabb haladás), pl. 3.0
        Skeleton.printReturn("3.0");
        return 3.0;
    }

    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        Skeleton.printCall("IceSheet", "handleWeatherChange(snowAmount)");
        // Ha esik a hó a jégre, talán mély hó lesz belőle? 
        // Egyelőre maradjunk önmagánál:
        Skeleton.printReturn("this");
        return this;
    }
}