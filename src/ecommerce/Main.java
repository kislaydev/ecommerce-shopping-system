package ecommerce;

import ecommerce.model.Admin;
import ecommerce.model.Customer;
import ecommerce.model.User;
import ecommerce.model.Product;
import ecommerce.model.Category;
import ecommerce.model.Cart;
import ecommerce.model.Order;

import ecommerce.exception.InvalidProductException;
import ecommerce.exception.InvalidQuantityException;

import ecommerce.model.Payment;
import ecommerce.model.CardPayment;
import ecommerce.model.UPIPayment;

public class Main {

    public static void main(String[] args) {

        // Creating users
        Customer customer = new Customer(
                1,
                "Riya",
                "riya@email.com"
        );

        Admin admin = new Admin(
                2,
                "Aman",
                "aman@email.com"
        );

        customer.displayDetails();
        admin.displayDetails();

        try {

            // Creating a category
            Category category = new Category(
                    1,
                    "Electronics"
            );

            // Creating a product
            Product product = new Product(
                    1,
                    "Laptop",
                    50000,
                    10,
                    category
            );

            // Creating a cart
            Cart cart = new Cart();

            // Adding product to cart
            cart.addItem(product, 2);

            // Displaying cart
            cart.viewCart();

            // ---------------- ORDER TEST ----------------

            System.out.println("\n\n===== ORDER TEST =====");

            // Creating an order for the customer
            Order order = new Order(
                    5001,
                    customer
            );

            // Adding product to the order
            order.addItem(product, 2);

            // Displaying order details
            order.displayOrder();

            // ---------------- PAYMENT TEST ----------------

            System.out.println("\n\n===== PAYMENT TEST =====");

            Payment payment1 = new CardPayment(1001, order.calculateTotal(), "1234567890123456");

            payment1.makePayment();

            Payment payment2 = new UPIPayment(1002, order.calculateTotal(), "riya@upi");

            payment2.makePayment();

        } catch (InvalidProductException | InvalidQuantityException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}