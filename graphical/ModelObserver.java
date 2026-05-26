package graphical;

/**
 * Megfigyelő interfész a modell változásainak figyeléséhez.
 * Az ezt megvalósító osztályok értesítést kapnak, amikor a modell állapota megváltozik.
 */
public interface ModelObserver {
    /**
     * Akkor hívódik meg, amikor a megfigyelt modell állapota megváltozik.
     */
    void onModelChanged();
}
