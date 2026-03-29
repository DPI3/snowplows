package skeleton.tests;

import skeleton.src.*;

import java.util.Scanner;

/**
 * 29. teszteset: Ütközés: autó-busz teszt.
 * A teszteset ellenőrzi, hogy autó és busz ütközése esetén
 * az autó megáll, a busz pedig mozgásképtelenné válik.
 */
public class Test29 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Előfeltétel: A autó és B busz csúszás miatt összeütköznek.
     * Az ütközés hatására a Lane hasAccident = true értéket kap,
     * állapota Impassable lesz, az autó megáll, a busz
     * immobileTime értéket kap.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        // Előfeltétel: sáv létrehozása IceSheet állapottal (csúszós, ütközés lehetséges)
        Lane lane = new Lane();
        lane.setState(new IceSheet());

        // Autó és busz létrehozása ugyanazon a sávon
        Car carA = new Car();
        Bus busB = new Bus();

        // Mindkét jármű mozog - ütközés detektálása move() közben történik
        carA.move();
        busB.move();

        // Assert: van-e baleset a sávon? (tesztelő 1-et ad meg: igen)
        boolean accident = lane.hasAccident(scanner);
        assert accident
                : "FAIL: Lane.hasAccident should be true after collision";

        // Assert: a sáv járhatatlanná vált? (tesztelő 2-t ad meg: nem járható)
        boolean passable = lane.isPassable();
        assert !passable
                : "FAIL: Lane should be impassable after collision";

        // Assert: a busz mozgásképtelen lett
        assert busB.getImmobileTime() > 0
                : "FAIL: Bus B should have immobileTime > 0 after collision";
    }
}