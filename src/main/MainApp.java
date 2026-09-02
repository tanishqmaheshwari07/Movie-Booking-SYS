package main;

import database.DBConnection;
import dao.UserDAO;
import factory.MovieFactory;
import model.Movie;
import model.User;
import proxy.BookingProxy;

import java.util.Scanner;

/**
 * ============================================================================
 * CONSOLE CLIENT APPLICATION: MainApp
 * ============================================================================
 * 
 * Demonstrates the integration of all design patterns in the system:
 * 
 * 1. PROXY PATTERN (BookingProxy):
 *    - Guards sensitive operations (Book Ticket, View Bookings, Cancel Booking)
 *      requiring an active user authentication session before execution.
 * 
 * 2. FACTORY METHOD PATTERN (MovieFactory):
 *    - Instantiates specific Movie formats (2D, 3D, IMAX) dynamically based on user choices.
 * 
 * 3. SINGLETON PATTERN (BookingSystem):
 *    - Central booking coordinator accessed globally to maintain state consistency.
 * 
 * 4. CHAIN OF RESPONSIBILITY PATTERN (BookingHandler chain):
 *    - Triggered during booking to validate seat counts, calculate and verify payments,
 *      and confirm booking status.
 * 
 * 5. OBSERVER PATTERN (BookingSubject -> Observers):
 *    - Broadcasts notifications to Customer, Admin, and Manager upon confirmed booking.
 * 
 * 6. DAO PATTERN (UserDAO, BookingDAO):
 *    - Handles authentication and SQLite database queries/updates.
 * ============================================================================
 */
public class MainApp {
    private static final Scanner scanner = new Scanner(System.in);

    // Structural: Proxy controls authentication and delegates to Singleton BookingSystem
    private static final BookingProxy bookingProxy = new BookingProxy();

    // Data Access Object: DAO Pattern for database operations on users
    private static final UserDAO userDAO = new UserDAO();

    public static void main(String[] args) {
        // Ensure the SQLite database and tables exist before any operations
        DBConnection.initializeDatabase();

        while (true) {
            System.out.println("\n======== Movie Ticket Booking ========");
            System.out.println("1. Login");
            System.out.println("2. Book Ticket");
            System.out.println("3. View Bookings");
            System.out.println("4. Cancel Booking");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            int choice = readIntInput();

            switch (choice) {
                case 1:
                    handleLogin();
                    break;
                case 2:
                    handleBookTicket();
                    break;
                case 3:
                    // Proxy checks authentication and delegates to Singleton -> DAO
                    bookingProxy.viewBookings();
                    break;
                case 4:
                    handleCancelBooking();
                    break;
                case 5:
                    System.out.println("Thank you for using Movie Ticket Booking System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * DAO PATTERN USAGE:
     * Uses UserDAO to authenticate username and password against SQLite.
     * Sets the session in the BookingProxy.
     */
    private static void handleLogin() {
        System.out.print("Enter Username: ");
        String username = scanner.nextLine().trim();
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();

        // DAO Pattern: Authenticate user
        User user = userDAO.authenticate(username, password);
        if (user != null) {
            // Proxy Pattern: Set authenticated user session
            bookingProxy.setCurrentUser(user);
            System.out.println("Login successful! Welcome, " + user.getUsername() + ".");
        } else {
            System.err.println("Authentication failed. Invalid username or password.");
        }
    }

    /**
     * FACTORY, PROXY, CHAIN OF RESPONSIBILITY & OBSERVER PATTERNS:
     * - Proxy: Validates authentication
     * - Factory: Creates the Movie object based on format selection
     * - Proxy -> Singleton: Passes booking through the validation Chain and triggers Observers
     */
    private static void handleBookTicket() {
        // Proxy Pattern Check: Intercept unauthenticated access
        if (!bookingProxy.isAuthenticated()) {
            System.err.println("ACCESS DENIED: Please log in first.");
            return;
        }

        System.out.println("\n--- Select Movie ---");
        System.out.println("1. Avengers");
        System.out.println("2. Interstellar");
        System.out.println("3. Spider-Man");
        System.out.println("4. Avatar");
        System.out.print("Choice: ");
        int movieChoice = readIntInput();

        String movieTitle = switch (movieChoice) {
            case 1 -> "Avengers";
            case 2 -> "Interstellar";
            case 3 -> "Spider-Man";
            case 4 -> "Avatar";
            default -> null;
        };

        if (movieTitle == null) {
            System.out.println("Invalid movie selection.");
            return;
        }

        System.out.println("\n--- Select Format ---");
        System.out.println("1. 2D");
        System.out.println("2. 3D");
        System.out.println("3. IMAX");
        System.out.print("Choice: ");
        int formatChoice = readIntInput();

        String format = switch (formatChoice) {
            case 1 -> "2D";
            case 2 -> "3D";
            case 3 -> "IMAX";
            default -> null;
        };

        if (format == null) {
            System.out.println("Invalid format selection.");
            return;
        }

        System.out.print("Enter number of seats: ");
        int seats = readIntInput();

        // FACTORY METHOD PATTERN:
        // Dynamically instantiate TwoDMovie, ThreeDMovie, or ImaxMovie
        Movie movie = MovieFactory.createMovie(format, movieTitle);
        double totalPrice = movie.calculatePrice(seats);

        System.out.printf("Total calculated price: Rs. %.2f\n", totalPrice);
        System.out.print("Confirm booking? (Y/N): ");
        String confirm = scanner.nextLine().trim();

        if (confirm.equalsIgnoreCase("Y")) {
            // PROXY PATTERN -> CHAIN OF RESPONSIBILITY -> OBSERVER NOTIFICATIONS
            bookingProxy.bookTicket(movie, seats);
        } else {
            System.out.println("Booking order aborted.");
        }
    }

    /**
     * PROXY PATTERN USAGE:
     * Intercepts cancellation request and verifies active user session.
     */
    private static void handleCancelBooking() {
        if (!bookingProxy.isAuthenticated()) {
            System.err.println("ACCESS DENIED: Please log in first.");
            return;
        }

        System.out.print("Enter Booking ID to cancel: ");
        int bookingId = readIntInput();
        bookingProxy.cancelBooking(bookingId);
    }

    private static int readIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
