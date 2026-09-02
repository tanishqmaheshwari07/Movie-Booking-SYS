package chain;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Handler 3 (Chain of Responsibility Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Finalizes ticket confirmation in the chain.
 *   - Updates the booking status from "PENDING" to "CONFIRMED".
 * 
 * Flow:
 *   Marks ticket as CONFIRMED and passes control along to complete the chain.
 * ============================================================================
 */
public class TicketConfirmationHandler extends AbstractBookingHandler {

    @Override
    public boolean handle(Booking booking) {

        System.out.println("[3/3] Confirming ticket...");

        booking.setBookingStatus("CONFIRMED");

        System.out.println("Ticket confirmed successfully.");

        // Forward to next handler in chain (if any)
        return handleNext(booking);
    }
}