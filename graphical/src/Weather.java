package src;

import src.Bridge;
import src.Lane;
import src.Road;
import src.RoadNetwork;
import src.Tunnel;

/**
 * A Weather osztály a szimuláció időjárási viszonyait reprezentálja.
 * Felelős a havazás szimulálásáért és az utak állapotának befolyásolásáért.
 */
public class Weather {

    /** Az úthálózat, amelyen az időjárás hatásait alkalmazza. */
    private RoadNetwork roadNetwork;

    /** A havazás intenzitása. Meghatározza, mennyi hó esik a sávokra egyetlen körben. */
    private int snowIntensity;

    /**
     * Szimulációs lépés végrehajtása az időjárás-változásokhoz.
     * Minden útra alkalmazza a havazást, majd frissíti a sósávok állapotát.
     */
    public void tick() {
        if (roadNetwork == null) return;

        for (Road road : roadNetwork.getRoads()) {
            snowfallTick(road);
        }

        for (Road road : roadNetwork.getRoads()) {
            for (Lane lane : road.getLanes()) {
                lane.tickSalt();
            }
        }
    }

    /**
     * A havazás hatásának alkalmazása egy adott útra.
     * Hidak esetén az intenzitás növekszik, alagutak esetén nincs havazás.
     *
     * @param road az út, amelyre a havazás hat
     */
    public void snowfallTick(Road road) {
        if (road != null) {
            road.increaseSnowLevel();
        }
        int appliedSnow = snowIntensity;

        if (road instanceof Bridge) {
            appliedSnow += 2;
        } else if (road instanceof Tunnel) {
            appliedSnow = 0;
        }

        if (appliedSnow > 0) {
            for (Lane lane : road.getLanes()) {
                lane.applyWeather(appliedSnow);
            }
        }
    }

    /**
     * Beállítja a havazás új intenzitását. Az érték nem lehet negatív.
     *
     * @param intensity az új intenzitás értéke
     */
    public void setSnowIntensity(int intensity) {
        this.snowIntensity = Math.max(0, intensity);
    }

    /**
     * Visszaadja a havazás aktuális intenzitását.
     *
     * @return az aktuális intenzitás értéke
     */
    public int getSnowIntensity() {
        return snowIntensity;
    }

    /**
     * Alapértelmezett Weather konstruktor.
     */
    public Weather() {
    }

    /**
     * Weather példány létrehozása megadott úthálózattal és havazási intenzitással.
     *
     * @param roadNetwork az úthálózat
     * @param snowIntensity a havazás kezdő intenzitása
     */
    public Weather(RoadNetwork roadNetwork, int snowIntensity) {
        this.roadNetwork = roadNetwork;
        this.snowIntensity = snowIntensity;
    }

    /**
     * Beállítja az úthálózatot, amelyen az időjárás hatásait alkalmazza.
     *
     * @param roadNetwork az új úthálózat
     */
    public void setRoadNetwork(RoadNetwork roadNetwork) {
        this.roadNetwork = roadNetwork;
    }

}
