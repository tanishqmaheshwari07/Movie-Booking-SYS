package observer;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN: Observer Pattern (Behavioral / Publisher-Subscriber)
 * Component: Observer Interface
 * ============================================================================
 * 
 * Purpose:
 *   Defines the subscriber interface with an `update` callback method.
 *   All notification receivers (CustomerNotifier, AdminNotifier, ManagerNotifier)
 *   implement this interface.
 * 
 * Why it is used:
 *   - Loosely couples the subject (BookingSubject) from specific notification mechanisms.
 *   - Allows adding new types of notifications (SMS, Email, Push) without modifying
 *     the subject or booking system.
 * ============================================================================
 */
public interface Observer {

    /**
     * Receives update notification when a booking state change event occurs.
     *
     * @param booking The confirmed Booking information
     */
    void update(Booking booking);
}