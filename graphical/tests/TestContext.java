package tests;

import src.*;
import java.util.*;

/**
 * Teszt-környezet kontextus-tartó a 5–10. tesztekhez.
 * Minden teszt létrehozza a saját példányát és feltölti.
 */
public class TestContext {

    public final Map<String, Lane>          lanes      = new LinkedHashMap<>();
    public final Map<String, Route>         routes     = new LinkedHashMap<>();
    public final Map<String, Car>           cars       = new LinkedHashMap<>();
    public final Map<String, Bus>           buses      = new LinkedHashMap<>();
    public final Map<String, BusdriverRole> busdrivers = new LinkedHashMap<>();
    public final Map<String, Snowplow>      plows      = new LinkedHashMap<>();
    public final Map<String, CleanerRole>   cleaners   = new LinkedHashMap<>();

    public String selectedVehicle = null;

    public final Map<String, List<String>>  laneOccupants = new LinkedHashMap<>();

    public final Map<String, String>        vehicleLane   = new LinkedHashMap<>();

    public final Map<String, String>        vehicleRoute  = new LinkedHashMap<>();

    public final Map<String, List<String>>  routeLanes    = new LinkedHashMap<>();

    public final Map<String, String>        terminalLane  = new LinkedHashMap<>();

    public final Map<String, String>        busToDriver   = new LinkedHashMap<>();

    public final Map<String, Integer>       busArrivalReward = new LinkedHashMap<>();

    public final Set<String>               busArrivalIncrRounds = new LinkedHashSet<>();

    public final Set<String>               busArrivalShowReward = new LinkedHashSet<>();

    public final Map<String, String>        busNextRoute  = new LinkedHashMap<>();

    public final Map<String, Double>        defaultSpeed  = new LinkedHashMap<>();

    public final Set<String>               stuckVehicles = new LinkedHashSet<>();

    public final Map<String, String>        busAutoStart  = new LinkedHashMap<>();
    public final Map<String, String>        busAutoDest   = new LinkedHashMap<>();
    public final Map<String, String>        busAutoRoute  = new LinkedHashMap<>();

    public final Map<String, String>        carAutoRoute  = new LinkedHashMap<>();

    public final Map<String, String>        carResidence  = new LinkedHashMap<>();
    public final Map<String, String>        carWorkplace  = new LinkedHashMap<>();

    public final Map<String, String>        carArrivalLane = new LinkedHashMap<>();

    public final Set<String>               playerBuses   = new LinkedHashSet<>();

    public final Map<String, Map<String,String>> carReroute = new LinkedHashMap<>();


    /** Jármű áthelyezése sávra (laneOccupants és vehicleLane frissítése). */
    public void setVehicleLane(String vehicleId, String laneName) {
        String old = vehicleLane.get(vehicleId);
        if (old != null) {
            List<String> occ = laneOccupants.get(old);
            if (occ != null) occ.remove(vehicleId);
        }
        vehicleLane.put(vehicleId, laneName);
        if (laneName != null) {
            laneOccupants.computeIfAbsent(laneName, k -> new ArrayList<>()).add(vehicleId);
        }
    }

    /** Többi jármű a sávon (adott jármű nélkül). */
    public List<String> getOtherOccupants(String laneName, String excludeId) {
        List<String> all = laneOccupants.getOrDefault(laneName, Collections.emptyList());
        List<String> result = new ArrayList<>(all);
        result.remove(excludeId);
        return result;
    }

    /** Következő sáv neve az útvonalban az aktuális sáv után. */
    public String getNextLaneName(String routeName, String currentLaneName) {
        List<String> list = routeLanes.get(routeName);
        if (list == null) return null;
        int idx = list.indexOf(currentLaneName);
        if (idx == -1 || idx == list.size() - 1) return null;
        return list.get(idx + 1);
    }

    /** Jármű (Car vagy Bus) lekérése id alapján. */
    public Vehicle getVehicle(String id) {
        if (cars.containsKey(id))  return cars.get(id);
        if (buses.containsKey(id)) return buses.get(id);
        return null;
    }
}
