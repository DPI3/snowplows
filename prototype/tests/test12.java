package tests;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import src.*;

/**
 * Teszteset 12: Játék kiértékelése.
 * A játék végi állapot detektálásának ellenőrzése (a körlimit elérésekor). 
 * A pontszámítási logika helyességének tesztelése mind a buszsofőr, mind a takarító szerepkörök esetén.
 */
public class test12 implements TestCase {
    @Override
    public void run() {

        List<Vehicle> vehicles=new ArrayList<>();
        Snowplow snowplow1 = new Snowplow("plow_1", null, 0, null);
        Snowplow snowplow2 = new Snowplow("plow_2", null, 0, null);

        Bus bus1= new Bus("bus_1", null, 0, null, null);
        Bus bus2= new Bus("bus_2", null, 0, null, null);

        Car car1=new Car("car_1", null, 0, null, null);
        Car car2=new Car("car_2", null, 0, null, null);

        vehicles.add(snowplow1); 
        vehicles.add(snowplow2);
        vehicles.add(bus1);
        vehicles.add(bus2);
        vehicles.add(car1);
        vehicles.add(car2);

        List <Role> roles1= new ArrayList<>();
        roles1.add(new CleanerRole("cleaner_1", 70, snowplow1));
        BusdriverRole b1=new BusdriverRole("busdriver_1", bus1);
        b1.incrementCompletedRounds();
        roles1.add(b1);
        Player p1=new Player(1, "Játékos1", roles1);

        List <Role> roles2= new ArrayList<>();
        roles2.add(new CleanerRole("cleaner_2", 85, snowplow2));
        roles2.add(new BusdriverRole("busdriver_2", bus1));
        Player p2=new Player(2, "Játékos2", roles2);

        List <Player> pList= new ArrayList<>();
        pList.add(p1);
        pList.add(p2);
        
        Game game= new Game(9, 10, vehicles, pList);
        try {
            List<String> commands = Files.readAllLines(Paths.get("test_data/input/test12_in.txt"));
            for (String raw : commands) {
                TestSupport.dispatch(raw, game);
            }
        } catch (IOException e) {
            System.err.println("Hiba a bemeneti fajl olvasasakor: " + e.getMessage());
        }
    }
}
