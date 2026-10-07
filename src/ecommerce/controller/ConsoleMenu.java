package ecommerce.controller;

import ecommerce.exception.InvalidProductException;
import ecommerce.exception.InvalidQuantityException;
import ecommerce.model.CardPayment;
import ecommerce.model.Cart;
import ecommerce.model.CartItem;
import ecommerce.model.Customer;
import ecommerce.model.Order;
import ecommerce.model.Payment;
import ecommerce.model.Product;
import ecommerce.model.UPIPayment;
import ecommerce.service.AuthenticationService;
import ecommerce.service.Discount;
import ecommerce.service.Invoice;
import ecommerce.service.ProductBrowser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class ConsoleMenu {

    private Scanner scanner;
    private ProductBrowser productBrowser;
    private Cart cart;
    private AuthenticationService authenticationService;
    private Customer loggedInCustomer;

    private int nextOrderId = 5001;
    private int nextPaymentId = 1001;
    private int nextInvoiceId = 9001;

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
        productBrowser = new ProductBrowser();
        cart = new Cart();
        authenticationService = new AuthenticationService();
        loggedInCustomer = null;
    }

    public void start() {
        int choice;

        do {
            System.out.println("\n===== E-Commerce Shopping System =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        registerCustomer();
                        break;

                    case 2:
                        loginCustomer();
                        break;

                    case 3:
                        System.out.println(
                                "Thank you for using the E-Commerce Shopping System."
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid choice. Please try again."
                        );
                }

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 3);
    }

    private void registerCustomer() throws IOException {

        System.out.println("\n========== REGISTER ==========");

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Customer customer =
                authenticationService.register(
                        name,
                        email,
                        password
                );

        if (customer == null) {
            System.out.println(
                    "An account with this email already exists."
            );
            return;
        }

        System.out.println("Registration successful.");
        System.out.println(
                "Your Customer ID: " + customer.getUserId()
        );
    }

    private void loginCustomer() throws IOException {

        System.out.println("\n========== LOGIN ==========");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Customer customer =
                authenticationService.login(
                        email,
                        password
                );

        if (customer == null) {
            System.out.println(
                    "Invalid email or password."
            );
            return;
        }

        loggedInCustomer = customer;
        cart = new Cart();

        System.out.println(
                "Login successful. Welcome, "
                        + loggedInCustomer.getName()
                        + "!"
        );

        customerMenu();
    }

    private void customerMenu() {

        int choice;

        do {
            System.out.println("\n===== Customer Menu =====");
            System.out.println("1. View All Products");
            System.out.println("2. Search Product");
            System.out.println("3. Browse by Category");
            System.out.println("4. Add Product to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Remove Product from Cart");
            System.out.println("7. Checkout");
            System.out.println("8. Logout");

            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (choice) {

                    case 1:
                        productBrowser.displayAllProducts();
                        break;

                    case 2:
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();

                        productBrowser.searchByName(name);
                        break;

                    case 3:
                        System.out.print("Enter category name: ");
                        String category = scanner.nextLine();

                        productBrowser.displayByCategory(category);
                        break;

                    case 4:
                        addProductToCart();
                        break;

                    case 5:
                        cart.viewCart();
                        break;

                    case 6:
                        removeProductFromCart();
                        break;

                    case 7:
                        checkout();
                        break;

                    case 8:
                        loggedInCustomer = null;
                        cart = new Cart();

                        System.out.println(
                                "Logged out successfully."
                        );
                        break;

                    default:
                        System.out.println(
                                "Invalid choice. Please try again."
                        );
                }

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (InvalidProductException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (InvalidQuantityException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 8);
    }

    private void addProductToCart()
            throws IOException,
            InvalidProductException,
            InvalidQuantityException {

        System.out.print("Enter product ID: ");

        int productId = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Product> products =
                productBrowser.getProducts();

        Product selectedProduct = null;

        // Search for the product using its ID.
        for (int i = 0; i < products.size(); i++) {

            Product product = products.get(i);

            if (product.getProductId() == productId) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("1. Add 1 item");
        System.out.println("2. Enter quantity");
        System.out.print("Choose Option: ");

        int quantityChoice = scanner.nextInt();
        scanner.nextLine();

        if (quantityChoice == 1) {

            // Method overloading
            // Adds exactly 1 item.
            cart.addItem(selectedProduct);

            System.out.println(
                    selectedProduct.getProductName()
                            + " added to cart successfully."
            );

        } else if (quantityChoice == 2) {

            System.out.print("Enter quantity: ");

            int quantity = scanner.nextInt();
            scanner.nextLine();

            // Check whether enough stock is available.
            if (quantity > selectedProduct.getQuantity()) {
                System.out.println(
                        "Not enough stock available."
                );
                return;
            }

            // Method overloading
            // Adds specified quantity.
            cart.addItem(
                    selectedProduct,
                    quantity
            );

            System.out.println(
                    selectedProduct.getProductName()
                            + " added to cart successfully."
            );

        } else {

            System.out.println("Invalid choice.");
        }
    }

    private void removeProductFromCart() {

        ArrayList<CartItem> cartItems =
                cart.getItems();

        if (cartItems.size() == 0) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n----- Shopping Cart -----");
        cart.viewCart();

        System.out.print("Enter product ID to remove: ");

        int productId = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;

        for (int i = 0; i < cartItems.size(); i++) {

            CartItem item = cartItems.get(i);

            if (item.getProduct().getProductId() == productId) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println(
                    "Product not found in cart."
            );
            return;
        }

        cart.removeItem(productId);

        System.out.println(
                "Product removed from cart successfully."
        );
    }

    private void checkout()
            throws IOException,
            InvalidProductException,
            InvalidQuantityException {

        ArrayList<CartItem> cartItems =
                cart.getItems();

        if (cartItems.size() == 0) {
            System.out.println(
                    "Cart is empty. Cannot checkout."
            );
            return;
        }

        System.out.println(
                "\n========== CHECKOUT =========="
        );

        // Use the customer who is currently logged in.
        Customer customer = loggedInCustomer;

        // Create a new order.
        Order order =
                new Order(
                        nextOrderId,
                        customer
                );

        nextOrderId++;

        // Add all cart items to the order.
        for (int i = 0; i < cartItems.size(); i++) {

            CartItem item = cartItems.get(i);

            order.addItem(
                    item.getProduct(),
                    item.getQuantity()
            );
        }

        System.out.println(
                "\n----- ORDER DETAILS -----"
        );

        order.displayOrder();

        // Apply a 10% discount.
        Discount discount =
                new Discount(10);

        double orderTotal =
                order.calculateTotal();

        double discountAmount =
                discount.getDiscountAmount(
                        orderTotal
                );

        double finalTotal =
                discount.applyDiscount(
                        orderTotal
                );

        System.out.println(
                "\n----- DISCOUNT -----"
        );

        System.out.println(
                "Order Total: ₹" + orderTotal
        );

        System.out.println(
                "Discount: "
                        + discount.getPercentage()
                        + "%"
        );

        System.out.println(
                "Discount Amount: ₹"
                        + discountAmount
        );

        System.out.println(
                "Final Total: ₹"
                        + finalTotal
        );

        // Select payment method.
        System.out.println(
                "\n----- PAYMENT -----"
        );

        System.out.println("1. Card Payment");
        System.out.println("2. UPI Payment");

        System.out.print(
                "Choose payment method: "
        );

        int paymentChoice =
                scanner.nextInt();

        scanner.nextLine();

        Payment payment;

        if (paymentChoice == 1) {

            System.out.print(
                    "Enter card number: "
            );

            String cardNumber =
                    scanner.nextLine();

            payment =
                    new CardPayment(
                            nextPaymentId,
                            finalTotal,
                            cardNumber
                    );

            nextPaymentId++;

        } else if (paymentChoice == 2) {

            System.out.print(
                    "Enter UPI ID: "
            );

            String upiId =
                    scanner.nextLine();

            payment =
                    new UPIPayment(
                            nextPaymentId,
                            finalTotal,
                            upiId
                    );

            nextPaymentId++;

        } else {

            System.out.println(
                    "Invalid payment method."
            );

            return;
        }

        // Process the payment.
        payment.makePayment();

        // Get the latest product list.
        ArrayList<Product> products =
                productBrowser.getProducts();

        // Reduce stock after successful payment.
        for (int i = 0; i < cartItems.size(); i++) {

            CartItem item =
                    cartItems.get(i);

            for (int j = 0; j < products.size(); j++) {

                Product product =
                        products.get(j);

                if (product.getProductId()
                        == item.getProduct().getProductId()) {

                    // Reduce product quantity
                    // by purchased quantity.
                    int newQuantity =
                            product.getQuantity()
                                    - item.getQuantity();

                    product.setQuantity(
                            newQuantity
                    );

                    break;
                }
            }
        }

        // Save updated stock to the file.
        productBrowser.saveProducts(
                products
        );

        // Generate the invoice.
        Invoice invoice =
                new Invoice(
                        nextInvoiceId,
                        order,
                        discount
                );

        nextInvoiceId++;

        invoice.generateInvoice();

        System.out.println(
                "\nCheckout completed successfully."
        );

        System.out.println(
                "=============================="
        );

        // Clear the cart after successful checkout.
        cart = new Cart();
    }
}