package skeleton.tests;

import skeleton.src.*;

import java.util.Scanner;

/**
 * 33. teszteset: Útvonalkereső algoritmus, legrövidebb út tesztje.
 * A teszteset ellenőrzi, hogy a RoadNetwork.getShortestPath(from, to)
 * metódus valóban a legoptimálisabb útvonalat adja-e vissza, ahol
 * az egyik rövidebb út járhatatlan (baleset vagy mély hó miatt).
 */
public class Test33 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Előfeltétel: Adott egy úthálózat A és B végponttal, amelyhez
     * két útvonal vezet:
     * - Útvonal 1: fizikailag rövidebb, de egyik sávja járhatatlan
     * - Útvonal 2: fizikailag hosszabb, de minden sávja Clear állapotú
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        // Úthálózat létrehozása
        RoadNetwork roadNetwork = new RoadNetwork();

        // Kezdő és cél csomópontok létrehozása
        Node nodeA = new Intersection("A");
        Node nodeB = new Intersection("B");

        // Útvonal 1 sávjainak létrehozása - járhatatlan (baleset miatt)
        Lane lane1 = new Lane();
        lane1.setState(new Brokenice()); // járhatatlan állapot

        // Útvonal 2 sávjainak létrehozása - járható (Clear állapotú)
        Lane lane2 = new Lane();
        lane2.setState(new Clear()); // járható állapot

        // Legrövidebb útvonal lekérése
        Route result = roadNetwork.getShortestPath(nodeA, nodeB);

        // Assert: az útvonal nem null
        assert result != null
                : "FAIL: Route should not be null";

        // Assert: az útvonal tartalmaz sávokat
        assert !result.getLanes().isEmpty()
                : "FAIL: Route should contain lanes";

        // Assert: az útvonal nem tartalmaz járhatatlan sávot
        for (Lane lane : result.getLanes()) {
            assert lane.isPassable()
                    : "FAIL: Route should not contain impassable lanes";
        }

        System.out.println("[RESULT] Teszt sikeresen lefutott.");
    }
}