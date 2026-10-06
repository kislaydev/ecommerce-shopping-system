package ecommerce.model;

public abstract class User {
    private int userId;
    private String name;
    private String email;

    public User(int userId, String name, String email){
        // Check that user ID is valid.
        if (userId <= 0) {
            throw new IllegalArgumentException("User ID must be greater than 0");
        }
        // Check that name is not empty.
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        // Check that email is not empty.
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Check that email contains @.
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }


        this.userId = userId;
        this.name = name;
        this.email = email;
    }

    public int getUserId(){
        return userId;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        // Check that name is not empty.
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        this.name = name;
    }
    public String getEmail(){
        return email;
    }
    public void setEmail(String email){
        // Check that email is not empty.
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }

        // Check that email contains @.
        if (!email.contains("@")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        this.email = email;
    }
    public abstract  void displayDetails();
}
