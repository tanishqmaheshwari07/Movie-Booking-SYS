package model;

/**
 * ============================================================================
 * DOMAIN MODEL: User Entity
 * ============================================================================
 * 
 * Purpose:
 *   Represents an authenticated system user containing their credentials and primary key ID.
 * ============================================================================
 */
public class User {

    private int userId;
    private String username;
    private String password;

    public User(int userId, String username, String password) {
        this.userId = userId;
        this.username = username;
        this.password = password;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}