package tests;

/**
 * Egységes interfész a tesztosztályok számára. Minden {@code testN} osztály
 * implementálja, így a {@link MainRunner} reflexióval példányosíthatja és
 * egységesen futtathatja őket.
 */
public interface TestCase {
    void run();
}