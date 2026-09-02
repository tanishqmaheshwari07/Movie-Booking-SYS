package chain;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Handler 1 (Chain of Responsibility Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Validates seat availability constraints before proceeding with the booking.
 *   - Verifies seats > 0
 *   - Verifies seats <= MAX_SEATS (60)
 * 
 * Flow:
 *   If validation passes -> delegates to the next link (PaymentHandler).
 *   If validation fails  -> halts the chain and returns false immediately.
 * ============================================================================
 */
public class SeatAvailabilityHandler extends AbstractBookingHandler {

    private static final int MAX_SEATS = 60;

    @Override
    public boolean handle(Booking booking) {

        System.out.println("[1/3] Checking seat availability...");

        if (booking.getSeats() <= 0) {
            System.out.println("Booking failed: Number of seats must be greater than 0.");
            return false;
        }

        if (booking.getSeats() > MAX_SEATS) {
            System.out.println("Booking failed: Maximum 60 seats allowed.");
            return false;
        }

        System.out.println("Seats are available.");

        // Forward to next handler in chain (PaymentHandler)
        return handleNext(booking);
    }
}