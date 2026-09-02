package observer;

import model.Booking;
import java.util.ArrayList;
import java.util.List;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Subject / Observable / Publisher (Observer Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Maintains a list of observers and notifies them automatically of state changes
 *   (e.g., when a new movie ticket booking is confirmed).
 * 
 * Key Operations:
 *   - attach(observer): Subscribes an observer
 *   - detach(observer): Unsubscribes an observer
 *   - notifyObservers(booking): Broadcasts update to all active subscribers
 * ============================================================================
 */
public class BookingSubject {

    // List of registered observers
    private final List<Observer> observers = new ArrayList<>();

    /**
     * Subscribes a new observer to receive booking notifications.
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Unsubscribes an observer from receiving notifications.
     */
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Iterates through all registered observers and triggers their `update` callback.
     *
     * @param booking The confirmed booking object
     */
    public void notifyObservers(Booking booking) {
        for (Observer observer : observers) {
            observer.update(booking);
        }
    }
}