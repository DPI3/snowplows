package skeleton.src;

public interface LaneState{
    boolean isPassable();
    double getDynamicWeight();
    LaneState handleWeatherChange(int snowAmount); 
}