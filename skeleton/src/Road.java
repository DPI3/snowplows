package skeleton.src;

import java.util.ArrayList;
import java.util.List;

/**
 * Absztrakt alaposztály minden úttípushoz.
 * Kezeli a sávok listáját és az időjárási hatásokat.
 */
public abstract class Road {
    protected List<Lane> lanes = new ArrayList<>();

    /**
     * Alkalmazza az időjárási hatásokat az útra. 
     * Megjegyzés: Az 'applyWeatherEffect' nevet használtam, hogy illeszkedjen az alosztályokhoz!
     * * @param weather az alkalmazandó időjárás
     */
    public abstract void applyWeatherEffect(Weather weather);
}