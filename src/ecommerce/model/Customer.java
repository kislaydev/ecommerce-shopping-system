package ecommerce.model;

public class Customer extends User{
    public Customer(int userId, String name, String email){
        super(userId, name, email);
    }
    @Override
    public void displayDetails() {
        System.out.println("Customer: " + getName() + " | Email: " + getEmail());
    }
}
