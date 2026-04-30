package tests;

import src.Car;
import src.Lane;
import src.DeepSnow;

/**
 * Teszteset 2: Jármű sávváltásának és az út járhatóságának tesztelése.
 * Ellenőrzi, hogy a jármű át tud-e menni egy tiszta sávra, 
 * illetve megakadályozza-e a rendszer, hogy járhatatlan (DeepSnow) sávra lépjen.
 */
public class test2 implements TestCase {

    @Override
    public void run() {
        // 1. Környezet inicializálása
        Lane startLane = new Lane(); // Alapértelmezett: Clear (járható)
        Lane clearLane = new Lane(); // Szintén Clear állapotú
        Lane snowLane = new Lane();
        snowLane.setState(new DeepSnow()); // DeepSnow (járhatatlan) állapot beállítása

        // Autó létrehozása a kezdő sávon (id, sáv, sebesség, lakhely, munkahely)[cite: 1]
        Car car = new Car("car_1", startLane, 50.0, null, null); 
        System.out.println("[Teszt] Autó a kezdő sávon. Jelenlegi sáv járható: " + car.getCurrentLane().isPassable());

        // 2. Sikeres sávváltás tesztelése
        System.out.println("[Teszt] Sávváltás megkísérlése egy tiszta sávra...");
        boolean successClear = car.changeLane(clearLane); // Ennek igaznak kell lennie[cite: 1]
        System.out.println("[Eredmény] Sávváltás sikeres: " + successClear);

        // 3. Sikertelen sávváltás tesztelése (Vastag hó)
        System.out.println("[Teszt] Sávváltás megkísérlése a vastag hóval borított sávra...");
        boolean successSnow = car.changeLane(snowLane); // Ennek hamisnak kell lennie[cite: 1]
        System.out.println("[Eredmény] Sávváltás sikeres: " + successSnow);
    }
}