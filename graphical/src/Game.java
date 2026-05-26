package src;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * A Game osztály a szimuláció központi vezérlője.
 * Feladata a teljes játékmenet koordinálása, a körök számának kezelése,
 * valamint a játékban szereplő járművek és játékosok nyilvántartása.
 */
public class Game {

    /** Az aktuális szimulációs kör sorszáma. */
    private int currentRound;

    /** Az aktuális körön belüli tick szám. */
    private int ticksInCurrentRound;

    /** Egy kör hány tickből áll. */
    private int TICKS_PER_ROUND = 10;

    /** A játék maximális időtartama körökben. */
    private int maxRound;

    /** A rendszerben lévő járművek listája. */
    private List<Vehicle> vehicles;

    /** A játékban résztvevő játékosok listája. */
    private List<Player> players;

    private int currentPlayerId=0;

    /** Az aktuális időjárás. */
    private Weather weather;

    /** Az úthálózat. */
    private RoadNetwork roadNetwork;

    /** A bolt. */
    private Store store;

    /**
     * Létrehoz egy Game objektumot a szükséges kapcsolatokkal és kezdőértékekkel.
     */
    public Game(int currentRound, int maxRound, List<Vehicle> vehicles, List<Player> players) {
        this.currentRound = currentRound;
        this.maxRound = maxRound;
        this.vehicles = vehicles;
        this.players = players;
        this.ticksInCurrentRound = 0;
    }

    /**
     * Paraméter nélküli konstruktor alapértelmezett értékekkel.
     */
    public Game() {
        this.currentRound = 0;
        this.maxRound = 10;
        this.vehicles = new ArrayList<>();
        this.players = new ArrayList<>();
        this.ticksInCurrentRound = 0;
    }

    /**
     * Egy egységgel előre lépteti a játékállapotot és frissíti a belső logikát.
     */
    public void tick() {
        ticksInCurrentRound++;

        if (ticksInCurrentRound >= TICKS_PER_ROUND) {
            ticksInCurrentRound = 0;
            currentRound++;
        }

        for (Vehicle v : vehicles) {
            v.tick();
        }

        checkCollisions();

        if (weather != null) {
            weather.tick();
        }


    }

    /**
     * Megvizsgálja, hogy a szimuláció elérte-e a maximális kört, vagy véget ért-e.
     * 
     * @return true, ha a játék véget ért; false, ha a játék nem ért véget
     */
    public boolean isOver() {
        return currentRound >= maxRound; 
    }

    /**
     * Lezárja a játékot.
     */
    public void end() {
    }

    /**
     * Az aktuális kör számát adja vissza, teszteléshez szükséges.
     * 
     * @return aktuális kör száma
     */
    public int getCurrentRound(){
        return currentRound;
    }

    /**
     * A játékosok listáját adja vissza, teszteléshez szükséges.
     * 
     * @return játékosok listája
     */
    public List<Player> getPlayers(){
        return players;
    }

    /**
     * Visszaadja a maximális körök számát.
     * 
     * @return a maximális körök száma
     */
    public int getMaxRound() {
        return maxRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public void setMaxRound(int maxRound) {
        this.maxRound = maxRound;
    }

    /**
     * Visszaadja a szimulációban résztvevő járművek listáját.
     * 
     * @return a szimulációban résztvevő járművek listája
     */
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    /**
     * Visszaadja az első Snowplow típusú járművet.
     *
     * @return Snowplow objektum vagy null
     */
    public Snowplow getSnowplow() {
        for (Vehicle v : vehicles) {
            if (v instanceof Snowplow) {
                return (Snowplow) v;
            }
        }
        return null;
    }

    /**
     * Visszaadja az első Bus típusú járművet.
     *
     * @return Bus objektum vagy null
     */
    public Bus getBus() {
        for (Vehicle v : vehicles) {
            if (v instanceof Bus) {
                return (Bus) v;
            }
        }
        return null;
    }

    /**
     * Visszaadja az első játékost.
     *
     * @return Player objektum vagy null
     */
    public Player getPlayer() {
        if (players.isEmpty()) {
            return null;
        }

        return players.get(currentPlayerId);
    }

    public void setPlayerCount(int count) {
        if(count < 1 || count > 5) return;
        
        while (players.size() < count) {
            CleanerRole cleanerRole = new CleanerRole("Cleaner"+players.size()+1, 2000, new Snowplow("snowplow"+players.size()+1,null,0, new ThrowerHead()));
            BusdriverRole busdriverRole= new BusdriverRole("Busdriver"+players.size()+1, new Bus("bus"+players.size()+1, null, 0, null, null), 1000,0);
            Player player1 = new Player(1, "Player1", cleanerRole, busdriverRole);
            players.add(player1);
            vehicles.add(cleanerRole.getSnowplow());
        }
        while (players.size() > count) {
            players.remove(players.size() - 1);
        }
    }

    /**
     * Visszaadja az aktuális kör számát.
     *
     * @return aktuális kör
     */
    public int getRound() {
        return currentRound;
    }

    /**
     * Visszaadja az összes Car típusú járművet.
     *
     * @return autók listája
     */
    public List<Car> getCars() {
        return vehicles.stream()
                .filter(v -> v instanceof Car)
                .map(v -> (Car) v)
                .collect(Collectors.toList());
    }

    /**
     * Visszaadja az úthálózatot.
     *
     * @return az úthálózat
     */
    public RoadNetwork getRoadNetwork() {
        return roadNetwork;
    }

    /**
     * Visszaadja a boltot.
     *
     * @return a bolt
     */
    public Store getStore() {
        return store;
    }

    /**
     * Visszaadja a játékidőt szöveges formában.
     *
     * @return formázott időszöveg
     */
    public String getFormattedTime() {
        return currentRound + ". kör (" + ticksInCurrentRound + "/" + TICKS_PER_ROUND + ")";
    }

    /**
     * Visszaadja az aktuálisan vezérelt jármű nevét.
     *
     * @return a jármű neve vagy üres szöveg
     */
    public String getCurrentControlledVehicleName() {
        Player player = getPlayer();
        if (player == null || player.getCurrentRole() == null) return "";

        Role role = player.getCurrentRole();
        if (role instanceof CleanerRole) {
            Snowplow sp = getSnowplow();
            return sp != null ? sp.getId() : "";
        } else if (role instanceof BusdriverRole) {
            Bus bus = getBus();
            return bus != null ? bus.getId() : "";
        }
        return "";
    }

    /**
     * Ellenőrzi és végrehajtja a szerepváltást a játékosok között.
     */
    public void checkRoleSwitch(){
        if(players.size() < 2) return;
        if(players.get(currentPlayerId).getCurrentRole() instanceof CleanerRole){
            players.get(currentPlayerId).setCurrentRole(players.get(currentPlayerId).getBusdriverRole());
        } else {
             currentPlayerId = (currentPlayerId + 1) % players.size();
            players.get(currentPlayerId).setCurrentRole(players.get(currentPlayerId).getCleanerRole());
        }
    }
    

    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
    }

    public void setRoadNetwork(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    private void checkCollisions() {
        for (int i = 0; i < vehicles.size(); i++) {
            for (int j = i + 1; j < vehicles.size(); j++) {
                Vehicle a = vehicles.get(i);
                Vehicle b = vehicles.get(j);

                if (a.getCurrentLane() == null || b.getCurrentLane() == null) continue;

                if (a.getCurrentLane() == b.getCurrentLane()) {
                    Lane lane = a.getCurrentLane();

                    if (a instanceof Car && b instanceof Car) {
                        lane.setHasAccident(true);
                    }

                    if (a instanceof Bus) {
                        ((Bus) a).disableForTicks(3);
                    }

                    if (b instanceof Bus) {
                        ((Bus) b).disableForTicks(3);
                    }
                }
            }
        }
    }
}