package tests;

import src.*;

/**
 * Teszteset 2: Jármű sávváltásának és az út járhatóságának tesztelése.
 * Ellenőrzi, hogy a jármű át tud-e menni egy tiszta sávra, 
 * illetve megakadályozza-e a rendszer, hogy járhatatlan (DeepSnow) sávra lépjen.
 */
public class test2 implements TestCase {

   @Override
    public void run() {

        Terminal termA = new Terminal("Terminal_A");
        Lane laneTermA = new Lane("Terminal_A", null, null);
        Lane lane12 = new Lane("lane_12", termA, null);

        lane12.setState(new DeepSnow());
        lane12.setSnowThickness(5.0);

        Snowplow plow1 = new Snowplow("snowplow_1", laneTermA, 30.0, new ThrowerHead());
        CleanerRole cleaner = new CleanerRole("player_cleaner", 100, plow1);

        String beforeLane = plow1.getCurrentLane().getName();
        String beforeState = plow1.getState().name();
        int beforeFuel = plow1.getFuel();

        double beforeSnow = lane12.getSnowThickness();
        boolean beforePassable = lane12.isPassable();
        String beforeLaneState = lane12.getLaneState().getClass().getSimpleName();
        int beforePoints = cleaner.getScore();

        plow1.setCurrentLane(lane12);

        cleaner.controlSnowplow(plow1);

        String afterLane = plow1.getCurrentLane().getName();
        String afterState = plow1.getState().name();
        int afterFuel = plow1.getFuel();

        double afterSnow = lane12.getSnowThickness();
        boolean afterPassable = lane12.isPassable();
        String afterLaneState = lane12.getLaneState().getClass().getSimpleName();
        int afterPoints = cleaner.getScore();

        System.out.println("[" + plow1.getId() + "] [currentLane]: " + beforeLane + " -> " + afterLane);
        //System.out.println("[" + plow1.getId() + "] [state]: " + beforeState + " -> " + afterState); 
        //System.out.println("[" + plow1.getId() + "] [fuel]: " + beforeFuel + " -> " + afterFuel); 
        //System.out.println();
        
        
        System.out.println("[" + lane12.getName() + "] [currentState]: " + beforeLaneState + " -> " + afterLaneState);
        //System.out.println("[" + lane12.getName() + "] [isPassable]: " + beforePassable + " -> " + afterPassable);
        //System.out.println();
        System.out.println("[" + lane12.getName() + "] [snowThickness]: " + beforeSnow + " -> " + afterSnow);
        System.out.println("[" + cleaner.getName() + "] [money]: " + beforePoints + " -> " + afterPoints);
    }
}