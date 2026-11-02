package chain;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Base Handler (Chain of Responsibility Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Provides default boilerplate implementation for chaining and forwarding requests
 *   to the next handler in the sequence.
 * 
 * Concrete subclasses only need to implement their specific business validation logic
 * and call `handleNext(booking)`.
 * ============================================================================
 */
public abstract class AbstractBookingHandler
        implements BookingHandler {

    // Next handler in the chain
    private BookingHandler next;

    @Override
    public void setNext(BookingHandler nextHandler) {
        this.next = nextHandler;
    }

    /**
     * Helper method to pass the booking to the next handler if present.
     * If at the end of the chain, returns true (all steps completed successfully).
     */
    protected boolean handleNext(Booking booking) {

        if (next == null) {
            return true;
        }

        return next.handle(booking);
    }
}
