/**
 * A Buyable interfesz a megvasarolhato elemek kozos szerzodeset irja le.
 *
 * Az interfeszt megvalosito objektumok rendelkeznek arral,
 * amely alapjan a boltban megvasarolhatok.
 */
public interface Buyable{
    /**
     * Visszaadja az elem arat.
     *
     * @return az elem ara
     */
    int getPrice();
}