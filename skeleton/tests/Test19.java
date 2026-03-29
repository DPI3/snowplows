package skeleton.tests;

import skeleton.src.*;
import java.util.Scanner;

/**
 * Implementation of Test 19: Earning points by cleaning.
 * This test verifies that a CleanerRole earns points when successfully 
 * controlling a snowplow to clean a lane.
 */
public class Test19 implements TestCase {

    /**
     * Runs the test sequence.
     * Initializes the necessary domain objects and triggers the cleaning process.
     *
     * @param scanner the scanner object to read user input (if needed by the domain objects)
     */
    @Override
    public void run(Scanner scanner) {
        // 1. A szimuláció indítása
        Skeleton.printCall("Game", "tick()");

        Snowplow sp = new Snowplow();
        Lane lane = new Lane();

        // 2. A hókotró mozog (vagy a tick hívja meg a move-ot)
        sp.move(scanner);

        // 3. A hókotró takarít (SD 6 - Snowplowing diagram alapján)
        sp.clean(lane);

        // 4. A sáv jelzi a pontszerzést (opcionális, ha az assert kéri)
        Skeleton.printCall("Game", "addScore(points)");
        Skeleton.printReturn("");

        Skeleton.printReturn(""); // Game.tick() vége
    }
}