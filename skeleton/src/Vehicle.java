/**
 * A Vehicle absztrakt osztály a rendszerben szereplő járművek közös ősosztálya.
 *
 * Az osztály tartalmazza a járművek alapvető tulajdonságait,
 * mint például az aktuális sáv, pozíció és sebesség.
 *
 * A Vehicle osztály felelős az általános mozgási viselkedés definiálásáért,
 * amelyet a leszármazott osztályok (Car, Bus, Snowplow) használnak.
 *
 * Az osztály absztrakt, így közvetlenül nem példányosítható.
 */
public abstract class Vehicle {
    /**
     * A jármű egyedi azonosítója.
     */
    protected String id;

    /**
     * Az a sáv, amelyben a jármű jelenleg tartózkodik.
     */
    protected Lane currentLane;

    /**
     * A jármű pozíciója az aktuális sávon belül.
     */
    protected double positionOnLane;

    /**
     * A jármű aktuális sebessége.
     */
    protected double speed;


    /**
     * Konstruktor a jármű inicializálásához.
     *
     * @param id a jármű azonosítója
     * @param currentLane az aktuális sáv
     * @param positionOnLane a pozíció a sávon
     * @param speed a jármű sebessége
     */
    public Vehicle(String id, Lane currentLane, double positionOnLane, double speed) {
        
        
        this.id = id;
        this.currentLane = currentLane;
        this.positionOnLane = positionOnLane;
        this.speed = speed;
    }

    /**
     * A jármű mozgását végrehajtó metódus.
     *
     * A szkeleton implementációban csak a metódushívás kerül naplózásra.
     */
    protected void move() {
    }

    /**
     * Egy szimulációs lépést (tick) hajt végre.
     *
     * A jármű minden időegységben ezt a metódust hívja meg.
     */
    public void tick() {
    }
}