package model;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Concrete Product (Factory Pattern)
 * ============================================================================
 * 
 * Purpose:
 *   Represents 3D Movie tickets with 3D glasses / gear surcharge.
 *   - Base price: Rs. 220 per seat.
 *   - Additional 3D surcharge: Rs. 30 per seat.
 *   - Calculation: (basePrice + 30) * seats.
 * ============================================================================
 */
public class ThreeDMovie extends Movie {

    public ThreeDMovie(String title) {
        super(title, 220);
    }

    @Override
    public String getFormatType() {
        return "3D";
    }

    @Override
    public double calculatePrice(int seats) {
        return (getBasePrice() + 30) * seats;
    }
}