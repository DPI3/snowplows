package prototype.src;

/**
 * A Car osztály egy személyautót reprezentál.
 * Lakóhely és munkahely között közlekedik.
 */
public class Car extends Vehicle {

    private final Residence residence;
    private final Workplace workplace;

    /** Az aktuális útszakasz */
    private Road currentRoad;

    /** Alap sebesség (elakadás után visszaállításhoz) */
    private final double defaultSpeed;

    public Car(String id, Lane lane, double speed,
               Residence res, Workplace work) {
        super(id, lane, speed);
        this.residence = res;
        this.workplace = work;
        this.defaultSpeed = speed;
    }

    /**
     * Sávváltás felüldefiniálása (road frissítés miatt)
     */
    @Override
    public boolean changeLane(Lane targetLane) {
        if (targetLane == null || !targetLane.isPassable()) {
            return false;
        }

        this.currentLane = targetLane;
        this.currentRoad = targetLane.getParentRoad();
        this.positionOnLane = 0.0;
        this.speed = defaultSpeed;

        return true;
    }

    /** Jármű megállítása */
    public void stopAndWait() {
        this.speed = 0.0;
    }

    /** Újraindulás */
    public void resume() {
        this.speed = defaultSpeed;
    }
}