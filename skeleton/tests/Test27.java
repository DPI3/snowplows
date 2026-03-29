package skeleton.tests;

import skeleton.src.*;

import java.util.Scanner;

/**
 * 27. teszteset: Jégpáncél kialakulása teszt.
 * A teszteset ellenőrzi, hogy ha kellő számú autó halad át egy
 * vékony hóval (ThinSnow) borított sávon, akkor a sáv állapota
 * jégpáncéllá (IceSheet) változik-e.
 */
public class Test27 extends TestCase {

    /**
     * A tesztszekvencia futtatása.
     * Előfeltétel: Adott egy sáv ThinSnow állapottal.
     * Több autó halad át a sávon, amíg a compactionCounter el nem éri
     * a határértéket, és a sáv IceSheet állapotba nem vált.
     *
     * @param scanner a scanner objektum a felhasználói bevitel olvasásához (nem használt)
     */
    @Override
    public void run(Scanner scanner) {

        // Előfeltétel: sáv létrehozása ThinSnow állapottal
        Lane lane = new Lane();
        lane.setState(new ThinSnow());

        // Autók létrehozása
        Car car1 = new Car(lane);
        Car car2 = new Car(lane);
        Car car3 = new Car(lane);

        // Loop: több autó áthalad a sávon
        // car1 áthalad - határérték még nem érve el
        car1.move();

        // car2 áthalad - határérték még nem érve el
        car2.move();

        // car3 áthalad - határérték elérve, IceSheet állapotba vált
        car3.move();
    }
}