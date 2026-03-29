package skeleton.tests;

import skeleton.src.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A 24. teszteset (Autó elakadása járhatatlan úton teszt) implementációja.
 * A teszt azt vizsgálja, hogyan viselkedik az autó, ha a lekérdezett 
 * következő sáv (pl. mély hó miatt) járhatatlan.
 */
public class Test24 implements TestCase {

    /**
     * Futtatja a tesztesetet.
     * Példányosítja az autót, az útvonalat és a sávot, majd beállítja a kezdőállapotot.
     * Ezt követően az autót hozzáadja a játékhoz, és elindítja a szimulációs lépést.
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