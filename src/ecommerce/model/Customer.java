package ecommerce.model;

import java.util.ArrayList;

public class Customer extends User {

    private String passwordHash; // Stores the hashed version of the customer's password
    private String salt; // A different salt makes the same password produce a different hash


    public Customer(int userId, String name, String email){
        super(userId, name, email);
        passwordHash = "";
        salt = "";
    }
    public Customer(int userId, String name, String email, String passwordHash, String salt){
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
        System.out.println("Customer: " + getName() + " | Email: " + getEmail());
    }
}
