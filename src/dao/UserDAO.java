package dao;

import database.DBConnection;
import model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * ============================================================================
 * DESIGN PATTERN: Data Access Object (DAO) Pattern (Architectural / Structural)
 * ============================================================================
 * 
 * Purpose:
 *   Encapsulates all database operations, queries, and mappings for User entities.
 *   Shields business logic and controllers from raw SQL statements and JDBC code.
 * 
 * Where it is used:
 *   - Used during user login authentication in MainApp and WebApp.
 * 
 * Why it is used:
 *   - Separation of Concerns: Keeps database persistence separated from domain models.
 *   - Maintainability: If database schema or JDBC driver changes, only the DAO changes.
 * ============================================================================
 */
public class UserDAO {

    /**
     * Authenticates a user by validating username and password against the SQLite database.
     *
     * @param username The input username
     * @param password The input password
     * @return User object if credentials are valid, null otherwise
     */
    public User authenticate(String username, String password) {

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet result = statement.executeQuery();

            if (result.next()) {
                return new User(
                        result.getInt("user_id"),
                        result.getString("username"),
                        result.getString("password")
                );
            }

        } catch (Exception e) {
            System.out.println("Login database error: " + e.getMessage());
        }

        return null;
    }
}