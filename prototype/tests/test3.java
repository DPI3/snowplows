package tests;

import src.Lane;
import src.Snowplow;
import src.SweeperHead;
import src.ThinSnow;

/**
 * Teszteset 3: Hókotró tisztítási funkciójának tesztelése.
 * Ellenőrzi, hogy egy SweeperHead-del (seprűs fejjel) felszerelt hókotró 
 * képes-e letakarítani a vékony havat, és visszaállítani a sávot Clear állapotba.
 */
public class test3 implements TestCase {

    @Override
    public void run() {
        // 1. Környezet inicializálása: létrehozunk egy sávot, amit havassá teszünk
        Lane lane = new Lane();
        lane.setState(new ThinSnow()); 
        System.out.println("[Teszt] Sáv létrehozva. Kezdeti állapot: " + lane.getLaneState().getClass().getSimpleName());

        // 2. Hókotró és a megfelelő fej létrehozása
        SweeperHead sweeper = new SweeperHead();
        // A hókotró paraméterei: azonosító, kezdő sáv, sebesség, felszerelt fej[cite: 1]
        Snowplow plow = new Snowplow("plow_1", lane, 30.0, sweeper); 
        System.out.println("[Teszt] Hókotró (plow_1) a sávon, SweeperHead felszerelve.");

        // 3. Tisztítási folyamat elindítása
        System.out.println("[Teszt] Takarítás megkezdése...");
        plow.clean(lane); // A hókotró megtisztítja azt a sávot, amit átadunk neki[cite: 1]

        // 4. Eredmények kiértékelése
        // A SweeperHead a "lane.change(3)" belső hívással csökkenti a havat, aminek Clear állapotot kell eredményeznie[cite: 1]
        System.out.println("[Eredmény] Takarítás utáni állapot: " + lane.getLaneState().getClass().getSimpleName());
    }
}