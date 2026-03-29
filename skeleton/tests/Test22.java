package skeleton.tests;

import skeleton.src.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A 22. teszteset (Busz forduló teljesítése teszt) implementációja.
 * A dokumentáció és a szekvenciadiagram alapján ellenőrzi, hogy a végállomást elérve 
 * a busz növeli-e a sofőr fordulószámát és lekér-e új útvonalat.
 */
public class Test22 implements TestCase {

    /**
     * Futtatja a tesztesetet.
     * Példányosítja a sofőrt és a buszt, majd a játék (Game) léptetésével
     * kiváltja a mozgási és érkezési folyamatot.
     */
    @Override
    public void run() {
        BusdriverRole driver = new BusdriverRole();
        Bus bus = new Bus();
        bus.setDriver(driver);
        
        List<Vehicle> vehicles = new ArrayList<>();
        vehicles.add(bus);
        
        Game game = new Game(0, 10, vehicles, new ArrayList<>());
        game.tick();
    }
}