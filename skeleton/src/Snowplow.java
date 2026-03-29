package skeleton.src;

import java.util.Scanner;

/**
 * A Snowplow osztály egy hókotró járművet reprezentál.
 *
 * A hókotró képes különböző típusú fejekkel tisztítani az utakat,
 * valamint nyersanyagokat (só, biokerozin) használ a működéshez.
 *
 * A Snowplow a Vehicle osztályból származik,
 * és megvalósítja a Buyable interfészt.
 */
public class Snowplow extends Vehicle {

    /**
     * Az aktuálisan felszerelt kotrófej.
     */
    private Head currentHead;

    /**
     * A rendelkezésre álló só mennyisége.
     */
    private int saltStock;

    /**
     * A rendelkezésre álló biokerozin mennyisége.
     */
    private int biokeroseneStock;

    /**
     * Üres konstruktor a tesztelhetőség érdekében.
     */
    public Snowplow() {
        super();
        Skeleton.printCall("Snowplow", "Snowplow()");
        ThrowerHead throwerHead= new ThrowerHead();
        Skeleton.printReturn("");
    }

    /**
     * Konstruktor a Snowplow objektum létrehozásához.
     *
     * @param id azonosító
     * @param currentLane aktuális sáv
     * @param positionOnLane pozíció
     * @param speed sebesség
     * @param currentHead aktuális fej
     * @param saltStock só készlet
     * @param biokeroseneStock biokerozin készlet
     */
    public Snowplow(String id, Lane currentLane, double positionOnLane, double speed,
                    Head currentHead, int saltStock, int biokeroseneStock) {
        super(id, currentLane, positionOnLane, speed);
        this.currentHead = currentHead;
        this.saltStock = saltStock;
        this.biokeroseneStock = biokeroseneStock;
    }

    /**
     * A fej cseréje.
     *
     * @param newHead az új fej
     */
    public void changeHead(Head newHead) {
        Skeleton.printCall("Snowplow", "changeHead(newHead)");

        this.currentHead = newHead;
        Skeleton.printState("A hókotró feje lecserélve.");

        Skeleton.printReturn("");
    }

    /**
     * Tisztítást végez a megadott sávon.
     *
     * @param lane a tisztítandó sáv
     */
    public void clean(Lane lane) {
        Skeleton.printCall("Snowplow", "clean(lane)");

        if (currentHead != null) {
            currentHead.clean(lane, this);
        } else {
            Skeleton.printState("Nincs felszerelt fej, a tisztítás nem hajtható végre.");
        }

        Skeleton.printReturn("");
    }

    /**
     * A hókotró mozgását hajtja végre.
     */
    @Override
    public void move() {
        Skeleton.printCall("Snowplow", "move()");

        Skeleton.printState("A hókotró előrehalad az aktuális sávban.");

        Skeleton.printReturn("");
    }

    
}