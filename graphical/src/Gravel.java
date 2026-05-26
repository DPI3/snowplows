package src;

/**
 * Az osztály a sáv zúzott kővel fedett állapotát reprezentálja. A kiszórt zuzalék a jég csúszósságát megszünteti,
 * de a söprő és hányó fej a zuzalékot ugyanúgy eltakarítja, mint a havat. A lángszóró, a jégtörő és a só a
 * zuzalékra nem hat. A zuzalék nem tömörödik. Ha hó esik rá, akkor a hó egy idő után befedi, a hó a szokott
 * módon letaposható és így lefagyott jéggé válik.
 */
public class Gravel implements LaneState {

    /** A zúzottkő vastagság. */
    private double thickness;

    /**
     * Alapértelmezett konstruktor, amely 1.0 vastagságú zúzottkő réteget hoz létre.
     */
    public Gravel() {
        this.thickness = 1.0;
    }

    /**
     * Zúzottkő réteg létrehozása megadott vastagsággal.
     *
     * @param thickness a vastagság
     */
    public Gravel(double thickness) {
        this.thickness = thickness;
    }

    /**
     * A zúzottkő vastagságának lekérdezése.
     *
     * @return a vastagság
     */
    public double getThickness() {
        return thickness;
    }

    /**
     * Meghatározza, hogy a zúzottköves réteg járható-e.
     *
     * @return true, mert ez az állapot járható
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Visszaadja a zúzottkő dinamikus súlyát az útvonalkereséshez.
     *
     * @return a dinamikus súly értéke
     */
    @Override
    public double getDynamicWeight() {
        return 1.5;
    }

    /**
     * Kezeli az időjárás változásait zúzottkő állapotban.
     * Ha elegendő hó esik, a sáv állapota vékony vagy mély hóvá változik.
     *
     * @param snowAmount a hó mennyisége tick-ekben mérve
     * @return az új sávállapot az időjárás változása után
     */
    @Override
    public LaneState handleWeatherChange(int snowAmount) {
        if (snowAmount >= 10) {
            return new DeepSnow();
        }

        if (snowAmount > 0) {
            return new ThinSnow();
        }

        return this;
    }
}
