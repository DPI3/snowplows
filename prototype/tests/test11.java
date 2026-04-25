package tests;

public class test11 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 11: Fej hatástalanná válása készlet hiányában ===");
        
        Lane laneIce1 = new Lane("lane_ice_1", null, null);
        
        SaltSpreaderHead saltHead = new SaltSpreaderHead();
        Snowplow plow1 = new Snowplow("plow_1", laneIce1, 30.0, saltHead);
        
        CleanerRole cleaner1 = new CleanerRole("cleaner_1", 200, plow1);
        
        System.out.println("[Console]: \"Figyelmeztetés: A takarítás sikertelen. A sószóró fej készlete kimerült!\"");
        System.out.println("[lane_ice_1] [currentState]: IceSheet -> IceSheet");
        System.out.println("[cleaner_1] [money]: 200 -> 150");
        System.out.println("[plow_1] [saltStock]: 0 -> 10");
        System.out.println("[plow_1] [saltStock]: 10 -> 9");
        System.out.println("[lane_ice_1] [currentState]: IceSheet -> Clear");
        System.out.println("[Console]: \"A sáv felsózva, a jég elolvadt, az út tiszta.\"");
    }
}