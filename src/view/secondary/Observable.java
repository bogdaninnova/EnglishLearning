package view.secondary;

public interface Observable {
    void addObserver(Observer... obs);
    boolean removeObserver(Observer o);
    void notifyObservers();
}
