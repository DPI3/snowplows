package tests;

import src.*;
import java.util.*;

/**
 * Teszt-környezet kontextus-tartó a 5–10. tesztekhez.
 * Minden teszt létrehozza a saját példányát és feltölti.
 */
public class TestContext {

    // Névvel azonosított entitások
    public final Map<String, Lane>          lanes      = new LinkedHashMap<>();
    public final Map<String, Route>         routes     = new LinkedHashMap<>();
    public final Map<String, Car>           cars       = new LinkedHashMap<>();
    public final Map<String, Bus>           buses      = new LinkedHashMap<>();
    public final Map<String, BusdriverRole> busdrivers = new LinkedHashMap<>();
    public final Map<String, Snowplow>      plows      = new LinkedHashMap<>();
    public final Map<String, CleanerRole>   cleaners   = new LinkedHashMap<>();

    // kijelol parancs állapota
    public String selectedVehicle = null;

    // Sávon tartózkodó járművek: lane name → lista vehicle id-kből
    public final Map<String, List<String>>  laneOccupants = new LinkedHashMap<>();

    // Aktuális sáv járművenként: vehicle id → lane name
    public final Map<String, String>        vehicleLane   = new LinkedHashMap<>();

    // Aktuális útvonal járművenként: vehicle id → route name (null = nincs)
    public final Map<String, String>        vehicleRoute  = new LinkedHashMap<>();

    // Útvonal sávlistája: route name → sávok neve sorrendben
    public final Map<String, List<String>>  routeLanes    = new LinkedHashMap<>();

    // Terminál-sáv: lane name → terminál neve (megérkezési esemény)
    public final Map<String, String>        terminalLane  = new LinkedHashMap<>();

    // Busz → buszvezető kapcsolat: bus id → busdriver id
    public final Map<String, String>        busToDriver   = new LinkedHashMap<>();

    // Megérkezéskor fizetendő jutalom (bus id → összeg)
    public final Map<String, Integer>       busArrivalReward = new LinkedHashMap<>();

    // Megérkezéskor completedRounds növelendő (bus id-k halmaza)
    public final Set<String>               busArrivalIncrRounds = new LinkedHashSet<>();

    // Megérkezéskor jutalommal együtt írandó log (bus id-k halmaza)
    // ha igaz: "Forduló teljesítve: ..., jutalom: N"
    // ha hamis: "Forduló befejezve: ..."
    public final Set<String>               busArrivalShowReward = new LinkedHashSet<>();

    // Következő útvonal terminál-megérkezés után: bus id → következő route name
    public final Map<String, String>        busNextRoute  = new LinkedHashMap<>();

    // Alapsebesség járművenként (visszaállításhoz elakadás után): vehicle id → sebesség
    public final Map<String, Double>        defaultSpeed  = new LinkedHashMap<>();

    // Elakadt járművek halmaza
    public final Set<String>               stuckVehicles = new LinkedHashSet<>();

    // Auto-útvonalas busz: bus id → {start terminál, célterminál, route name}
    public final Map<String, String>        busAutoStart  = new LinkedHashMap<>();
    public final Map<String, String>        busAutoDest   = new LinkedHashMap<>();
    public final Map<String, String>        busAutoRoute  = new LinkedHashMap<>();

    // Autó auto-útvonal: car id → route name (tick közben rendelődik hozzá)
    public final Map<String, String>        carAutoRoute  = new LinkedHashMap<>();

    // Autó lakóhely/munkahely megjelenítési neve
    public final Map<String, String>        carResidence  = new LinkedHashMap<>();
    public final Map<String, String>        carWorkplace  = new LinkedHashMap<>();

    // Megérkezési sáv autókhoz: lane name → helynév (pl. work_1)
    public final Map<String, String>        carArrivalLane = new LinkedHashMap<>();

    // Játékos buszok (bus id): ütközés esetén büntetés
    public final Set<String>               playerBuses   = new LinkedHashSet<>();

    // Átirányítás: car id → (régi route name → új route name)
    public final Map<String, Map<String,String>> carReroute = new LinkedHashMap<>();

    // ---- Segédmetódusok ----

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
