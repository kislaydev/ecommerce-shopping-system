package ecommerce.service;

import ecommerce.model.Customer; // Customer is used to create and manage customer accounts
import ecommerce.repository.CustomerRepository; // CustomerRepository is used to save and load customer accounts
import java.io.IOException; // IOException is used because customer data is stored in a file
import java.util.ArrayList; // ArrayList stores the existing customer accounts

public class AuthenticationService {
    private CustomerRepository customerRepository;
    private PasswordHasher passwordHasher;
    public AuthenticationService() {
        customerRepository = new CustomerRepository();
        passwordHasher = new PasswordHasher();
    }
    public Customer register(String name, String email, String password) throws IOException {
        ArrayList<Customer> customers = customerRepository.loadCustomers();
        // Check if another account already uses the same email
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            if (customer.getEmail().equalsIgnoreCase(email)) {
                return null;
            }
        }
        // Start customer IDs from 1001
        int newUserId = 1001;
        // Find the next available customer ID
        for (int i = 0; i < customers.size(); i++) {
            if (customers.get(i).getUserId() >= newUserId) {
                newUserId = customers.get(i).getUserId() + 1;
            }
        }
        // Generate a random salt for the new password
        String salt = passwordHasher.generateSalt();
        // Create the password hash using the password and salt
        String passwordHash = passwordHasher.hashPassword(password, salt);
        Customer customer = new Customer(newUserId, name, email, passwordHash, salt);
        customers.add(customer);

        // Save the new customer account into the file
        customerRepository.saveCustomers(customers);

        return customer;
    }
    public Customer login(String email, String password) throws IOException {
        ArrayList<Customer> customers = customerRepository.loadCustomers();
        // Search for the customer using the email
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            if (customer.getEmail().equalsIgnoreCase(email)) {
                // Hash the entered password using the customer's stored salt
                String passwordHash = passwordHasher.hashPassword(password, customer.getSalt());
                // Check whether the entered password matches the stored hash
                if (passwordHash.equals(customer.getPasswordHash())) {
                    return customer;
                }
                return null;
            }
        }
        return null;
    }
}