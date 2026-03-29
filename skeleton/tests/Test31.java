package skeleton.tests;

import skeleton.src.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 31. teszteset: Busz mozgásképtelenség megszűnése teszt.
 * A teszteset ellenőrzi, hogy egy balesetet szenvedett busz
 * a megadott idő letelte után újra képes-e elindulni.
 */
public class Test31 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Előfeltétel: A Bus immobileTime értéke nagyobb mint 0
     * egy korábbi ütközés miatt.
     * A busz nem mozdul, de az immobileTime csökken tickenként,
     * amíg el nem éri a 0-t, majd újra elindul.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Előfeltétel: busz létrehozása immobileTime > 0 értékkel
        Bus bus = new Bus("bus1", null, 0.0, 0.0, null, null, 3, null);

        // Járművek és játékosok listájának összeállítása
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(bus);
        List<Player> players = new ArrayList<>();

        // Game létrehozása
        Game game = new Game(0, 10, vehicles, players);

        // 1. tick - immobileTime csökken, busz nem mozdul
        game.tick();

        // Assert: busz még mozgásképtelen
        assert bus.getImmobileTime() > 0
                : "FAIL: Bus should still be immobile after first tick";

        // 2. tick - immobileTime csökken, busz nem mozdul
        game.tick();

        // Assert: busz még mozgásképtelen
        assert bus.getImmobileTime() > 0
                : "FAIL: Bus should still be immobile after second tick";

        // 3. tick - immobileTime eléri a 0-t, busz újra mozog
        game.tick();

        // Assert: busz már mozoghat
        assert bus.getImmobileTime() == 0
                : "FAIL: Bus immobileTime should be 0 after third tick";
    }
}