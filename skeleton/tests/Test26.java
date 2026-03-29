package skeleton.tests;

import skeleton.src.*;

import java.util.Scanner;

/**
 * 26. teszteset: Autó útvonalának újratervezése akadály esetén.
 * A teszteset ellenőrzi, hogy ha a legrövidebb út járhatatlanná
 * válik, az autó képes-e alternatív útvonalat keresni.
 */
public class Test26 implements TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Előfeltétel: A Car halad a currentRoute mentén.
     * A következő sáv állapota járhatatlanná változik.
     * A Car új útvonalat kér a getShortestPath() segítségével.
     */
    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        // Előfeltétel: úthálózat létrehozása
        RoadNetwork roadNetwork = new RoadNetwork();

        // Csomópontok létrehozása
        Residence residence = new Residence("R1");
        Workplace workplace = new Workplace("W1");

        // Sávok létrehozása
        // Útvonal 1 sávja - járhatatlanná válik
        Lane blockedLane = new Lane();
        blockedLane.setState(new DeepSnow());

        // Útvonal 2 sávja - járható alternatíva
        Lane alternativeLane = new Lane();
        alternativeLane.setState(new Clear());

        // Útvonal létrehozása
        Route currentRoute = new Route();

        // Autó létrehozása
        Car car = new Car("car1", blockedLane, 0.0, 1.0,
                residence, workplace, currentRoute);

        // Az autó mozog - akadályt észlel
        car.move();

        // Az akadály jelzése - sáv járhatatlanná válik
        blockedLane.setState(new DeepSnow());

        // Új útvonal kérése a RoadNetwork-től
        Route alternativeRoute = roadNetwork.getShortestPath(residence, workplace);

        // Assert: az alternatív útvonal nem null
        assert alternativeRoute != null
                : "FAIL: Alternative route should not be null";

        // Assert: az alternatív útvonal járható sávokat tartalmaz
        boolean passable = alternativeLane.isPassable();
        assert passable
                : "FAIL: Alternative route lane should be passable";
    }
}