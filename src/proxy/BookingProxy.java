package proxy;

import model.Booking;
import model.Movie;
import model.User;
import singleton.BookingSystem;

import java.util.List;

/**
 * ============================================================================
 * DESIGN PATTERN: Protection Proxy Pattern (Structural)
 * ============================================================================
 * 
 * Purpose:
 *   Provides a surrogate or placeholder for another object to control access to it.
 *   Specifically acts as a Protection Proxy that checks whether a user is authenticated
 *   before forwarding sensitive requests (booking tickets, viewing history, canceling)
 *   to the underlying BookingSystem (Singleton).
 * 
 * Where it is used:
 *   - Used in MainApp (Console UI) to guard against unauthenticated operations.
 * 
 * Why it is used:
 *   - Security and Access Control: Prevents unauthenticated users from making bookings
 *     or accessing private user data.
 *   - Separation of Concerns: Keeps authentication validation logic outside the core
 *     booking and persistence algorithms.
 * ============================================================================
 */
public class BookingProxy implements BookingService {

    // Reference to the Real Subject (accessed via Singleton)
    private final BookingSystem bookingSystem;

    // Current authenticated user session
    private User currentUser;

    public BookingProxy() {
        this.bookingSystem = BookingSystem.getInstance();
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    /**
     * Helper check to verify if an active authenticated user session exists.
     */
    public boolean isAuthenticated() {
        return currentUser != null;
    }

    public void logout() {
        this.currentUser = null;
    }

    /**
     * PROXY ACCESS CONTROL:
     * Intercepts bookTicket request; validates authentication before delegating.
     */
    @Override
    public void bookTicket(Movie movie, int seats) {
        if (!isAuthenticated()) {
            System.err.println("ACCESS DENIED: Please log in first to book tickets.");
            return;
        }

        double totalAmount = movie.calculatePrice(seats);
        Booking booking = new Booking(
                0, 
                currentUser.getUserId(), 
                movie.getTitle(), 
                movie.getFormatType(), 
                seats, 
                totalAmount, 
                "PENDING"
        );
        
        // Delegate to the Real Subject (BookingSystem)
        bookingSystem.processBooking(booking);
    }

    /**
     * PROXY ACCESS CONTROL:
     * Intercepts viewBookings request; validates authentication before delegating.
     */
    @Override
    public void viewBookings() {
        if (!isAuthenticated()) {
            System.err.println("ACCESS DENIED: Please log in first to view bookings.");
            return;
        }

        // Delegate retrieval to the Real Subject
        List<Booking> bookings = bookingSystem.getUserBookings(currentUser.getUserId());
        if (bookings.isEmpty()) {
            System.out.println("No booking history found.");
            return;
        }

        System.out.println("\n================ Your Bookings ================");
        System.out.printf("%-10s %-15s %-8s %-6s %-10s %-10s\n", "ID", "Movie", "Type", "Seats", "Amount", "Status");
        System.out.println("---------------------------------------------------------------");
        for (Booking b : bookings) {
            System.out.printf("%-10d %-15s %-8s %-6d Rs.%-7.2f %-10s\n", 
                    b.getBookingId(), b.getMovieName(), b.getMovieType(), b.getSeats(), b.getTotalAmount(), b.getBookingStatus());
        }
    }

    /**
     * PROXY ACCESS CONTROL:
     * Intercepts cancelBooking request; validates authentication before delegating.
     */
    @Override
    public void cancelBooking(int bookingId) {
        if (!isAuthenticated()) {
            System.err.println("ACCESS DENIED: Please log in first to cancel bookings.");
            return;
        }

        // Delegate cancellation to the Real Subject
        boolean updated = bookingSystem.cancelBooking(bookingId, currentUser.getUserId());
        if (updated) {
            System.out.println("Booking #" + bookingId + " status changed to CANCELLED.");
        } else {
            System.err.println("Failed to cancel booking ID #" + bookingId + ". Check if ID is valid.");
        }
    }
}
