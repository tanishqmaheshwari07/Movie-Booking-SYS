package model;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Product (Factory Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Represents premium IMAX format movie tickets with a 10% premium experience tax/fee.
 *   - Base price: Rs. 350 per seat.
 *   - Multiplier: 1.10 (10% premium).
 *   - Calculation: basePrice * seats * 1.10.
 * ============================================================================
 */
public class ImaxMovie extends Movie {

    public ImaxMovie(String title) {
        super(title, 350);
    }

    @Override
    public String getFormatType() {
        return "IMAX";
    }

    @Override
    public double calculatePrice(int seats) {
        return getBasePrice() * seats * 1.10;
    }
}