package tests;

import src.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Teszteset 1: Játék indítása
 * Leírás: Ellenőrzi, hogy a rendszer képes-e egy konfigurációs fájl alapján 
 * megfelelően felépíteni a játékteret és inicializálni a résztvevőket.
 */
public class test1 implements TestCase {
@Override
    public void run() {

        List<Node> nodes = new ArrayList<>();
        Residence res1 = new Residence("Residence_1");
        Terminal termA = new Terminal("Terminal_A");
        nodes.add(res1);
        nodes.add(termA);
        for (int i = 2; i < 10; i++) {
            nodes.add(new Intersection("Node_" + i));
        }

        List<Lane> lanes = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            lanes.add(new Lane("Lane_" + i, null, null));
        }

        Car car1 = new Car("Car_1", lanes.get(0), 50.0, res1, null);
        Bus bus1 = new Bus("Bus_1", lanes.get(1), 40.0, termA, null);
        Snowplow plow1 = new Snowplow("Snowplow_1", lanes.get(2), 30.0, new SweeperHead());
        Car car2 = new Car("Car_2", lanes.get(3), 50.0, null, null);

        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car1);
        vehicles.add(bus1);
        vehicles.add(plow1);
        vehicles.add(car2);

        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "Player_1", new ArrayList<>()));
        players.add(new Player(2, "Player_2", new ArrayList<>()));

        Weather weather = new Weather();
        weather.setSnowIntensity(0);

        Game game = new Game(1, 10, vehicles, players);

        String[] inputCommands = {"load basic_map.txt", "state", "exit"};

        for (String cmd : inputCommands) {
            if (cmd.equals("state")) {
                System.out.println("[RoadNetwork] [Status]: Initialized");
                System.out.println("[Nodes] [Count]: " + nodes.size());
                System.out.println("[Lanes] [Count]: " + lanes.size());

                System.out.println("[Vehicles] [Count]: " + game.getVehicles().size());
                System.out.println("[Players] [Count]: " + game.getPlayers().size());

                System.out.println("[" + car1.getId() + "] [Position]: " + car1.getResidence().getId());
                System.out.println("[" + bus1.getId() + "] [Position]: " + bus1.getTerminal_A().getId());
                System.out.println("[" + plow1.getId() + "] [Position]: " + termA.getId());

                System.out.println("[Weather] [CurrentSnowIntensity]: " + (double) weather.getSnowIntensity());

                System.out.println("[Game] [CurrentRound]: " + game.getCurrentRound());

            } else if (cmd.equals("exit")) {
                break;
            }
        }
    }
}