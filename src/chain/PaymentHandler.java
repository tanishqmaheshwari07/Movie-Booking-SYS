package chain;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Handler 2 (Chain of Responsibility Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Verifies payment validation and total price calculation.
 *   - Verifies total calculated amount > 0
 * 
 * Flow:
 *   If validation passes -> delegates to the next link (TicketConfirmationHandler).
 *   If validation fails  -> halts the chain and returns false immediately.
 * ============================================================================
 */
public class PaymentHandler extends AbstractBookingHandler {

    @Override
    public boolean handle(Booking booking) {

        System.out.println("[2/3] Verifying payment...");

        if (booking.getTotalAmount() <= 0) {
            System.out.println("Payment failed: Invalid amount.");
            return false;
        }

        System.out.println("Payment verified successfully.");

        // Forward to next handler in chain (TicketConfirmationHandler)
        return handleNext(booking);
    }
}