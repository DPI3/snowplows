public class Snowplow extends Vehicle implements Buyable {
    private Head currentHead;
    private int saltStock;
    private int biokeroseneStock;
    private CleanerRole cleanerRole;

    public Snowplow(String id, Lane currentLane, double positionOnLane, double speed,
                    Head currentHead, int saltStock, int biokeroseneStock,
                    CleanerRole cleanerRole) {
        super(id, currentLane, positionOnLane, speed);
        this.currentHead = currentHead;
        this.saltStock = saltStock;
        this.biokeroseneStock = biokeroseneStock;
        this.cleanerRole = cleanerRole;
    }

    public void changeHead(Head newHead) {
    }

    public void clean(Lane lane) {
    }

    @Override
    public int getPrice() {
        return 0;
    }
}