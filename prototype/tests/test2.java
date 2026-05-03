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

        String beforeLane      = plow1.getCurrentLane().getName();
        String beforeLaneState = lane12.getLaneState().getClass().getSimpleName();
        double beforeSnow      = lane12.getSnowThickness();
        int    beforeMoney     = cleaner.getMoney();

        plow1.setCurrentLane(lane12);
        cleaner.controlSnowplow(plow1);

        String afterLane      = plow1.getCurrentLane().getName();
        String afterLaneState = lane12.getLaneState().getClass().getSimpleName();
        double afterSnow      = lane12.getSnowThickness();
        int    afterMoney     = cleaner.getMoney();

        System.out.println("[" + plow1.getId() + "] [currentLane]: " + beforeLane + " -> " + afterLane);
        System.out.println("[" + lane12.getName() + "] [currentState]: " + beforeLaneState + " -> " + afterLaneState);
        System.out.println("[" + lane12.getName() + "] [snowThickness]: " + beforeSnow + " -> " + afterSnow);
        System.out.println("[" + cleaner.getName() + "] [money]: " + beforeMoney + " -> " + afterMoney);
    }
}
