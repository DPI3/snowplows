package src;

/**
 * A Lane osztály az úthálózat gráfjának egy irányított élét reprezentálja.
 * Ez az osztály a "gazdája" az útszakasz fizikai állapotának (hó, jég, baleset).
 */
public class Lane {

    /** Az sáv neve. */
    private String name;

    /** Az sáv kiindulási pontja. */
    private Node source;

    /** A sáv célja. */
    private Node destination;

    /** Az az út objektum, amelyhez ez a sáv tartozik. */
    private Road parentRoad;

    /** A sávon felhalmozódott hó mennyiségi mutatója. */
    private int snowValue;

    /** A hóréteg aktuális fizikai vastagsága. */
    private double snowThickness;

    /** A jégréteg aktuális fizikai vastagsága. */
    private double iceThickness;

    /** A zúzottkő réteg aktuális fizikai vastagsága. */
    private double gravelThickness;

    /** Jelzi, ha a sávon baleset történt, ami akadályozza a haladást. */
    private boolean hasAccident;

    /** A baleset vagy elakadás esetén a kényszerű várakozási időt mérő számláló. */
    private int waitTimer;

    /** A sáv aktuális állapotát reprezentáló objektum. */
    private LaneState currentState;

    public Lane() {
        // Alapértelmezett állapot a tiszta út
        this.currentState = new Clear();
    }

    /**
     * Sáv létrehozása a hozzá tartozó úthoz.
     * 
     * @param road a sávhoz tartozó út
     */
    public Lane(Road road) {
        this.parentRoad = road;
    }

    /**
     * Sáv létrehozása csomópontokból.
     * 
     * @param name a sáv neve
     * @param source a sáv kiinduló csomópontja
     * @param destination a sáv célállomása
     */
    public Lane(String name, Node source, Node destination) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.currentState = new Clear();
    }

    /**
     * Visszadja a sáv nevét.
     * 
     * @return a sáv neve
     */
    public String getName() {
        return name;
    }

    /**
     * Beállítja a sáv nevét.
     * 
     * @param name a sáv új neve
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Visszadja a sáv hórétegének vastagságát.
     * 
     * @return a sáv hóréteg vastagsága
     */
    public double getSnowThickness() {
        return snowThickness;
    }

    /**
     * Beállítja a sáv hórétegének vastagságát.
     * 
     * @param name a sáv új hóréteg vastagsága
     */
    public void setSnowThickness(double snowThickness) {
        this.snowThickness = snowThickness;
    }

     /**
     * Visszadja a sáv jégrétegének vastagságát.
     * 
     * @return a sáv jégréteg vastagsága
     */
    public double getIceThickness() {
        return iceThickness;
    }

    /**
     * Beállítja a sáv jégrétegének vastagságát.
     * 
     * @param name a sáv új jégréteg vastagsága
     */
    public void setIceThickness(double iceThickness) {
        this.iceThickness = iceThickness;
    }

    /**
     * Visszaadja  az út objektumot, amelyhez ez a sáv tartozik.
     * 
     * @return az út objektum, amelyhez ez a sáv tartozik
     */
    public Road getParentRoad() {
        return parentRoad;
    }

    public void setParentRoad(Road road) {
        this.parentRoad = road;
    }

    /**
     * Beállítja a sáv zúzottkő rétegének vastagságát.
     * 
     * @param name a sáv új zúzottkő réteg vastagsága
     */
    public void setGravelThickness(double thickness) {
        this.gravelThickness = thickness;
    }

     /**
     * Visszadja a sáv zúzottkő rétegének vastagságát.
     * 
     * @return a sáv zúzottkő rétegének vastagsága
     */
    public double getGravelThickness() {
        return gravelThickness;
    }

    /**
     * Megváltoztatja a sáv jelenlegi állapotát a paraméterben kapott új állapotra.
     * 
     * @param newState az új állapot
     */
    public void setState(LaneState newState) {
        this.currentState = newState;
    }

    /**
     * Kiértékeli a sáv aktuális állapotát, és visszaadja, hogy járható-e.
     * 
     * @return true, ha járható; false, ha járhatatlan
     */
    public boolean isPassable() {
        if (currentState != null) {
            return currentState.isPassable();
        }
        return false;
    }

    /**
     * Kiszámítja a sáv súlyozását az útvonalkereséshez.
     * 
     * @return a sáv súlya
     */
    public double getDynamicWeight() {
        if (currentState != null) {
            return currentState.getDynamicWeight();
        }
        return 1.0;
    }

    /**
     * Visszaadja a sáv leírására szolgáló állapot-objektumot.
     * 
     * @return a sáv leírására szolgáló állapot-objektum
     */
    public LaneState getLaneState() {
        return currentState;
    }

    /**
     * Módosítja a sáv állapotát az időjárási viszonyok hatásának megfelelően.
     * 
     * @param snowamount a hó mennyisége
     */
    public void applyWeather(int snowamount) {
        if (currentState != null) {
            this.currentState = currentState.handleWeatherChange(snowamount);
        }
    }

    /**
     * Visszaadja, hogy a sávon van-e baleset.
     * 
     * @return true, ha van baleset; false, ha nincsen baleset
     */
    public boolean hasAccident() {
        return hasAccident;
    }
    
    /**
     * A balesetet vagy annak hiányát jelző érték beállítása.
     * 
     * @param hasAccident a baleset új értéke
     */
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
    
     /**
     * Lekérdezi a sáv célját.
     * 
     * @return a cél
     */
    public Node getDestination() {
        return destination;
    }
    

    /**
     * Visszaadja a sáv hosszát.
     * 
     * @return a hossz
     */
    public double getLength() {
        return 100.0; // Példa hossz
    }


    /**
     * Lekérdezi a sáv kiindulási pontját.
     * 
     * @return a kiindulási pont
     */
    public Node getSource() {
        return source;
    }

    /**
     * Az indulási pont beállítása
     * 
     * @param source az új indulási pont
     */
    public void setSource(Node source) {
        this.source = source;
    }

    /**
     * A cél beállítása
     * 
     * @param destination az új cél
     */
    public void setDestination(Node destination) {
        this.destination = destination;
    }
}