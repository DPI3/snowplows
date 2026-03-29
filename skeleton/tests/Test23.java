package skeleton.tests;

import skeleton.src.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A 21. teszteset (Busz közlekedés teszt) implementációja.
 * Ez a teszt a busz normál haladását vizsgálja, beleértve a következő sáv lekérdezését,
 * annak járhatóságának ellenőrzését, a pozíció frissítését, valamint a balesetek vizsgálatát.
 */
public class Test23 implements TestCase {

    /**
     * Futtatja a tesztesetet.
     * Példányosítja a buszt, az útvonalat és a sávot, majd beállítja a busz kezdőállapotát.
     * Ezt követően a buszt hozzáadja a játékhoz, és egy szimulációs lépés (tick) 
     * meghívásával elindítja a mozgási folyamatot.
     */
    @Override
    public void run() {
        Car car = new Car();
        Route route = new Route();
        Lane currentLane = new Lane();
        
        car.setCurrentRoute(route);
        car.setCurrentLane(currentLane);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(car);
        
        Game game = new Game(0, 10, vehicles, new ArrayList<>());
        
        game.tick();
    }
}