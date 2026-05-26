package src;

/**
 * A Lane osztály az úthálózat gráfjának egy irányított élét reprezentálja.
 * Ez az osztály a "gazdája" az útszakasz fizikai állapotának (hó, jég, baleset).
 */
public class Lane {

    /** A sáv neve. */
    private String name;

    /** A sáv kiindulási pontja. */
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

    /** Jelzi, hogy a sáv sózva van-e. */
    private boolean salted;

    /** A só hatásának hátralévő időtartama. */
    private int saltTimer;

    /**
     * Paraméter nélküli konstruktor, amely tiszta állapotú sávot hoz létre.
     */
    public Lane() {
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
     * Sáv létrehozása csomópontokból és névvel.
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
     * Visszaadja a sáv nevét.
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
     * Visszaadja a sáv hórétegének vastagságát.
     *
     * @return a sáv hóréteg vastagsága
     */
    public double getSnowThickness() {
        return snowThickness;
    }

    /**
     * Beállítja a sáv hórétegének vastagságát.
     *
     * @param snowThickness a sáv új hóréteg vastagsága
     */
    public void setSnowThickness(double snowThickness) {
        this.snowThickness = snowThickness;
    }

    /**
     * Visszaadja a sáv jégrétegének vastagságát.
     *
     * @return a sáv jégréteg vastagsága
     */
    public double getIceThickness() {
        return iceThickness;
    }

    /**
     * Beállítja a sáv jégrétegének vastagságát.
     *
     * @param iceThickness a sáv új jégréteg vastagsága
     */
    public void setIceThickness(double iceThickness) {
        this.iceThickness = iceThickness;
    }

    /**
     * Visszaadja az út objektumot, amelyhez ez a sáv tartozik.
     *
     * @return az út objektum, amelyhez ez a sáv tartozik
     */
    public Road getParentRoad() {
        return parentRoad;
    }

    /**
     * Beállítja a sávhoz tartozó szülő utat.
     *
     * @param road az új szülő út objektum
     */
    public void setParentRoad(Road road) {
        this.parentRoad = road;
    }

    /**
     * Beállítja a sáv zúzottkő rétegének vastagságát.
     *
     * @param thickness a sáv új zúzottkő réteg vastagsága
     */
    public void setGravelThickness(double thickness) {
        this.gravelThickness = thickness;
    }

    /**
     * Visszaadja a sáv zúzottkő rétegének vastagságát.
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
        if (hasAccident) return false;

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
     * Visszaadja a sáv aktuális állapotát reprezentáló objektumot.
     *
     * @return a sáv állapot-objektum
     */
    public LaneState getLaneState() {
        return currentState;
    }

    /**
     * Módosítja a sáv állapotát az időjárási viszonyok hatásának megfelelően.
     * Ha a sáv sózva van, az időjárás nem hat rá.
     *
     * @param snowamount a hó mennyisége
     */
    public void applyWeather(int snowamount) {
        if (salted) {
            return;
        }

        if (currentState != null) {
            this.currentState = currentState.handleWeatherChange(snowamount);
        }
    }

    /**
     * Visszaadja, hogy a sávon van-e baleset.
     *
     * @return true, ha van baleset; false, ha nincs
     */
    public boolean hasAccident() {
        return hasAccident;
    }

    /**
     * Beállítja a baleseti állapotot a sávon.
     *
     * @param hasAccident true, ha baleset történt; false, ha nincs baleset
     */
    public void setHasAccident(boolean hasAccident) {
        this.hasAccident = hasAccident;
    }

    /**
     * Módosítja a hó, jég vagy zúzalék mennyiségét a sávon havazás vagy hókotrás hatására.
     *
     * @param amount a változás mértéke
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
                setState(new Clear());
            } else {
                setState(new Gravel(newThickness));
            }
        }
    }

    /**
     * Visszaadja a sáv célcsomópontját.
     *
     * @return a cél csomópont
     */
    public Node getDestination() {
        return destination;
    }

    /**
     * Visszaadja a sáv hosszát.
     *
     * @return a sáv hossza
     */
    public double getLength() {
        return 100.0;
    }

    /**
     * Visszaadja a sáv kiindulási csomópontját.
     *
     * @return a kiindulási csomópont
     */
    public Node getSource() {
        return source;
    }

    /**
     * Beállítja a sáv kiindulási csomópontját.
     *
     * @param source az új kiindulási csomópont
     */
    public void setSource(Node source) {
        this.source = source;
    }

    /**
     * Beállítja a sáv célcsomópontját.
     *
     * @param destination az új célcsomópont
     */
    public void setDestination(Node destination) {
        this.destination = destination;
    }

    /**
     * Sót alkalmaz a sávra megadott időtartamra. A sáv tiszta állapotba kerül,
     * és az időtartam lejártáig védett az időjárás hatásaival szemben.
     *
     * @param duration a só hatásának időtartama tickekben
     */
    public void applySalt(int duration) {
        this.salted = true;
        this.saltTimer = duration;
        this.currentState = new Clear();
        this.snowThickness = 0;
        this.iceThickness = 0;
    }

    /**
     * Visszaadja, hogy a sáv sózva van-e.
     *
     * @return true, ha a sáv sózva van; false egyébként
     */
    public boolean isSalted() {
        return salted;
    }

    /**
     * Egy tick-kel csökkenti a só hatásának hátralévő időtartamát.
     * Ha az időtartam lejárt, a sáv sózott állapota megszűnik.
     */
    public void tickSalt() {
        if (!salted) return;

        saltTimer--;

        if (saltTimer <= 0) {
            salted = false;
            saltTimer = 0;
        }
    }

    /**
     * Eltávolítja a sávról a mozgatható anyagot (vékony hó, mély hó, törött jég vagy zúzottkő),
     * és a sávot tiszta állapotba állítja.
     *
     * @return az eltávolított anyag állapot-objektuma, vagy null, ha nem volt mozgatható anyag
     */
    public LaneState removeMovableMaterial() {
        if (currentState instanceof ThinSnow ||
            currentState instanceof DeepSnow ||
            currentState instanceof BrokenIce ||
            currentState instanceof Gravel) {

            LaneState removed = currentState;
            currentState = new Clear();
            snowThickness = 0;
            iceThickness = 0;
            gravelThickness = 0;
            return removed;
        }

        return null;
    }

    /**
     * Anyagot helyez el a sávon, ha az jelenleg tiszta állapotban van.
     *
     * @param material az elhelyezendő anyag állapot-objektuma
     */
    public void placeMaterial(LaneState material) {
        if (material == null) return;

        if (currentState instanceof Clear) {
            currentState = material;
        }
    }
}
