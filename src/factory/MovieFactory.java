package factory;

import model.ImaxMovie;
import model.Movie;
import model.ThreeDMovie;
import model.TwoDMovie;

/**
 * ============================================================================
 * DESIGN PATTERN: Factory Method Pattern (Creational)
 * ============================================================================
 * 
 * Purpose:
 *   Defines an interface/method for creating objects without specifying the exact
 *   class of the object that will be created.
 * 
 * Where it is used:
 *   - MovieFactory is used by MainApp, WebApp, and Booking workflows to instantiate
 *     concrete movie format types (2D, 3D, IMAX) dynamically based on user input.
 * 
 * Why it is used:
 *   - Decouples client code from concrete implementations (TwoDMovie, ThreeDMovie, ImaxMovie).
 *   - Follows the Open/Closed Principle: new movie formats (e.g., 4DX, ScreenX) can be 
 *     added easily with minimal modification to calling code.
 *   - Centralizes pricing and format instantiation rules in one place.
 * ============================================================================
 */
public class MovieFactory {

    /**
     * Factory method to instantiate and return the corresponding Movie object
     * based on the requested format type.
     *
     * @param type  The format of the movie ("2D", "3D", "IMAX")
     * @param title The title/name of the movie
     * @return Concrete Movie subclass instance
     * @throws IllegalArgumentException if format type is not supported
     */
    public static Movie createMovie(String type, String title) {

        switch (type.toUpperCase()) {

            case "2D":
                return new TwoDMovie(title);

            case "3D":
                return new ThreeDMovie(title);

            case "IMAX":
                return new ImaxMovie(title);

            default:
                throw new IllegalArgumentException(
                        "Invalid movie format: " + type + ". Supported formats are: 2D, 3D, IMAX.");
        }
    }
}