package application.entities;

/**
 * Represents an admin user in the application.
 * With Constructor, Getter and Setter and toString methods
 */
public class Admin {

    private int id;
    private String username;
    private String email;
    private String password;

    /**
     * Default constructor for the Admin class.
     */
    public Admin() {
    }

    /**
     * Parameterized constructor for the Admin class with all fields.
     *
     * @param id       The unique identifier of the admin.
     * @param username The username of the admin.
     * @param email    The email address of the admin.
     * @param password The password of the admin.
     */
    public Admin(int id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor for the Admin class with username and password.
     *
     * @param username The username of the admin.
     * @param password The password of the admin.
     */
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    /**
     * Constructor for the Admin class with username, email, and password.
     *
     * @param username The username of the admin.
     * @param email    The email address of the admin.
     * @param password The password of the admin.
     */
    public Admin(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
