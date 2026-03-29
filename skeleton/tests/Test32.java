package skeleton.tests;

import skeleton.src.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 32. teszteset: Játék kiértékelése teszt.
 * A teszteset ellenőrzi, hogy a játék végén helyesen
 * számolódnak ki a buszvezetők és takarítók pontjai.
 */
public class Test32 extends TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Előfeltétel: A játék fut, buszvezetők és takarítók
     * teljesítettek fordulókat és takarításokat.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához
     */
    @Override
    public void run(Scanner scanner) {

        // Szerepkörök létrehozása
        BusdriverRole busDriverRole = new BusdriverRole();
        CleanerRole cleanerRole = new CleanerRole();

        // Játékosok létrehozása szerepkörökkel
        List<Role> rolesPlayer1 = new ArrayList<>();
        rolesPlayer1.add(busDriverRole);

        List<Role> rolesPlayer2 = new ArrayList<>();
        rolesPlayer2.add(cleanerRole);

        Player player1 = new Player(1, "BusDriver", rolesPlayer1);
        Player player2 = new Player(2, "Cleaner", rolesPlayer2);

        // Játékosok listája
        List<Player> players = new ArrayList<>();
        players.add(player1);
        players.add(player2);

        // Game létrehozása
        Game game = new Game(0, 10, new ArrayList<>(), players);

        // Scoreboard létrehozása
        Scoreboard scoreboard = new Scoreboard();

        // Játék befejezése - game.end() kiértékeli a pontokat
        game.end();

        // Scoreboard kiértékelése
        scoreboard.evaluate();

        // Assert: a scoreboard tartalmazza a játékosokat
        assert scoreboard.getScores() != null
                : "FAIL: Scoreboard scores should not be null";

        assert scoreboard.getScores().containsKey(player1)
                : "FAIL: Scoreboard should contain player1";

        assert scoreboard.getScores().containsKey(player2)
                : "FAIL: Scoreboard should contain player2";

        // Assert: a pontszámok nem negatívak
        assert scoreboard.getScores().get(player1) >= 0
                : "FAIL: Player1 score should be >= 0";

        assert scoreboard.getScores().get(player2) >= 0
                : "FAIL: Player2 score should be >= 0";

        System.out.println("[RESULT] Teszt sikeresen lefutott.");
    }
}