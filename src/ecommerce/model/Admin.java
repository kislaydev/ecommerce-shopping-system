
package ecommerce.model;

public class Admin extends User {

    private String passwordHash;
    private String salt;

    // Retain the existing constructor for compatibility.
    public Admin(int userId, String name, String email) {
        super(userId, name, email);
        this.passwordHash = "";
        this.salt = "";
    }

    // Constructor used when loading or creating an authenticated admin.
    public Admin(int userId, String name, String email,
                 String passwordHash, String salt) {
        super(userId, name, email);
        this.passwordHash = passwordHash;
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    @Override
    public void displayDetails() {
        System.out.println(
            "Admin: " + getName() + " | Email: " + getEmail()
        );
    }
}
