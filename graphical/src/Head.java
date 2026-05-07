package src;

/**
 * Absztrakt kotrófej.
 * Minden fej egy adott tisztítási stratégiát valósít meg.
 */
public abstract class Head implements Buyable {

    /**
     * A sáv tisztítása
     * 
     * @param lane a tisztítandó szakasz
     * @param snowplow a tisztítást végző hókotró
     */
    public abstract void clean(Lane lane, Snowplow snowplow);
}