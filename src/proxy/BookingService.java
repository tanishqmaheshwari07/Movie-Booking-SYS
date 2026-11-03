package proxy;

import model.Movie;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Subject Interface (Proxy Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Defines the common interface implemented by both the Real Subject (business layer)
 *   and the Proxy (BookingProxy) so that a client can interact with the proxy transparently.
 * ============================================================================
 */
public interface BookingService {
    void bookTicket(Movie movie, int seats);
    void viewBookings();
    void cancelBooking(int bookingId);
}
