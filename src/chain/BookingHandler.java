package chain;

import model.Booking;

/**
 * ============================================================================
 * DESIGN PATTERN: Chain of Responsibility Pattern (Behavioral)
 * Component: Handler Interface
 * ============================================================================
 * 
 * Purpose:
 *   Declares an interface for processing booking requests and linking handlers in a chain.
 *   Each handler in the chain decides either to process the request or pass it along.
 * 
 * Where it is used:
 *   - Used in the booking pipeline (SeatAvailabilityHandler -> PaymentHandler -> TicketConfirmationHandler).
 * 
 * Why it is used:
 *   - Decouples the sender of a booking request from its sequential validation logic.
 *   - Allows adding/removing validation steps (e.g., CouponHandler, AgeRestrictionHandler)
 *     without modifying existing handlers or client code.
 * ============================================================================
 */
public interface BookingHandler {

    /**
     * Sets the next successor handler in the processing chain.
     *
     * @param nextHandler The next BookingHandler
     */
    void setNext(BookingHandler nextHandler);

    /**
     * Handles the current step of the booking verification.
     *
     * @param booking The booking request to validate and process
     * @return true if this step (and all subsequent steps) succeed, false otherwise
     */
    boolean handle(Booking booking);
}