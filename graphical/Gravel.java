package src;

/**
 * Az osztály a sáv zúzott kővel fedett állapotát reprezentálja. A kiszórt zuzalék a jég csúszósságát megszünteti,
 * de a söprő és hányó fej a zuzalékot ugyanúgy eltakarítja, mint a havat. A lángszóró, a jégtörő és a só a 
 * zuzalékra nem hat. A zuzalék nem tömörödik. Ha hó esik rá, akkor a hó egy idő után befedi,  a hó a szokott 
 * módon letaposható és így lefagyott jéggé válik.
 */
public class Gravel implements LaneState {

    /** A zúzottkő vastagság. */
    private double thickness;

    public Gravel() {
        this.thickness = 1.0;
    }

    /** 
     * Zúzottkő réteg létrehozása megadott vastagsággal 
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
     * Meghatarozza, hogy a zúzottköves réteg járható-e
     *
     * @return true, mert ez az állapot  járhato
     */
    @Override
    public boolean isPassable() {
        return true;
    }

    /**
     * Visszaadja a vekony ho dinamikus sulyat.
     *
     * @return a dinamikus suly erteke
     */
    @Override
    public double getDynamicWeight() {
        return 1.5;
    }

     /**
     * Kezeli az idojaras valtozasait vekony ho allapotban.
     *
     * @param snowAmount a ho mennyisege tick-ekben merve
     * @return az uj lane state az idojaras valtozasa utan
     */
    @Override
    public LaneState handleWeatherChange(int snowamount) {
        return this;
    }
}