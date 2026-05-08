package src;

import java.util.ArrayList;
import java.util.List;

public class ModelObservable {
    private final List<ModelObserver> observers = new ArrayList<>();

    public void addObserver(ModelObserver o) {
        if (o != null && !observers.contains(o)) {
            observers.add(o);
        }
    }

    public void removeObserver(ModelObserver o) {
        observers.remove(o);
    }

    public void notifyObservers() {
        for (ModelObserver o : observers) {
            o.onModelChanged();
        }
    }
}
