package observer;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Observer 2 (Observer Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Handles system administrator notifications to log and audit new transactions.
 * ============================================================================
 */
public class AdminNotifier implements Observer {

    @Override
    public void update(Booking booking) {
        System.out.println(
                "[ADMIN NOTIFICATION] " +
                "New booking ID: " +
                booking.getBookingId());
    }
}