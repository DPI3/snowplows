package tests;

import src.Lane;

/**
 * Teszteset 1: Alapvető sávállapot és havazás szimuláció.
 * A teszt ellenőrzi, hogy egy Clear (tiszta) sáv megfelelően 
 * ThinSnow (vékony hó) állapotba kerül-e havazás hatására.
 */
public class test1 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 1: Havazás hatása egy tiszta sávra ===");

        // 1. Sáv inicializálása
        Lane lane = new Lane();
        System.out.println("[Teszt] Sáv létrehozva. Kezdeti állapot: " + lane.getLaneState().getClass().getSimpleName());

        // 2. Időjárás szimulálása
        System.out.println("[Teszt] Havazás szimulálása (3 egységnyi hó)...");
        lane.applyWeather(3);

        // 3. Eredmények kiértékelése
        System.out.println("[Eredmény] Új állapot: " + lane.getLaneState().getClass().getSimpleName());
        System.out.println("[Eredmény] A sáv járható: " + lane.isPassable());
    }
}