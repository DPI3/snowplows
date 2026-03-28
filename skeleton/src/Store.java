package skeleton.src;

public class Store extends Node {
    
    public Store(String id) {
        super(id);
    }

    @Override
    public void onVehicleEnter(Vehicle vehicle) {
        Skeleton.printCall("Store", "onVehicleEnter(vehicle)");
        // Ide jöhetne a vásárlási logika, de a szkeletonban elég a log
        Skeleton.printReturn("");
    }
}