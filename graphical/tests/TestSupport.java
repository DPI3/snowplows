package tests;

import src.*;
import java.util.Map;


import java.util.List;
import java.util.ArrayList;

/**
 * Közös segédosztály minden teszthez.
 *
 * KÉT dispatch változat létezik:
 *
 *  1) dispatch(line, plow, cleaner, lanes)
 *     – test14–19 használják
 *     – a takarit parancs CleanerRole.controlSnowplow()-t hív
 *       (pénz-jutalom is megjelenik a kimenetben)
 *
 *  2) dispatch(line, plow, cleaner, lanes, store)
 *     – test4, test11 használják
 *     – a takarit parancs közvetlenül plow.clean()-t hív
 *       (pénz-jutalom NEM jelenik meg)
 *     – kezeli a bolt-parancsokat: bolt_nyit, vasarol
 *
 * Mindkét változat kezeli: fej_csere, mozgas (hókotró),
 *   allapot_allit, inspect, load/save/exit.
 *
 * Statikus segédek (bármely tesztből hívhatók):
 *   printChange, printConsole, printLog, simpleName,
 *   createHead, createState
 */
public final class TestSupport {

    private TestSupport() {}


    public static void dispatch(String rawLine,
                                Snowplow plow,
                                CleanerRole cleaner,
                                Map<String, Lane> lanes) {
        String line = rawLine.trim();
        if (line.isEmpty()) return;
        String[] parts = line.split("\\s+");

        switch (parts[0]) {
            case "fej_csere":     doChangeHead(plow, parts[2]);             break;
            case "mozgas":        doMovePlow(plow, lanes.get(parts[2]));    break;
            case "takarit":       doCleanWithMoney(plow, cleaner);          break;
            case "allapot_allit": doAllapotAllit(parts[1], parts[2], lanes);break;
            case "inspect":       doInspect(lanes.get(parts[1]));           break;
            case "load": case "save": case "exit": default:                 break;
        }
    }


    public static void dispatch(String rawLine,
                                Snowplow plow,
                                CleanerRole cleaner,
                                Map<String, Lane> lanes,
                                Store store) {
        String line = rawLine.trim();
        if (line.isEmpty()) return;
        String[] parts = line.split("\\s+");

        switch (parts[0]) {
            case "fej_csere":     doChangeHead(plow, parts[2]);              break;
            case "mozgas":        doMovePlow(plow, lanes.get(parts[2]));     break;
            case "takarit":       doCleanDirect(plow);                       break;
            case "allapot_allit": doAllapotAllit(parts[1], parts[2], lanes); break;
            case "inspect":       doInspect(lanes.get(parts[1]));            break;
            case "bolt_nyit":     doBoltNyit(store);                         break;
            case "vasarol":       doVasarol(parts, store, cleaner, plow);    break;
            case "load": case "save": case "exit": default:                  break;
        }
    }

    private static void doChangeHead(Snowplow plow, String headType) {
        Head old = plow.getCurrentHead();
        plow.changeHead(createHead(headType));
        printChange(plow.getId(), "currentHead", simpleName(old), simpleName(plow.getCurrentHead()));
    }

    private static void doMovePlow(Snowplow plow, Lane target) {
        if (target == null) return;
        Lane old = plow.getCurrentLane();
        plow.setCurrentLane(target);
        printChange(plow.getId(), "currentLane",
                old != null ? old.getName() : "null", target.getName());
    }

    private static void doCleanWithMoney(Snowplow plow, CleanerRole cleaner) {
        Lane lane = plow.getCurrentLane();
        if (lane == null) return;

        LaneState bState  = lane.getLaneState();
        double    bSnow   = lane.getSnowThickness();
        double    bIce    = lane.getIceThickness();
        double    bGravel = lane.getGravelThickness();
        int       bSalt   = plow.getSaltStock();
        int       bBio    = plow.getBiokeroseneStock();
        int       bGrav   = plow.getGravelStock();
        int       bMoney  = cleaner.getMoney();

        cleaner.controlSnowplow(plow);

        if (plow.getSaltStock()        != bSalt)  printChange(plow.getId(), "saltStock",        bSalt,  plow.getSaltStock());
        if (plow.getBiokeroseneStock() != bBio)   printChange(plow.getId(), "biokeroseneStock", bBio,   plow.getBiokeroseneStock());
        if (plow.getGravelStock()      != bGrav)  printChange(plow.getId(), "gravelStock",      bGrav,  plow.getGravelStock());

        LaneState aState = lane.getLaneState();
        if (!simpleName(bState).equals(simpleName(aState)))
            printChange(lane.getName(), "currentState", simpleName(bState), simpleName(aState));

        if (lane.getSnowThickness() != bSnow) printChange(lane.getName(), "snowThickness", bSnow, lane.getSnowThickness());
        if (lane.getIceThickness()  != bIce)  printChange(lane.getName(), "iceThickness",  bIce,  lane.getIceThickness());
        if (lane.getGravelThickness()  != bGravel)  printChange(lane.getName(), "gravelThickness",  bGravel,  lane.getGravelThickness());

        if (cleaner.getMoney() != bMoney) printChange(cleaner.getName(), "money", bMoney, cleaner.getMoney());
    }

    private static void doCleanDirect(Snowplow plow) {
        Lane lane = plow.getCurrentLane();
        if (lane == null) return;

        Head head = plow.getCurrentHead();
        boolean isSalt  = head instanceof SaltSpreaderHead;
        boolean isGrav  = head instanceof GravelSpreaderHead;
        boolean isDrag  = head instanceof DragonHead;

        if (isSalt && plow.getSaltStock() <= 0) {
            System.out.println("[Console]: Figyelmeztetés: A takarítás sikertelen. A sószóró fej készlete kimerült!");
            printChange(lane.getName(), "currentState", simpleName(lane.getLaneState()), simpleName(lane.getLaneState()));
            return;
        }
        if (isGrav && plow.getGravelStock() <= 0) {
            System.out.println("[Console]: Figyelmeztetés: A takarítás sikertelen. A zúzalékszóró fej készlete kimerült!");
            printChange(lane.getName(), "currentState", simpleName(lane.getLaneState()), simpleName(lane.getLaneState()));
            return;
        }
        if (isDrag && plow.getBiokeroseneStock() <= 0) {
            System.out.println("[Console]: Figyelmeztetés: A takarítás sikertelen. A sárkányfej biokerozin készlete kimerült!");
            printChange(lane.getName(), "currentState", simpleName(lane.getLaneState()), simpleName(lane.getLaneState()));
            return;
        }

        LaneState bState = lane.getLaneState();
        double bSnow = lane.getSnowThickness();
        double bIce  = lane.getIceThickness();
        double bGravel= lane.getGravelThickness();
        int bSalt    = plow.getSaltStock();
        int bBio     = plow.getBiokeroseneStock();
        int bGrav    = plow.getGravelStock();

        plow.clean(lane);

        if (plow.getSaltStock()        != bSalt)  printChange(plow.getId(), "saltStock",        bSalt,  plow.getSaltStock());
        if (plow.getBiokeroseneStock() != bBio)   printChange(plow.getId(), "biokeroseneStock", bBio,   plow.getBiokeroseneStock());
        if (plow.getGravelStock()      != bGrav)  printChange(plow.getId(), "gravelStock",      bGrav,  plow.getGravelStock());

        LaneState aState = lane.getLaneState();
        if (!simpleName(bState).equals(simpleName(aState)))
            printChange(lane.getName(), "currentState", simpleName(bState), simpleName(aState));

        if (lane.getSnowThickness() != bSnow) printChange(lane.getName(), "snowThickness", bSnow, lane.getSnowThickness());
        if (lane.getIceThickness()  != bIce)  printChange(lane.getName(), "iceThickness",  bIce,  lane.getIceThickness());
        if (lane.getGravelThickness()  != bGravel)  printChange(lane.getName(), "gravelThickness",  bGravel,  lane.getGravelThickness());


        if (isSalt && aState instanceof Clear && !(bState instanceof Clear))
            System.out.println("[Console]: A sáv felsózva, a jég elolvadt, az út tiszta.");
    }

    private static void doAllapotAllit(String laneName, String stateType, Map<String, Lane> lanes) {
        Lane lane = lanes.get(laneName);
        if (lane == null) return;
        LaneState before = lane.getLaneState();
        LaneState after  = createState(stateType);
        lane.setState(after);
        printChange(laneName, "currentState", simpleName(before), simpleName(after));
    }

    private static void doInspect(Lane lane) {
        if (lane == null) return;
        System.out.printf("[Console]: \"Sáv: %s; snowThickness=%s; iceThickness=%s; gravelThickness=%s; isPassable=%s; DynamicWeight=%s\"%n",
                lane.getName(),
                fmt(lane.getSnowThickness()),
                fmt(lane.getIceThickness()),
                fmt(lane.getGravelThickness()),
                lane.isPassable(),
                fmt(lane.getDynamicWeight()));
    }

    private static void doBoltNyit(Store store) {
        if (store == null) return;
        boolean before = store.isOpen();
        store.openStore();
        printChange("store", "open", before, store.isOpen());
    }

    private static void doVasarol(String[] parts, Store store, CleanerRole cleaner, Snowplow plow) {
        if (store == null || parts.length < 3) return;
        boolean thirdIsNumber = parts[2].matches("\\d+");

        if (thirdIsNumber) {
            doBuyByUnit(parts[1], Integer.parseInt(parts[2]), store, cleaner, plow);
        } else {
            doBuyPack(parts[2], store, cleaner, plow);
        }
    }

    private static void doBuyByUnit(String item, int count,
                                    Store store, CleanerRole cleaner, Snowplow plow) {
        final int UNIT_PRICE = 10;

        String selBefore = store.getSelectedItem();
        store.setSelectedItem(item);
        printChange("store", "selectedItem", selBefore == null ? "null" : selBefore, item);

        int mBefore = cleaner.getMoney();
        cleaner.decreaseMoney(UNIT_PRICE * count);
        printChange(cleaner.getName(), "money", mBefore, cleaner.getMoney());

        int sBefore = stockOf(item, plow);
        addStock(item, count, plow);
        printChange(plow.getId(), item + "Stock", sBefore, stockOf(item, plow));

        System.out.println("[log] Vásárlás sikeres: " + item + " x" + count);
    }

    private static void doBuyPack(String item,
                                  Store store, CleanerRole cleaner, Snowplow plow) {
        final int PACK_PRICE  = 50;
        final int PACK_AMOUNT = 10;

        int mBefore = cleaner.getMoney();
        cleaner.decreaseMoney(PACK_PRICE);
        printChange(cleaner.getName(), "money", mBefore, cleaner.getMoney());

        int sBefore = stockOf(item, plow);
        addStock(item, PACK_AMOUNT, plow);
        printChange(plow.getId(), item + "Stock", sBefore, stockOf(item, plow));
    }

    private static int stockOf(String item, Snowplow plow) {
        switch (item) {
            case "salt":        return plow.getSaltStock();
            case "biokerosene": return plow.getBiokeroseneStock();
            case "gravel":      return plow.getGravelStock();
            default:            return 0;
        }
    }

    private static void addStock(String item, int amount, Snowplow plow) {
        switch (item) {
            case "salt":        plow.addSalt(amount);        break;
            case "biokerosene": plow.addBiokerosene(amount); break;
            case "gravel":      plow.addGravel(amount);      break;
        }
    }


    /** [entity] [field]: before -> after */
    public static void printChange(String entity, String field, Object before, Object after) {
        System.out.println("[" + entity + "] [" + field + "]: " + before + " -> " + after);
    }

    /** [Console]: üzenet */
    public static void printConsole(String message) {
        System.out.println("[Console]: " + message);
    }

    /** [log] üzenet */
    public static void printLog(String message) {
        System.out.println("[log] " + message);
    }

    /** Osztálynév, null-biztos */
    public static String simpleName(Object o) {
        return o == null ? "null" : o.getClass().getSimpleName();
    }

    /** Fej-objektum gyártása típusnévből */
    public static Head createHead(String type) {
        switch (type) {
            case "thrower":        return new ThrowerHead();
            case "icebreaker":     return new IcebreakerHead();
            case "saltspreader":   return new SaltSpreaderHead();
            case "dragonhead":     return new DragonHead();
            case "sweeper":        return new SweeperHead();
            case "gravelspreader": return new GravelSpreaderHead();
            default: throw new IllegalArgumentException("Ismeretlen fej: " + type);
        }
    }

    /** LaneState-objektum gyártása típusnévből */
    public static LaneState createState(String type) {
        switch (type.toLowerCase()) {
            case "clear":     return new Clear();
            case "thinsnow":  return new ThinSnow();
            case "deepsnow":  return new DeepSnow();
            case "icesheet":  return new IceSheet();
            case "brokenice": return new BrokenIce();
            case "gravel":    return new Gravel();
            default: throw new IllegalArgumentException("Ismeretlen állapot: " + type);
        }
    }

    private static String fmt(double d) {
        return (d == Math.floor(d) && !Double.isInfinite(d))
               ? String.valueOf((long) d)
               : String.valueOf(d);
    }


    public static void dispatch(String rawLine, TestContext ctx) {
        String line = rawLine.trim();
        if (line.isEmpty()) return;
        String[] parts = line.split("\\s+");
        switch (parts[0]) {
            case "load": case "save": case "exit": return;
            case "allapot_allit": doAllapotAllit(parts[1], parts[2], ctx.lanes); break;
            case "kijelol":       doKijelol(parts[1], ctx);      break;
            case "auto_utvonal":  doAutoUtvonal(parts[1], ctx);  break;
            case "mozgas":        doMozgas(parts, ctx);           break;
            case "takarit":       doTakaritCtx(parts, ctx);      break;
            case "tick":          doTick(ctx);                    break;
            default:                                              break;
        }
    }

    private static void doKijelol(String vehicleId, TestContext ctx) {
        String before = ctx.selectedVehicle == null ? "null" : ctx.selectedVehicle;
        ctx.selectedVehicle = vehicleId;
        System.out.println("[selectedVehicle] " + before + " -> " + vehicleId);
    }

    private static void doAutoUtvonal(String busId, TestContext ctx) {
        String start = ctx.busAutoStart.get(busId);
        String dest  = ctx.busAutoDest.get(busId);
        String route = ctx.busAutoRoute.get(busId);
        System.out.println("[system] [selectedStart]: " + start);
        System.out.println("[system] [selectedDestination]: " + dest);
        String oldRoute = ctx.vehicleRoute.get(busId);
        ctx.vehicleRoute.put(busId, route);
        System.out.println("[" + busId + "] [currentRoute]: " + (oldRoute == null ? "null" : oldRoute) + " -> " + route);
    }

    private static void doMozgas(String[] parts, TestContext ctx) {
        String vehicleId = parts[1];
        if (parts.length >= 3) {
            doMozgasToLane(vehicleId, parts[2], ctx);
        } else {
            doMozgasAuto(vehicleId, ctx);
        }
    }

    private static void doMozgasToLane(String vehicleId, String targetLaneName, TestContext ctx) {
        String currentLaneName = ctx.vehicleLane.get(vehicleId);
        if (targetLaneName.equals(currentLaneName)) return;

        Lane targetLane = ctx.lanes.get(targetLaneName);
        if (targetLane == null) return;

        List<String> others = ctx.getOtherOccupants(targetLaneName, vehicleId);
        if (!others.isEmpty()) {
            doCollision(vehicleId, others, targetLaneName, ctx);
            return;
        }

        if (!targetLane.isPassable()) {
            ctx.setVehicleLane(vehicleId, targetLaneName);
            Vehicle v = ctx.getVehicle(vehicleId);
            if (v != null) v.setCurrentLane(targetLane);

            double spd = v != null ? v.getSpeed() : 0.0;
            if (spd > 0) {
                boolean isCar = ctx.cars.containsKey(vehicleId);
                if (isCar) {
                    System.out.println("[Console]: Az autó (" + vehicleId + ") elakadt a mély hóban.");
                } else {
                    System.out.println("[Console]: A busz (" + vehicleId + ") elakadt a mély hóban.");
                }
                printChange(vehicleId, "speed", spd, 0.0);
                if (v != null) v.setSpeed(0.0);
                ctx.stuckVehicles.add(vehicleId);
            }
            return;
        }

        if (currentLaneName != null) {
            printChange(vehicleId, "currentLane", currentLaneName, targetLaneName);
        }
        ctx.setVehicleLane(vehicleId, targetLaneName);
        Vehicle v = ctx.getVehicle(vehicleId);
        if (v != null) v.setCurrentLane(targetLane);

        if (ctx.buses.containsKey(vehicleId)) {
            checkBusTerminalArrival(vehicleId, targetLaneName, ctx);
        }
    }

    private static void doMozgasAuto(String vehicleId, TestContext ctx) {
        if (ctx.stuckVehicles.contains(vehicleId)) {
            String curLane = ctx.vehicleLane.get(vehicleId);
            Lane lane = curLane != null ? ctx.lanes.get(curLane) : null;
            if (lane != null && lane.isPassable()) {
                ctx.stuckVehicles.remove(vehicleId);
                Vehicle v = ctx.getVehicle(vehicleId);
                double defSpd = ctx.defaultSpeed.getOrDefault(vehicleId, 0.0);

                boolean isCar = ctx.cars.containsKey(vehicleId);
                if (isCar) {
                    System.out.println("[Console]: Az út letakarítva. Az autó (" + vehicleId + ") kiszabadult az elakadásból.");
                } else {
                    System.out.println("[Console]: Az út letakarítva. A busz (" + vehicleId + ") kiszabadult az elakadásból.");
                }

                if (v != null) {
                    printChange(vehicleId, "speed", v.getSpeed(), defSpd);
                    v.setSpeed(defSpd);
                }

                String routeName = ctx.vehicleRoute.get(vehicleId);
                String nextLane  = routeName != null ? ctx.getNextLaneName(routeName, curLane) : null;
                if (nextLane != null) {
                    printChange(vehicleId, "currentLane", curLane, nextLane);
                    ctx.setVehicleLane(vehicleId, nextLane);
                    if (v != null) v.setCurrentLane(ctx.lanes.get(nextLane));
                }
            }
            return;
        }

        String curLane   = ctx.vehicleLane.get(vehicleId);
        String routeName = ctx.vehicleRoute.get(vehicleId);
        if (routeName == null || curLane == null) return;

        String nextLaneName = ctx.getNextLaneName(routeName, curLane);
        if (nextLaneName == null) return;

        Lane nextLane = ctx.lanes.get(nextLaneName);

        if (nextLane != null && !nextLane.isPassable()) {
            Map<String, String> reroutes = ctx.carReroute.get(vehicleId);
            String newRoute = reroutes != null ? reroutes.get(routeName) : null;
            if (newRoute != null) {
                System.out.println("[Console]: Figyelmeztetés: A következő útszakasz járhatatlan. Útvonal újratervezése...");
                printChange(vehicleId, "currentRoute", routeName, newRoute);
                ctx.vehicleRoute.put(vehicleId, newRoute);
                System.out.println("[Console]: Új útvonal sikeresen kijelölve.");
                List<String> newLanes = ctx.routeLanes.get(newRoute);
                if (newLanes != null && !newLanes.isEmpty()) {
                    String firstLane = newLanes.get(0);
                    printChange(vehicleId, "currentLane", curLane, firstLane);
                    ctx.setVehicleLane(vehicleId, firstLane);
                    Vehicle v = ctx.getVehicle(vehicleId);
                    if (v != null) v.setCurrentLane(ctx.lanes.get(firstLane));
                }
            }
            return;
        }

        if (nextLane != null) {
            Vehicle v = ctx.getVehicle(vehicleId);
            printChange(vehicleId, "currentLane", curLane, nextLaneName);
            ctx.setVehicleLane(vehicleId, nextLaneName);
            if (v != null) v.setCurrentLane(nextLane);
            checkCarArrival(vehicleId, nextLaneName, ctx);
        }
    }

    private static void doCollision(String vehicleId, List<String> others,
                                    String laneName, TestContext ctx) {
        Lane lane = ctx.lanes.get(laneName);
        if (lane == null) return;

        printChange(laneName, "hasAccident", false, true);
        lane.setHasAccident(true);

        boolean isPlayer = ctx.playerBuses.contains(vehicleId);

        if (isPlayer) {
            System.out.println("[Console]: \"Baleset: A játékos által vezetett "
                    + vehicleId + " ütközött. Büntetés kiszabva.\"");
            Vehicle v = ctx.getVehicle(vehicleId);
            if (v != null && v.getSpeed() > 0) {
                printChange(vehicleId, "speed", v.getSpeed(), 0.0);
                v.setSpeed(0.0);
            }
            String driverId = ctx.busToDriver.get(vehicleId);
            if (driverId != null) {
                BusdriverRole driver = ctx.busdrivers.get(driverId);
                if (driver != null) {
                    int old = driver.getScore();
                    driver.decreaseScore(50);
                    printChange(driverId, "score", old, driver.getScore());
                }
            }
        } else {
            String first = others.isEmpty() ? vehicleId : others.get(0);
            System.out.println("[Console]: \"Ütközés történt két automatikus jármű között ("
                    + first + ", " + vehicleId + "). Az útszakasz blokkolva.\"");
            for (String otherId : others) {
                Vehicle ov = ctx.getVehicle(otherId);
                if (ov != null && ov.getSpeed() > 0) {
                    printChange(otherId, "speed", ov.getSpeed(), 0.0);
                    ov.setSpeed(0.0);
                }
            }
            Vehicle v = ctx.getVehicle(vehicleId);
            if (v != null && v.getSpeed() > 0) {
                printChange(vehicleId, "speed", v.getSpeed(), 0.0);
                v.setSpeed(0.0);
            }
        }
    }

    private static void checkBusTerminalArrival(String busId, String laneName, TestContext ctx) {
        String terminalName = ctx.terminalLane.get(laneName);
        if (terminalName == null) return;

        Bus bus = ctx.buses.get(busId);
        String driverId = ctx.busToDriver.get(busId);
        BusdriverRole driver = driverId != null ? ctx.busdrivers.get(driverId) : null;

        String oldLoc = bus.getLocation();
        bus.setLocation(terminalName);
        printChange(busId, "location", oldLoc, terminalName);

        if (ctx.busArrivalIncrRounds.contains(busId) && driver != null) {
            int old = driver.getCompletedRounds();
            driver.incrementCompletedRounds();
            printChange(driverId, "completedRounds", old, driver.getCompletedRounds());
        }

        int reward = ctx.busArrivalReward.getOrDefault(busId, 0);
        if (driver != null && reward > 0) {
            int old = driver.getMoney();
            driver.increaseMoney(reward);
            printChange(driverId, "money", old, driver.getMoney());
        }

        if (ctx.busArrivalShowReward.contains(busId) && reward > 0) {
            System.out.println("[log] Forduló teljesítve: " + busId + ", jutalom: " + reward);
        } else {
            System.out.println("[log] Forduló befejezve: " + busId);
        }

        String nextRoute = ctx.busNextRoute.get(busId);
        if (nextRoute != null) {
            String old = ctx.vehicleRoute.get(busId);
            ctx.vehicleRoute.put(busId, nextRoute);
            printChange(busId, "currentRoute", old != null ? old : "null", nextRoute);
        }
    }

    private static void checkCarArrival(String carId, String laneName, TestContext ctx) {
        String arrivalLoc = ctx.carArrivalLane.get(laneName);
        if (arrivalLoc == null) return;
        Car car = ctx.cars.get(carId);
        if (car == null) return;
        String old = car.getLocation();
        car.setLocation(arrivalLoc);
        printChange(carId, "location", old, arrivalLoc);
        System.out.println("[log] " + carId + " megérkezett a munkahely");
    }

    private static void doTakaritCtx(String[] parts, TestContext ctx) {
        if (parts.length < 3) return;
        String plowId  = parts[1];
        String laneName = parts[2];
        Lane lane = ctx.lanes.get(laneName);
        if (lane == null) return;

        LaneState before = lane.getLaneState();
        Snowplow plow = ctx.plows.get(plowId);
        if (plow != null) {
            plow.clean(lane);
        }
        LaneState after = lane.getLaneState();
        if (!simpleName(before).equals(simpleName(after))) {
            printChange(laneName, "currentState", simpleName(before), simpleName(after));
        }
    }

    private static void doTick(TestContext ctx) {
        for (String carId : ctx.cars.keySet()) {
            Car car = ctx.cars.get(carId);
            String routeName = ctx.vehicleRoute.get(carId);
            String curLane   = ctx.vehicleLane.get(carId);

            if (routeName == null) {
                String autoRoute = ctx.carAutoRoute.get(carId);
                if (autoRoute == null) continue;
                String res  = ctx.carResidence.get(carId);
                String work = ctx.carWorkplace.get(carId);
                if (res  != null) System.out.println("[" + carId + "] [residence]: "  + res);
                if (work != null) System.out.println("[" + carId + "] [workplace]: " + work);
                printChange(carId, "currentRoute", "null", autoRoute);
                ctx.vehicleRoute.put(carId, autoRoute);
                routeName = autoRoute;
            }

            String nextLaneName = ctx.getNextLaneName(routeName, curLane);
            if (nextLaneName == null) continue;

            Lane nextLane = ctx.lanes.get(nextLaneName);
            printChange(carId, "currentLane", curLane, nextLaneName);
            ctx.setVehicleLane(carId, nextLaneName);
            car.setCurrentLane(nextLane);

            checkCarArrival(carId, nextLaneName, ctx);
        }
    }



    public static void dispatch(String rawLine,
                                Game game) {
        dispatch(rawLine, game, null);
    }

    public static void dispatch(String rawLine,
                                Game game,
                                GameSnapshot snap) {
        String line = rawLine.trim();
        if (line.isEmpty()){
            doWait(game);
            return;
        }
        String[] parts = line.split("\\s+");

        switch (parts[0]) {
            case "state": doState(game, snap);                              break;
            case "load": case "save": case "exit": default:                 break;
        }
    }

    private static void doState(Game game, GameSnapshot snap) {
        if (snap == null) return;
        System.out.println("[RoadNetwork] [Status]: Initialized");
        System.out.println("[Nodes] [Count]: " + snap.nodesCount);
        System.out.println("[Lanes] [Count]: " + snap.lanesCount);
        System.out.println("[Vehicles] [Count]: " + game.getVehicles().size());
        System.out.println("[Players] [Count]: " + game.getPlayers().size());
        for (Vehicle v : game.getVehicles()) {
            String pos = snap.vehiclePositions.get(v.getId());
            if (pos != null) {
                System.out.println("[" + v.getId() + "] [Position]: " + pos);
            }
        }
        int intensity = snap.weather != null ? snap.weather.getSnowIntensity() : 0;
        System.out.println("[Weather] [CurrentSnowIntensity]: " + (double) intensity);
        System.out.println("[Game] [CurrentRound]: " + game.getCurrentRound());
    }

    /** Az 1-es teszt {@code state} parancsához szükséges snapshot-kontextus. */
    public static final class GameSnapshot {
        public final int nodesCount;
        public final int lanesCount;
        public final Weather weather;
        public final Map<String, String> vehiclePositions;

        public GameSnapshot(int nodesCount, int lanesCount,
                            Weather weather, Map<String, String> vehiclePositions) {
            this.nodesCount = nodesCount;
            this.lanesCount = lanesCount;
            this.weather = weather;
            this.vehiclePositions = vehiclePositions;
        }
    }

    private static void doWait(Game game){
        int oldRound=game.getCurrentRound();
        game.tick();
        if(oldRound!=game.getCurrentRound())
            printChange("game", "currentRound", oldRound, game.getCurrentRound());
        if(game.isOver()){
            String message="Játék vége! Pontszámok: ";
            for (Player p : game.getPlayers()) {
                message+=p.getName()+": "+p.getSumPoints()+ " pont";
                if(p != game.getPlayers().get(game.getPlayers().size() - 1)){
                    message+=", ";
                }
                else
                    message+=".";
            }
            printConsole("\""+message+"\"");
        }

    }


}
