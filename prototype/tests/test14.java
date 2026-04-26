package tests;
import src.*;


public class test14 implements TestCase{
     @Override
    public void run() {
        Snowplow plow_1= new Snowplow("plow_1", new Lane("lane_start", null, null), 0, new SweeperHead());
        Lane lane_ice2=new Lane("lane_ice2", null, null);
        lane_ice2.setState(new IceSheet());
        
        plow_1.changeHead(new GravelSpreaderHead());
        System.out.println("[plow_1] [currentHead]: SweeperHead -> GravelSpreaderHead");
        
        plow_1.changeLane(lane_ice2);
        System.out.println("[plow_1] [currentLane]: lane_start -> lane_ice2");

        plow_1.clean(plow_1.getCurrentLane());
        System.out.println("[plow_1] [gravelStock ]: 10 -> 9");
        System.out.println("[lane_ice2] [currentState]: IceSheet -> Gravel");
        System.out.println("[lane_ice2] [gravelThickness]: 0.0 -> 1.0");
        System.out.println("[cleaner_1] [money]: 100 -> 150");

    }
}
