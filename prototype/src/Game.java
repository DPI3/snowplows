package prototype.src;

import java.util.ArrayList;
import java.util.List;

/**
 * A Game osztály a szimuláció központi vezérlője.
 */
public class Game {

    private int currentRound;
    private int maxRound;
    private boolean finished;

    private List<Vehicle> vehicles;
    private List<Player> players;

    public Game() {
        this.currentRound = 0;
        this.maxRound = 10;
        this.finished = false;
        this.vehicles = new ArrayList<>();
        this.players = new ArrayList<>();
    }

    public Game(int currentRound, int maxRound, List<Vehicle> vehicles, List<Player> players) {
        this.currentRound = currentRound;
        this.maxRound = maxRound;
        this.finished = false;
        this.vehicles = vehicles != null ? vehicles : new ArrayList<>();
        this.players = players != null ? players : new ArrayList<>();
    }

    public void tick() {
        if (finished) {
            return;
        }

        currentRound++;

        for (Vehicle vehicle : vehicles) {
            if (vehicle != null) {
                vehicle.tick();
            }
        }

        if (currentRound >= maxRound) {
            end();
        }
    }

    public boolean isOver() {
        return finished || currentRound >= maxRound;
    }

    public void end() {
        finished = true;
    }

    public void addVehicle(Vehicle vehicle) {
        if (vehicle != null) {
            vehicles.add(vehicle);
        }
    }

    public void addPlayer(Player player) {
        if (player != null) {
            players.add(player);
        }
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public int getMaxRound() {
        return maxRound;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Player> getPlayers() {
        return players;
    }
}