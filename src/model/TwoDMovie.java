package model;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Product (Factory Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Represents standard 2D Movie tickets.
 *   - Base price: Rs. 150 per seat.
 *   - Calculation: basePrice * seats.
 * ============================================================================
 */
public class TwoDMovie extends Movie {

    public TwoDMovie(String title) {
        super(title, 150);
    }

    @Override
    public String getFormatType() {
        return "2D";
    }

    @Override
    public double calculatePrice(int seats) {
        return getBasePrice() * seats;
    }
}