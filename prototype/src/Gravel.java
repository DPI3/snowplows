package prototype.src;

/**
 * Zúzalékkal borított sávállapot.
 */
public class Gravel implements LaneState {

    private double thickness;

    public Gravel() {
        this.thickness = 1.0;
    }

    public Gravel(double thickness) {
        this.thickness = thickness;
    }

    public double getThickness() {
        return thickness;
    }

    @Override
    public boolean isPassable() {
        return true;
    }

    @Override
    public double getDynamicWeight() {
        return 1.5;
    }

    @Override
    public LaneState handleWeatherChange(int snowamount) {
        return this;
    }
}