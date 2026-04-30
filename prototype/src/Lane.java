package src;

/**
 * A Lane osztály az úthálózat gráfjának egy irányított élét reprezentálja.
 * Ez az osztály a "gazdája" az útszakasz fizikai állapotának (hó, jég, baleset).
 */
public class Lane {

    private String name;
    private Node source;
    private Node destination;
    private Road parentRoad;

    private int snowValue;
    private double snowThickness;
    private double iceThickness;
    private double gravelThickness;

    private boolean hasAccident;
    private int waitTimer;

    private LaneState currentState;

    public Lane() {
        // Alapértelmezett állapot a tiszta út
        this.currentState = new Clear();
    }

    public Lane(Road road) {
        this.parentRoad = road;
    }

    public Lane(String name, Node source, Node destination) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.currentState = new Clear();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSnowThickness() {
        return snowThickness;
    }

    public void setSnowThickness(double snowThickness) {
        this.snowThickness = snowThickness;
    }

    public double getIceThickness() {
        return iceThickness;
    }

    public void setIceThickness(double iceThickness) {
        this.iceThickness = iceThickness;
    }

    public Road getParentRoad() {
        return parentRoad;
    }

    public void setParentRoad(Road road) {
        this.parentRoad = road;
    }

    public void setGravelThickness(double thickness) {
        this.gravelThickness = thickness;
    }

    public double getGravelThickness() {
        return gravelThickness;
    }

    /**
     * Megváltoztatja a sáv jelenlegi állapotát a paraméterben kapott új állapotra.
     */
    public void setState(LaneState newState) {
        this.currentState = newState;
    }

    /**
     * Kiértékeli a sáv aktuális állapotát, és visszaadja, hogy járható-e.
     */
    public boolean isPassable() {
        if (currentState != null) {
            return currentState.isPassable();
        }
        return false;
    }

    /**
     * Kiszámítja a sáv súlyozását az útvonalkereséshez.
     */
    public double getDynamicWeight() {
        if (currentState != null) {
            return currentState.getDynamicWeight();
        }
        return 1.0;
    }

    /**
     * Visszaadja a sáv leírására szolgáló állapot-objektumot.
     */
    public LaneState getLaneState() {
        return currentState;
    }

    /**
     * Módosítja a sáv állapotát az időjárási viszonyok hatásának megfelelően.
     */
    public void applyWeather(int snowamount) {
        if (currentState != null) {
            this.currentState = currentState.handleWeatherChange(snowamount);
        }
    }

    /**
     * Visszaadja, hogy a sávon van-e baleset.
     */
    public boolean hasAccident() {
        return hasAccident;
    }
    
    public void setHasAccident(boolean hasAccident) {
        this.hasAccident = hasAccident;
    }

    /**
     * Módosítja a hó, jég vagy zúzalék mennyiségét a sávon havazás vagy hókotrás hatására.
     * A PDF specifikációja alapján leprogramozva.
     */
    public void change(int amount) {
        if (currentState instanceof DeepSnow || currentState instanceof ThinSnow) {
            snowThickness = Math.max(0, snowThickness - amount);
            snowValue = Math.max(0, snowValue - amount);
            if (snowThickness <= 0) {
                setState(new Clear());
            }
        } else if (currentState instanceof IceSheet || currentState instanceof BrokenIce) {
            iceThickness = Math.max(0, iceThickness - amount);
            if (iceThickness <= 0) {
                setState(new Clear());
            }
        } else if (currentState instanceof Gravel) {
            Gravel g = (Gravel) currentState;

            double newThickness = Math.max(0, g.getThickness() - amount);

            if (newThickness <= 0) {
                setState(new IceSheet());
            } else {
                setState(new Gravel(newThickness));
            }
        }
    }
    
    // Szükséges getterek a működéshez
    public Node getDestination() {
        return destination;
    }
    
    public double getLength() {
        return 100.0; // Példa hossz
    }

    public Node getSource() {
        return source;
    }

    public void setSource(Node source) {
        this.source = source;
    }

    public void setDestination(Node destination) {
        this.destination = destination;
    }
}