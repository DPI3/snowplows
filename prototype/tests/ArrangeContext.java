package tests;

import src.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Az arrange-parancsnyelv értelmezője és szcenárió-építője.
 *
 * Egy ArrangeContext példány egyetlen teszt kezdőállapotát építi fel az
 * {@link #execute(String)} sorozatos hívásával. Az építés végén a
 * {@link #dispatcher()} egy futás-idejű parancsfeldolgozót ad vissza, amely
 * automatikusan a megfelelő {@link TestSupport#dispatch} módba kapcsol
 * (game / ctx / store / sima) attól függően, hogy mi került felvételre.
 *
 * Az arrange-nyelv leírását lásd az {@link InteractiveRunner} osztály doc-jában.
 */
public final class ArrangeContext {

    public final TestContext ctx = new TestContext();

    private Snowplow plow;
    private CleanerRole cleaner;
    private Store store;

    private final Map<String, Node> nodes = new LinkedHashMap<>();

    private final Map<String, Role> rolesByName = new LinkedHashMap<>();

    private int gameCurrent = 0;
    private int gameMax = 0;
    private boolean gameInit = false;
    private final List<Vehicle> gameVehicles = new ArrayList<>();
    private final List<Player> gamePlayers = new ArrayList<>();

    private boolean usesCtx = false;


    /**
     * Egyetlen arrange-sor végrehajtása. Üres sor és komment-sor (#) no-op.
     * Hibás parancs / paraméter esetén IllegalArgumentException dobódik.
     */
    public void execute(String rawLine) {
        String line = rawLine;
        int hash = line.indexOf('#');
        if (hash >= 0) line = line.substring(0, hash);
        line = line.trim();
        if (line.isEmpty()) return;

        String[] tokens = tokenize(line);
        if (tokens.length == 0) return;
        String cmd = tokens[0].toLowerCase();
        Map<String, String> f = parseFlags(tokens, 1);

        switch (cmd) {
            case "add_lane":          doAddLane(f);          break;
            case "add_residence":     doAddResidence(f);     break;
            case "add_workplace":     doAddWorkplace(f);     break;
            case "add_terminal":      doAddTerminal(f);      break;
            case "add_plow":          doAddPlow(f);          break;
            case "add_car":           doAddCar(f);           break;
            case "add_bus":           doAddBus(f);           break;
            case "add_cleaner":       doAddCleaner(f);       break;
            case "add_busdriver":     doAddBusdriver(f);     break;
            case "add_role":          doAddRole(f);          break;
            case "add_route":         doAddRoute(f);         break;
            case "set_vehicle_route": doSetVehicleRoute(f);  break;
            case "add_store":         doAddStore();          break;
            case "set_bus_auto":      doSetBusAuto(f);       break;
            case "set_terminal_lane": doSetTerminalLane(f);  break;
            case "set_bus_arrival":   doSetBusArrival(f);    break;
            case "set_car_auto":      doSetCarAuto(f);       break;
            case "set_car_arrival":   doSetCarArrival(f);    break;
            case "set_car_reroute":   doSetCarReroute(f);    break;
            case "add_player":        doAddPlayer(f);        break;
            case "init_game":         doInitGame(f);         break;
            default:
                throw new IllegalArgumentException("Ismeretlen arrange parancs: " + cmd);
        }
    }

    /**
     * A felépített szcenárió fölött futó parancsfeldolgozó. Az aktuális
     * állapot alapján automatikusan választ a 4 dispatch-mód közül:
     *   - Game van  → TestSupport.dispatch(line, game)
     *   - ctx-specifikus → TestSupport.dispatch(line, ctx)
     *   - Store van → TestSupport.dispatch(line, plow, cleaner, lanes, store)
     *   - különben → TestSupport.dispatch(line, plow, cleaner, lanes)
     */
    public Consumer<String> dispatcher() {
        if (gameInit) {
            Game game = new Game(gameCurrent, gameMax, gameVehicles, gamePlayers);
            return line -> TestSupport.dispatch(line, game);
        }
        if (usesCtx) {
            final TestContext c = ctx;
            return line -> TestSupport.dispatch(line, c);
        }
        if (plow == null || cleaner == null) {
            throw new IllegalStateException(
                    "A szcenárióból hiányzik a plow vagy a cleaner — "
                  + "add_plow és add_cleaner kötelező a sima módhoz.");
        }
        if (store != null) {
            final Snowplow p = plow;
            final CleanerRole cl = cleaner;
            final Store s = store;
            return line -> TestSupport.dispatch(line, p, cl, ctx.lanes, s);
        }
        final Snowplow p = plow;
        final CleanerRole cl = cleaner;
        return line -> TestSupport.dispatch(line, p, cl, ctx.lanes);
    }


    private void doAddLane(Map<String, String> f) {
        String name = require(f, "n", "add_lane");
        Lane lane = new Lane(name, null, null);
        String state = f.get("state");
        if (state != null && !state.isEmpty()) lane.setState(TestSupport.createState(state));
        if (f.containsKey("snow"))   lane.setSnowThickness(parseDouble(f.get("snow")));
        if (f.containsKey("ice"))    lane.setIceThickness(parseDouble(f.get("ice")));
        if (f.containsKey("gravel")) lane.setGravelThickness(parseDouble(f.get("gravel")));
        ctx.lanes.put(name, lane);
    }

    private void doAddResidence(Map<String, String> f) {
        String name = require(f, "n", "add_residence");
        nodes.put(name, new Residence(name));
        usesCtx = true;
    }

    private void doAddWorkplace(Map<String, String> f) {
        String name = require(f, "n", "add_workplace");
        nodes.put(name, new Workplace(name));
        usesCtx = true;
    }

    private void doAddTerminal(Map<String, String> f) {
        String name = require(f, "n", "add_terminal");
        nodes.put(name, new Terminal(name));
        usesCtx = true;
    }


    private void doAddPlow(Map<String, String> f) {
        String name = require(f, "n", "add_plow");
        Lane lane = optLane(f.get("pos"));
        Head head = null;
        if (f.containsKey("head")) head = TestSupport.createHead(f.get("head"));
        Snowplow p = new Snowplow(name, lane, 0, head);
        if (f.containsKey("salt"))   p.setSaltStock(parseInt(f.get("salt")));
        if (f.containsKey("bio"))    p.setBiokeroseneStock(parseInt(f.get("bio")));
        if (f.containsKey("gravel")) p.setGravelStock(parseInt(f.get("gravel")));
        this.plow = p;
        ctx.plows.put(name, p);
        gameVehicles.add(p);
    }

    private void doAddCar(Map<String, String> f) {
        String name = require(f, "n", "add_car");
        Lane lane = optLane(f.get("pos"));
        double speed = optDouble(f, "speed", 0.0);
        Residence res = (Residence) optNode(f.get("residence"));
        Workplace  wrk = (Workplace) optNode(f.get("workplace"));
        Car car = new Car(name, lane, speed, res, wrk);
        ctx.cars.put(name, car);
        if (lane != null) ctx.setVehicleLane(name, lane.getName());
        ctx.defaultSpeed.put(name, speed);
        gameVehicles.add(car);
        usesCtx = true;
    }

    private void doAddBus(Map<String, String> f) {
        String name = require(f, "n", "add_bus");
        Lane lane = optLane(f.get("pos"));
        double speed = optDouble(f, "speed", 0.0);
        Terminal termA = (Terminal) optNode(f.get("termA"));
        Terminal termB = (Terminal) optNode(f.get("termB"));
        Bus bus = new Bus(name, lane, speed, termA, termB);
        ctx.buses.put(name, bus);
        if (lane != null) ctx.setVehicleLane(name, lane.getName());
        ctx.defaultSpeed.put(name, speed);
        ctx.vehicleRoute.put(name, null);
        if (f.containsKey("player")) ctx.playerBuses.add(name);
        gameVehicles.add(bus);
        usesCtx = true;
    }


    private void doAddCleaner(Map<String, String> f) {
        String name    = require(f, "n", "add_cleaner");
        int money      = parseInt(require(f, "money", "add_cleaner"));
        String plowRef = require(f, "plow", "add_cleaner");
        Snowplow p = ctx.plows.get(plowRef);
        if (p == null) throw new IllegalArgumentException("add_cleaner: ismeretlen plow: " + plowRef);
        CleanerRole c = new CleanerRole(name, money, p);
        this.cleaner = c;
        ctx.cleaners.put(name, c);
        rolesByName.put(name, c);
    }

    private void doAddBusdriver(Map<String, String> f) {
        String name   = require(f, "n", "add_busdriver");
        String busRef = require(f, "bus", "add_busdriver");
        int money     = optInt(f, "money", 0);
        int score     = optInt(f, "score", 0);
        int rounds    = optInt(f, "rounds", 0);
        Bus b = ctx.buses.get(busRef);
        if (b == null) throw new IllegalArgumentException("add_busdriver: ismeretlen bus: " + busRef);
        BusdriverRole d = new BusdriverRole(name, b, money, score);
        for (int i = 0; i < rounds; i++) d.incrementCompletedRounds();
        ctx.busdrivers.put(name, d);
        ctx.busToDriver.put(busRef, name);
        rolesByName.put(name, d);
        usesCtx = true;
    }

    private void doAddRole(Map<String, String> f) {
        String type = require(f, "type", "add_role").toLowerCase();
        switch (type) {
            case "cleaner":   doAddCleaner(f);   break;
            case "busdriver": doAddBusdriver(f); break;
            default: throw new IllegalArgumentException("add_role: ismeretlen szerep-típus: " + type);
        }
    }


    private void doAddRoute(Map<String, String> f) {
        String name = require(f, "n", "add_route");
        String lanesCsv = require(f, "lanes", "add_route");
        List<String> laneList = Arrays.asList(lanesCsv.split("\\s*,\\s*"));
        ctx.routeLanes.put(name, laneList);
        usesCtx = true;
    }

    private void doSetVehicleRoute(Map<String, String> f) {
        String vehicle = require(f, "vehicle", "set_vehicle_route");
        String route   = require(f, "route",   "set_vehicle_route");
        ctx.vehicleRoute.put(vehicle, route);
        usesCtx = true;
    }


    private void doAddStore() {
        this.store = new Store(new ArrayList<>());
    }


    private void doSetBusAuto(Map<String, String> f) {
        String bus   = require(f, "bus",   "set_bus_auto");
        String start = require(f, "start", "set_bus_auto");
        String dest  = require(f, "dest",  "set_bus_auto");
        String route = require(f, "route", "set_bus_auto");
        ctx.busAutoStart.put(bus, start);
        ctx.busAutoDest.put(bus, dest);
        ctx.busAutoRoute.put(bus, route);
        usesCtx = true;
    }

    private void doSetTerminalLane(Map<String, String> f) {
        String lane = require(f, "lane", "set_terminal_lane");
        String term = require(f, "terminal", "set_terminal_lane");
        ctx.terminalLane.put(lane, term);
        usesCtx = true;
    }

    private void doSetBusArrival(Map<String, String> f) {
        String bus = require(f, "bus", "set_bus_arrival");
        if (f.containsKey("reward"))      ctx.busArrivalReward.put(bus, parseInt(f.get("reward")));
        if (f.containsKey("incr_rounds")) ctx.busArrivalIncrRounds.add(bus);
        if (f.containsKey("show_reward")) ctx.busArrivalShowReward.add(bus);
        if (f.containsKey("next_route"))  ctx.busNextRoute.put(bus, f.get("next_route"));
        usesCtx = true;
    }


    private void doSetCarAuto(Map<String, String> f) {
        String car   = require(f, "car",   "set_car_auto");
        String route = require(f, "route", "set_car_auto");
        ctx.carAutoRoute.put(car, route);
        if (f.containsKey("residence")) ctx.carResidence.put(car, f.get("residence"));
        if (f.containsKey("workplace")) ctx.carWorkplace.put(car, f.get("workplace"));
        usesCtx = true;
    }

    private void doSetCarArrival(Map<String, String> f) {
        String lane = require(f, "lane", "set_car_arrival");
        String loc  = require(f, "location", "set_car_arrival");
        ctx.carArrivalLane.put(lane, loc);
        usesCtx = true;
    }

    private void doSetCarReroute(Map<String, String> f) {
        String car  = require(f, "car",  "set_car_reroute");
        String from = require(f, "from", "set_car_reroute");
        String to   = require(f, "to",   "set_car_reroute");
        Map<String, String> reroute = ctx.carReroute.computeIfAbsent(car, k -> new HashMap<>());
        reroute.put(from, to);
        usesCtx = true;
    }


    private void doAddPlayer(Map<String, String> f) {
        int id      = parseInt(require(f, "id", "add_player"));
        String name = require(f, "n", "add_player");
        String roleCsv = require(f, "roles", "add_player");
        List<Role> playerRoles = new ArrayList<>();
        for (String roleName : roleCsv.split("\\s*,\\s*")) {
            Role r = rolesByName.get(roleName);
            if (r == null) throw new IllegalArgumentException("add_player: ismeretlen szerep: " + roleName);
            playerRoles.add(r);
        }
        gamePlayers.add(new Player(id, name, playerRoles));
    }

    private void doInitGame(Map<String, String> f) {
        this.gameCurrent = parseInt(require(f, "current", "init_game"));
        this.gameMax     = parseInt(require(f, "max",     "init_game"));
        this.gameInit    = true;
    }


    private Lane optLane(String name) {
        if (name == null || name.isEmpty()) return null;
        Lane l = ctx.lanes.get(name);
        if (l == null) throw new IllegalArgumentException("Ismeretlen sáv: " + name);
        return l;
    }

    private Node optNode(String name) {
        if (name == null || name.isEmpty()) return null;
        Node n = nodes.get(name);
        if (n == null) throw new IllegalArgumentException("Ismeretlen csomópont: " + name);
        return n;
    }

    private static String require(Map<String, String> f, String key, String cmd) {
        String v = f.get(key);
        if (v == null || v.isEmpty()) {
            throw new IllegalArgumentException(cmd + ": hiányzó kötelező paraméter: -" + key);
        }
        return v;
    }

    private static int optInt(Map<String, String> f, String key, int def) {
        String v = f.get(key);
        return (v == null || v.isEmpty()) ? def : parseInt(v);
    }

    private static double optDouble(Map<String, String> f, String key, double def) {
        String v = f.get(key);
        return (v == null || v.isEmpty()) ? def : parseDouble(v);
    }

    private static int parseInt(String s) {
        try { return Integer.parseInt(s); }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Számot vártam, de ezt kaptam: " + s);
        }
    }

    private static double parseDouble(String s) {
        try { return Double.parseDouble(s); }
        catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Számot vártam, de ezt kaptam: " + s);
        }
    }


    private static String[] tokenize(String line) {
        List<String> out = new ArrayList<>();
        StringBuilder cur = new StringBuilder();
        boolean inQuote = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') { inQuote = !inQuote; continue; }
            if (!inQuote && Character.isWhitespace(c)) {
                if (cur.length() > 0) { out.add(cur.toString()); cur.setLength(0); }
            } else {
                cur.append(c);
            }
        }
        if (cur.length() > 0) out.add(cur.toString());
        return out.toArray(new String[0]);
    }

    /**
     * "-flag value" párokra bont, vagy "-flag" boolean-ként ha nincs értéke.
     * Negatív szám-értékeket kifejezetten szám-mintával felismerünk.
     */
    private static Map<String, String> parseFlags(String[] parts, int from) {
        Map<String, String> flags = new LinkedHashMap<>();
        for (int i = from; i < parts.length; i++) {
            String p = parts[i];
            if (p.startsWith("-") && p.length() > 1 && !isNumber(p)) {
                String key = p.substring(1);
                if (i + 1 < parts.length && (!parts[i + 1].startsWith("-") || isNumber(parts[i + 1]))) {
                    flags.put(key, parts[++i]);
                } else {
                    flags.put(key, "");
                }
            }
        }
        return flags;
    }

    private static boolean isNumber(String s) {
        if (s == null || s.isEmpty()) return false;
        try { Double.parseDouble(s); return true; }
        catch (NumberFormatException e) { return false; }
    }
}
