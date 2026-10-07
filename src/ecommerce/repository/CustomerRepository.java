package ecommerce.repository;

import ecommerce.model.Customer; // Customer is used because this repository stores customer accounts
import java.io.IOException; // IOException is used because file reading and writing can fail
import java.util.ArrayList; // ArrayList stores multiple Customer objects
import java.util.Base64; // Base64 converts name and email into safe text for file storage

public class CustomerRepository {
    private static final String FNAME = "customers.txt";
    public ArrayList<Customer> loadCustomers() throws IOException {
        ArrayList<Customer> customers = new ArrayList<Customer>();
        String data = FileManager.readFromFile(FNAME);
        if (data.isEmpty()) {
            return customers;
        }
        String[] lines = data.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i].trim();
            if (line.isEmpty()) {
                continue;
            }
            String[] parts = line.split("\\|");
            if (parts.length == 5) {
                int userId = Integer.parseInt(parts[0]);
                String name = decode(parts[1]);
                String email = decode(parts[2]);
                String passwordHash = parts[3];
                String salt = parts[4];
                Customer customer = new Customer(userId, name, email, passwordHash, salt);
                customers.add(customer);
            }
        }
        return customers;
    }

    public void saveCustomers(ArrayList<Customer> customers) throws IOException {
        String data = "";
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            String line = customer.getUserId() + "|" + encode(customer.getName()) + "|" + encode(customer.getEmail()) + "|" + customer.getPasswordHash() + "|" + customer.getSalt();
            data = data + line + "\n";
        }
        FileManager.saveToFile(FNAME, data);
    }

    // Base64 is used so name and email do not interfere with the | separator
    private String encode(String value) {
        return Base64.getEncoder().encodeToString(value.getBytes());
    }

    private String decode(String value) {
        return new String(Base64.getDecoder().decode(value));
    }
}