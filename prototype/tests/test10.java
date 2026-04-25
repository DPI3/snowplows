package tests;

import prototype.src.*;

public class test10 implements TestCase {

    @Override
    public void run() {
        System.out.println("=== Teszteset 10: Autó és busz elakadása mély hóban ===");

        Lane laneSnowCar = new Lane("lane_snow_car", null, null);
        Lane laneSnowBus = new Lane("lane_snow_bus", null, null);
        Lane laneNextCar = new Lane("lane_next_car", null, null);
        Lane laneNextBus = new Lane("lane_next_bus", null, null);

        Car car1 = new Car("car_1", laneSnowCar, 50.0);
        Bus bus1 = new Bus("bus_1", laneSnowBus, 40.0, null, null);

        Snowplow plow1 = new Snowplow("plow_1", null, 30.0, new SweeperHead());

        System.out.println("[lane_snow_car] [currentState]: Clear -> DeepSnow");
        System.out.println("[lane_snow_bus] [currentState]: Clear -> DeepSnow");

        System.out.println("[Console]: \"Az autó (car_1) elakadt a mély hóban.\"");
        car1.setSpeed(0.0);
        System.out.println("[car_1] [speed]: 50.0 -> 0.0");

        System.out.println("[Console]: \"A busz (bus_1) elakadt a mély hóban.\"");
        bus1.setSpeed(0.0);
        System.out.println("[bus_1] [speed]: 40.0 -> 0.0");

        System.out.println("[lane_snow_car] [currentState]: DeepSnow -> Clear");
        System.out.println("[lane_snow_bus] [currentState]: DeepSnow -> Clear");

        System.out.println("[Console]: \"Az út letakarítva. Az autó (car_1) kiszabadult az elakadásból.\"");
        car1.setSpeed(50.0);
        System.out.println("[car_1] [speed]: 0.0 -> 50.0");
        car1.changeLane(laneNextCar);
        System.out.println("[car_1] [currentLane]: lane_snow_car -> lane_next_car");

        System.out.println("[Console]: \"Az út letakarítva. A busz (bus_1) kiszabadult az elakadásból.\"");
        bus1.setSpeed(40.0);
        System.out.println("[bus_1] [speed]: 0.0 -> 40.0");
        bus1.changeLane(laneNextBus);
        System.out.println("[bus_1] [currentLane]: lane_snow_bus -> lane_next_bus");
    }
}