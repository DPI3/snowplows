package graphical;

import java.util.ArrayList;
import java.util.List;

/**
 * Megfigyelhető modell osztály, amely az Observer tervezési mintát valósítja meg.
 * A megfigyelők (observers) értesítést kapnak a modell változásairól.
 */
public class ModelObservable {
    /** A regisztrált megfigyelők listája. */
    private final List<ModelObserver> observers = new ArrayList<>();

    /**
     * Hozzáad egy megfigyelőt a listához, ha az még nem szerepel benne.
     *
     * @param o a hozzáadandó megfigyelő; {@code null} érték esetén nem történik semmi
     */
    public void addObserver(ModelObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    /**
     * Eltávolít egy megfigyelőt a listából.
     *
     * @param o az eltávolítandó megfigyelő
     */
    public void removeObserver(ModelObserver o) {
        observers.remove(o);
    }

    /**
     * Értesíti az összes regisztrált megfigyelőt a modell változásáról.
     */
    public void notifyObservers() {
        for (ModelObserver o : observers) {
            o.onModelChanged();
        }
    }
}
