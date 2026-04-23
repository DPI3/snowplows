package prototype.src;

/**
 * Absztrakt kotrófej.
 * Minden fej egy adott tisztítási stratégiát valósít meg.
 */
public abstract class Head implements Buyable {

    /**
     * A sáv tisztítása
     */
    public abstract void clean(Lane lane, Snowplow snowplow);
}