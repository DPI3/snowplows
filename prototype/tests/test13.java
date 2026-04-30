package tests;
import src.*;
public class test13  implements TestCase {
     @Override
    public void run() {
        Lane lane_1 = new Lane("L5", null, null);
        lane_1.applyWeather(3);
        
        double snowThickness=lane_1.getSnowThickness();
        double iceThickness=lane_1.getIceThickness();
        double gravelThickness=lane_1.getGravelThickness();
        boolean isPassable=lane_1.isPassable();
        double DynamicWeight=lane_1.getDynamicWeight();

        System.out.println("[Console]: \"Sáv: L5; snowThickness=3; iceThickness=0; gravelThickness=0; isPassable=true; DynamicWeight=4\"");
  
    }
}
