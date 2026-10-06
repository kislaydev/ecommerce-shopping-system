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

    private int nextOrderId = 5001;
    private int nextPaymentId = 1001;
    private int nextInvoiceId = 9001;

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
        productBrowser = new ProductBrowser();
        cart = new Cart();
    }

    public void start() {

        int choice;

        do {

            System.out.println("\n===== E-Commerce Shopping System =====");
            System.out.println("1. View All Products");
            System.out.println("2. Search Product");
            System.out.println("3. Browse by Category");
            System.out.println("4. Add Product to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Checkout");
            System.out.println("7. Exit");
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
                        checkout();
                        break;

                    case 7:
                        System.out.println(
                                "Thank you for using the E-Commerce Shopping System."
                        );
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (IOException | InvalidProductException | InvalidQuantityException e) {

                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 7);
    }

    private void addProductToCart()
            throws IOException, InvalidProductException, InvalidQuantityException {

        System.out.print("Enter product ID: ");
        int productId = scanner.nextInt();

        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        ArrayList<Product> products = productBrowser.getProducts();

        Product selectedProduct = null;

        for (Product product : products) {

            if (product.getProductId() == productId) {
                selectedProduct = product;
                break;
            }
        }

        if (selectedProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        if (quantity > selectedProduct.getQuantity()) {
            System.out.println("Not enough stock available.");
            return;
        }

        cart.addItem(selectedProduct, quantity);

        System.out.println(
                selectedProduct.getProductName()
                + " added to cart successfully."
        );
    }

    private void checkout()
            throws InvalidQuantityException {

        ArrayList<CartItem> cartItems = cart.getItems();

        if (cartItems.size() == 0) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }

        System.out.println("\n========== CHECKOUT ==========");

        // Get customer details from user
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        Customer customer = new Customer(
                1,
                name,
                email
        );

        // Create order
        Order order = new Order(
                nextOrderId,
                customer
        );

        nextOrderId++;

        // Copy cart items into order
        for (CartItem item : cartItems) {

            order.addItem(
                    item.getProduct(),
                    item.getQuantity()
            );
        }

        System.out.println("\n----- ORDER DETAILS -----");
        order.displayOrder();

        // Apply discount
        Discount discount = new Discount(10);

        double orderTotal = order.calculateTotal();
        double discountAmount = discount.getDiscountAmount(orderTotal);
        double finalTotal = discount.applyDiscount(orderTotal);

        System.out.println("\n----- DISCOUNT -----");
        System.out.println("Order Total: ₹" + orderTotal);
        System.out.println("Discount: " + discount.getPercentage() + "%");
        System.out.println("Discount Amount: ₹" + discountAmount);
        System.out.println("Final Total: ₹" + finalTotal);

        // Select payment method
        System.out.println("\n----- PAYMENT -----");
        System.out.println("1. Card Payment");
        System.out.println("2. UPI Payment");
        System.out.print("Choose payment method: ");

        int paymentChoice = scanner.nextInt();
        scanner.nextLine();

        Payment payment;

        if (paymentChoice == 1) {

            System.out.print("Enter card number: ");
            String cardNumber = scanner.nextLine();

            payment = new CardPayment(
                    nextPaymentId,
                    finalTotal,
                    cardNumber
            );

            nextPaymentId++;

        } else if (paymentChoice == 2) {

            System.out.print("Enter UPI ID: ");
            String upiId = scanner.nextLine();

            payment = new UPIPayment(
                    nextPaymentId,
                    finalTotal,
                    upiId
            );

            nextPaymentId++;

        } else {

            System.out.println("Invalid payment method.");
            return;
        }

        // Process payment
        payment.makePayment();

        // Generate invoice
        Invoice invoice = new Invoice(
                nextInvoiceId,
                order,
                discount
        );

        nextInvoiceId++;

        invoice.generateInvoice();

        System.out.println("\nCheckout completed successfully.");
        System.out.println("==============================");
    }
}