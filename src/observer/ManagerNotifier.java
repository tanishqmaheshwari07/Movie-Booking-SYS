package observer;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Observer 3 (Observer Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Handles theater manager notifications to track theater occupancy and seat counts.
 * ============================================================================
 */
public class ManagerNotifier implements Observer {

    @Override
    public void update(Booking booking) {
        System.out.println(
                "[THEATER MANAGER NOTIFICATION] " +
                booking.getSeats() +
                " seats booked for " +
                booking.getMovieName());
    }
}