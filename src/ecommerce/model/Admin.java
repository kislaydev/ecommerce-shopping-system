package ecommerce.model;

public class Admin extends User{
    public Admin(int userId, String name, String email){
        super(userId, name, email);
    }
    @Override
    public void displayDetails() {
        System.out.println("Admin: " + getName() + " | Email: " + getEmail());
    }
}
