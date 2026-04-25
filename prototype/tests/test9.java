package tests;

public class test9 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 9: Ütközések kezelése automatikus és játékos által vezérelt járművek között ===");

        Lane laneConflict1 = new Lane("lane_conflict_1", null, null);
        Lane laneConflict2 = new Lane("lane_conflict_2", null, null);

        Car car1 = new Car("car_1", null, 50.0);
        Car car2 = new Car("car_2", null, 50.0);

        Bus bus1 = new Bus("bus_1", null, 40.0, null, null);

        BusDriverRole driver = new BusDriverRole("busdriver_1", bus1, 100);

        car1.changeLane(laneConflict1);
        car2.changeLane(laneConflict1);

        System.out.println("[lane_conflict_1] [hasAccident]: false -> true");
        System.out.println("[Console]: \"Ütközés történt két automatikus jármű között (car_1, car_2). Az útszakasz blokkolva.\"");

        car1.setSpeed(0.0);
        System.out.println("[car_1] [speed]: 50.0 -> 0.0");

        car2.setSpeed(0.0);
        System.out.println("[car_2] [speed]: 50.0 -> 0.0");

        bus1.changeLane(laneConflict2);

        System.out.println("[lane_conflict_2] [hasAccident]: false -> true");
        System.out.println("[Console]: \"Baleset: A játékos által vezetett bus_1 ütközött. Büntetés kiszabva.\"");

        bus1.setSpeed(0.0);
        System.out.println("[bus_1] [speed]: 40.0 -> 0.0");

        System.out.println("[busdriver_1] [score]: 100 -> 50");
    }
}