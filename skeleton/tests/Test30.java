package skeleton.tests;

import skeleton.src.*;

import java.util.Scanner;

/**
 * 30. teszteset: Ütközés: busz-busz teszt.
 * A teszteset ellenőrzi, hogy két busz ütközése esetén
 * mindkét busz mozgásképtelenné válik.
 */
public class Test30 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Előfeltétel: A és B busz csúszás miatt összeütköznek.
     * Az ütközés hatására a Lane hasAccident = true értéket kap,
     * állapota Impassable lesz, és mindkét busz immobileTime
     * értéket kap.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Előfeltétel: sáv létrehozása IceSheet állapottal (csúszós, ütközés lehetséges)
        Lane lane = new Lane();
        lane.setState(new IceSheet());

        // Két busz létrehozása ugyanazon a sávon
        Bus busA = new Bus();
        busA.setCurrentLane(lane);
        Bus busB = new Bus();
        busB.setCurrentLane(lane);

        // Mindkét busz mozog - ütközés detektálása move() közben történik
        busA.move();
        busB.move();

        // Assert: van-e baleset a sávon? (tesztelő 1-et ad meg: igen)
        boolean accident = lane.hasAccident(scanner);
        assert accident
                : "FAIL: Lane.hasAccident should be true after collision";

        // Assert: a sáv járhatatlanná vált? (tesztelő 2-t ad meg: nem járható)
        boolean passable = lane.isPassable();
        assert !passable
                : "FAIL: Lane should be impassable after collision";

        // Assert: mindkét busz mozgásképtelen lett
        assert busA.getImmobileTime() > 0
                : "FAIL: Bus A should have immobileTime > 0 after collision";
        assert busB.getImmobileTime() > 0
                : "FAIL: Bus B should have immobileTime > 0 after collision";

        System.out.println("[RESULT] Teszt sikeresen lefutott.");
    }
}