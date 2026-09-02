package singleton;

import chain.BookingHandler;
import chain.PaymentHandler;
import chain.SeatAvailabilityHandler;
import chain.TicketConfirmationHandler;
import dao.BookingDAO;
import model.Booking;
import observer.AdminNotifier;
import observer.BookingSubject;
import observer.CustomerNotifier;
import observer.ManagerNotifier;

import java.util.List;

/**
 * ============================================================================
 * DESIGN PATTERN: Singleton Pattern (Creational)
 * ============================================================================
 * 
 * Purpose:
 *   Ensures that only ONE single instance of the BookingSystem exists in the
 *   entire application runtime and provides a global access point to it.
 * 
 * Where it is used:
 *   - Used across the application (via BookingProxy and WebApp) to coordinate 
 *     the central booking pipeline.
 * 
 * Why it is used:
 *   - Prevents inconsistent states or race conditions across different booking sessions.
 *   - Acts as the central orchestrator / facade that brings together:
 *       1. Chain of Responsibility Pattern (seat check -> payment -> confirmation)
 *       2. Observer Pattern (notifying Customer, Admin, and Manager)
 *       3. DAO Pattern (persisting booking records into SQLite)
 * ============================================================================
 */
public class BookingSystem {

    // Eager/Lazy Singleton instance reference
    private static BookingSystem instance;

    // Data Access Object for database operations (DAO Pattern)
    private final BookingDAO bookingDAO;

    // Subject/Publisher for event notifications (Observer Pattern)
    private final BookingSubject bookingSubject;

    /**
     * Private constructor ensures no external class can instantiate BookingSystem with `new`.
     * Initializes DAOs and registers Observer listeners.
     */
    private BookingSystem() {

        this.bookingDAO = new BookingDAO();
        this.bookingSubject = new BookingSubject();

        // --------------------------------------------------------------------
        // OBSERVER PATTERN REGISTRATION:
        // Attach concrete observers to the subject
        // --------------------------------------------------------------------
        bookingSubject.attach(new CustomerNotifier());
        bookingSubject.attach(new AdminNotifier());
        bookingSubject.attach(new ManagerNotifier());
    }

    /**
     * Thread-safe global accessor method (synchronized) to retrieve the unique instance.
     *
     * @return The single BookingSystem instance
     */
    public static synchronized BookingSystem getInstance() {

        if (instance == null) {
            instance = new BookingSystem();
        }

        return instance;
    }

    /**
     * ========================================================================
     * DESIGN PATTERNS IN ACTION:
     * 1. Chain of Responsibility: Validates seat availability, payment, and status.
     * 2. DAO Pattern: Inserts the confirmed booking into SQLite.
     * 3. Observer Pattern: Broadcasts the confirmed booking event to all observers.
     * ========================================================================
     *
     * @param booking The booking request to process
     * @return true if the booking passes all checks and is persisted, false otherwise
     */
    public boolean processBooking(Booking booking) {

        // --------------------------------------------------------------------
        // CHAIN OF RESPONSIBILITY PATTERN:
        // Instantiate individual validation handlers
        // --------------------------------------------------------------------
        BookingHandler seatCheck = new SeatAvailabilityHandler();
        BookingHandler paymentCheck = new PaymentHandler();
        BookingHandler confirmation = new TicketConfirmationHandler();

        // Assemble the chain: Seat Check -> Payment Check -> Ticket Confirmation
        seatCheck.setNext(paymentCheck);
        paymentCheck.setNext(confirmation);

        // Execute the chain starting from the first handler
        boolean success = seatCheck.handle(booking);

        if (!success) {
            return false;
        }

        // --------------------------------------------------------------------
        // DAO PATTERN:
        // Persist the confirmed booking into the SQLite database
        // --------------------------------------------------------------------
        int bookingId = bookingDAO.insertBooking(booking);

        if (bookingId == -1) {
            System.out.println("Booking could not be saved.");
            return false;
        }

        // Create the final confirmed booking object with the generated database ID
        Booking confirmedBooking = new Booking(
                bookingId,
                booking.getUserId(),
                booking.getMovieName(),
                booking.getMovieType(),
                booking.getSeats(),
                booking.getTotalAmount(),
                booking.getBookingStatus()
        );

        System.out.println();
        System.out.println("--- Notifications ---");

        // --------------------------------------------------------------------
        // OBSERVER PATTERN:
        // Broadcast the event to all registered observers (Customer, Admin, Manager)
        // --------------------------------------------------------------------
        bookingSubject.notifyObservers(confirmedBooking);

        return true;
    }

    /**
     * Retrieves all bookings associated with a specific user using the DAO pattern.
     *
     * @param userId The user's ID
     * @return List of Booking objects
     */
    public List<Booking> getUserBookings(int userId) {
        return bookingDAO.getBookingsByUserId(userId);
    }

    /**
     * Cancels an existing booking for a user using the DAO pattern.
     *
     * @param bookingId The ID of the booking to cancel
     * @param userId    The ID of the user requesting cancellation
     * @return true if successfully updated, false otherwise
     */
    public boolean cancelBooking(int bookingId, int userId) {
        return bookingDAO.updateBookingStatus(bookingId, userId, "CANCELLED");
    }
}