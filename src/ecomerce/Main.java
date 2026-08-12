package ecomerce;

import ecommerce.model.Customer;
import ecommerce.model.Admin;
import ecommerce.model.User;

public class Main {
    public static void main(String[] args){
        User customer = new Customer(1, "Riya", "riya@email.com");
        User admin = new Admin(2, "Aman", "aman@emil.com");
        customer.displayDetails();
        admin.displayDetails();
    }
}
