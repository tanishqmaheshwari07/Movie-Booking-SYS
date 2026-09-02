package observer;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Observer 1 (Observer Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Handles customer-facing notifications when their movie booking is confirmed.
 * ============================================================================
 */
public class CustomerNotifier implements Observer {

    @Override
    public void update(Booking booking) {
        System.out.println(
                "[CUSTOMER NOTIFICATION] " +
                "Your ticket for " +
                booking.getMovieName() +
                " has been booked.");
    }
}