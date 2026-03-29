package skeleton.tests;

import skeleton.src.*;

import java.util.Scanner;

/**
 * 28. teszteset: Ütközés: autó-autó teszt.
 * A teszteset ellenőrzi, hogy két autó ütközése esetén a Lane
 * járhatatlanná válik, és mindkét autó megáll.
 */
public class Test28 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Előfeltétel: A és B autó csúszás miatt összeütköznek.
     * Az ütközés hatására a Lane hasAccident = true értéket kap,
     * állapota Impassable lesz, és mindkét autó megáll.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        // Előfeltétel: sáv létrehozása IceSheet állapottal (csúszós, ütközés lehetséges)
        Lane lane = new Lane();
        lane.setState(new IceSheet());

        // Két autó létrehozása ugyanazon a sávon
        Car carA = new Car();
        Car carB = new Car();

        // Mindkét autó mozog - ütközés detektálása move() közben történik
        carA.move();
        carB.move();

        // Assert: van-e baleset a sávon? (tesztelő 1-et ad meg: igen)
        boolean accident = lane.hasAccident(scanner);
        assert accident
                : "FAIL: Lane.hasAccident should be true after collision";

        // Assert: a sáv járhatatlanná vált? (tesztelő 2-t ad meg: nem járható)
        boolean passable = lane.isPassable();
        assert !passable
                : "FAIL: Lane should be impassable after collision";

        System.out.println("[RESULT] Teszt sikeresen lefutott.");
    }
}