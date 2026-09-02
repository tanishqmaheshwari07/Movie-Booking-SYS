package model;

/**
 * ============================================================================
 * DESIGN PATTERN ROLE: Product (Factory Pattern) / Base Domain Model
 * ============================================================================
 * 
 * Purpose:
 *   Abstract base class representing a generic movie product created by the Factory.
 *   Defines the common attributes (title, basePrice) and abstract methods that
 *   every concrete format (2D, 3D, IMAX) must implement.
 * ============================================================================
 */
public abstract class Movie {

    private String title;
    private double basePrice;

    public Movie(String title, double basePrice) {
        this.title = title;
        this.basePrice = basePrice;
    }

    public String getTitle() {
        return title;
    }

    public double getBasePrice() {
        return basePrice;
    }

    /**
     * Returns the format description (e.g. "2D", "3D", "IMAX").
     */
    public abstract String getFormatType();

    /**
     * Calculates the total ticket cost based on format-specific pricing formulas.
     *
     * @param seats Number of tickets to purchase
     * @return Total calculated price
     */
    public abstract double calculatePrice(int seats);
}