package tests;
import src.*;


public class test15 implements TestCase{
     @Override
    public void run() {
        Snowplow plow_1= new Snowplow("plow_1", new Lane("lane_1", null, null), 0, new ThrowerHead());
        Lane lane_5=new Lane("lane_5", null, null);
        lane_5.applyWeather(3);
        lane_5.setState(new ThinSnow());
        
        plow_1.changeHead(new SweeperHead());
        System.out.println("[plow_1] [currentHead]: ThrowerHead -> SweeperHead");
        
        plow_1.changeLane(lane_5);
        System.out.println("[plow_1] [currentLane]: lane_1 -> lane_5");

        plow_1.clean(plow_1.getCurrentLane());
        System.out.println("[lane_5] [currentState]: ThinSnow -> Clear");
        System.out.println("[lane_5] [snowThickness]: 3.0 -> 0.0");
        System.out.println("[cleaner_1] [money]: 100 -> 150");

    }
}
