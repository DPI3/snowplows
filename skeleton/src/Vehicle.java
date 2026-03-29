package skeleton.src;

import java.util.Scanner;

/**
 * A Vehicle absztrakt osztály a rendszerben szereplő járművek közös ősosztálya.
 *
 * Tartalmazza a járművek alapvető tulajdonságait és viselkedését,
 * például a mozgást, sávváltást és az időlépésenkénti működést.
 *
 * A konkrét járműtípusok (Car, Bus, Snowplow) ebből az osztályból származnak.
 */
public abstract class Vehicle {
    /**
     * A jármű egyedi azonosítója.
     */
    protected String id;

    /**
     * Az aktuális sáv, amelyben a jármű jelenleg tartózkodik.
     */
    protected Lane currentLane;

    /**
     * A jármű pozíciója a sávon belül.
     */
    protected double positionOnLane;

    /**
     * A jármű aktuális sebessége.
     */
    protected double speed;

    /**
     * Üres konstruktor a jármű inicializálásához.
     */
    public Vehicle() {}

    /**
     * Konstruktor a jármű inicializálásához.
     *
     * @param id azonosító
     * @param currentLane aktuális sáv
     * @param positionOnLane pozíció a sávon
     * @param speed sebesség
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
    public abstract void move();

    /**
     * Visszaadja a jármű aktuális sávját.
     * * Megjegyzés: Szándékosan nincs benne Skeleton logolás, 
     * hogy ne rontsa el a 19-es teszt elvárt kimenetét!
     *
     * @return az aktuális sáv (Lane)
     */
    public Lane getCurrentLane() {
        return this.currentLane;
    }


    /**
     * Sávváltás végrehajtása.
     *
     * A metódus megpróbálja a járművet a megadott cél sávba helyezni.
     *
     * @param targetLane a cél sáv
     * @return true, ha a sávváltás sikeres, különben false
     */
    public boolean changeLane(Lane targetLane) {
        Skeleton.printCall("Vehicle", "changeLane(targetLane)");

        this.currentLane = targetLane;
        Skeleton.printState("A jármű aktuális sávja megváltozott.");

        Skeleton.printReturn("true");
        return true;
    }

    public void updatePositionOn(Lane lane){ 
        positionOnLane++; //Ideiglenes

    }
    /**
     * Egy szimulációs lépést (tick) hajt végre.
     *
     * A jármű minden időegységben ezt a metódust hívja meg.
     */
    public void tick() {
        // A tick hívja a move-ot
        this.move();
    }

    public void setCurrentLane(Lane lane){
        Skeleton.printCall("Vehicle", "setCurrentLane(lane)");
        currentLane=lane;
        Skeleton.printReturn("");
    }

    public double getSpeed() {
        return speed;
    }
}