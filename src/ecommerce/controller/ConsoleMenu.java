
package ecommerce.controller;

import ecommerce.exception.InvalidProductException;
import ecommerce.exception.InvalidQuantityException;
import ecommerce.model.Admin;
import ecommerce.model.CardPayment;
import ecommerce.model.Cart;
import ecommerce.model.CartItem;
import ecommerce.model.Category;
import ecommerce.model.Customer;
import ecommerce.model.Order;
import ecommerce.model.Payment;
import ecommerce.model.Product;
import ecommerce.model.UPIPayment;
import ecommerce.service.AdminAuthenticationService;
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
    private AdminAuthenticationService adminAuthenticationService;
    private Customer loggedInCustomer;

    private int nextOrderId = 5001;
    private int nextPaymentId = 1001;
    private int nextInvoiceId = 9001;

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
        productBrowser = new ProductBrowser();
        cart = new Cart();
        authenticationService = new AuthenticationService();
        adminAuthenticationService = new AdminAuthenticationService();
        loggedInCustomer = null;
    }

    // ==================== MAIN MENU ====================

    public void start() {
        int choice = 0;

        do {
            System.out.println("\n===== E-Commerce Shopping System =====");
            System.out.println("1. Register (Customer)");
            System.out.println("2. Login (Customer)");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        registerCustomer();
                        break;

                    case 2:
                        loginCustomer();
                        break;

                    case 3:
                        adminLogin();
                        break;

                    case 4:
                        System.out.println(
                            "Thank you for using the E-Commerce Shopping System."
                        );
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");

            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 4);
    }

    // ==================== CUSTOMER REGISTRATION ====================

    private void registerCustomer() throws IOException {
        System.out.println("\n========== CUSTOMER REGISTER ==========");

        System.out.print("Enter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Customer customer = authenticationService.register(
            name, email, password
        );

        if (customer == null) {
            System.out.println(
                "An account with this email already exists."
            );
            return;
        }

        System.out.println("Registration successful.");
        System.out.println("Your Customer ID: " + customer.getUserId());
    }

    // ==================== CUSTOMER LOGIN ====================

    private void loginCustomer() throws IOException {
        System.out.println("\n========== CUSTOMER LOGIN ==========");

        System.out.print("Enter your email: ");
        String email = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        Customer customer = authenticationService.login(email, password);

        if (customer == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        loggedInCustomer = customer;
        cart = new Cart();

        System.out.println(
            "Login successful. Welcome, " + loggedInCustomer.getName() + "!"
        );

        customerMenu();
    }

    // ==================== ADMIN LOGIN ====================

    private void adminLogin() throws IOException {
        System.out.println("\n========== ADMIN LOGIN ==========");

        System.out.print("Enter admin email: ");
        String email = scanner.nextLine();

        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        Admin admin = adminAuthenticationService.login(email, password);

        if (admin == null) {
            System.out.println("Invalid email or password.");
            return;
        }

        System.out.println(
            "Admin login successful. Welcome, " + admin.getName() + "!"
        );

        adminMenu();
    }

    // ==================== ADMIN PANEL ====================

    private void adminMenu() {
        int choice = 0;

        do {
            System.out.println("\n========== ADMIN PANEL ==========");
            System.out.println("1. View All Products");
            System.out.println("2. Add Product");
            System.out.println("3. Edit Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case 1:
                        productBrowser.displayAllProducts();
                        break;

                    case 2:
                        addProduct();
                        break;

                    case 3:
                        editProduct();
                        break;

                    case 4:
                        deleteProduct();
                        break;

                    case 5:
                        System.out.println("Admin logged out successfully.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println(
                    "Invalid number. Please enter a valid number."
                );

            } catch (IOException e) {
                System.out.println("File error: " + e.getMessage());

            } catch (InvalidProductException e) {
                System.out.println("Product error: " + e.getMessage());

            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }

        } while (choice != 5);
    }

    // ==================== ADD PRODUCT ====================

    private void addProduct()
            throws IOException, InvalidProductException {

        System.out.println("\n========== ADD PRODUCT ==========");

        ArrayList<Product> products = productBrowser.getProducts();

        System.out.print("Enter new product ID: ");
        int productId = Integer.parseInt(scanner.nextLine().trim());

        if (productId <= 0) {
            System.out.println("Product ID must be greater than zero.");
            return;
        }

        if (findProduct(products, productId) != null) {
            System.out.println(
                "A product with this ID already exists. Use a unique ID."
            );
            return;
        }

        System.out.print("Enter product name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }

        System.out.print("Enter product price: ");
        double price = Double.parseDouble(scanner.nextLine().trim());

        if (!Double.isFinite(price) || price < 0) {
            System.out.println("Price must be a valid non-negative number.");
            return;
        }

        System.out.print("Enter product quantity: ");
        int quantity = Integer.parseInt(scanner.nextLine().trim());

        if (quantity < 0) {
            System.out.println("Quantity cannot be negative.");
            return;
        }

        System.out.print("Enter category ID: ");
        int categoryId = Integer.parseInt(scanner.nextLine().trim());

        if (categoryId <= 0) {
            System.out.println("Category ID must be greater than zero.");
            return;
        }

        System.out.print("Enter category name: ");
        String categoryName = scanner.nextLine().trim();

        if (categoryName.isEmpty()) {
            System.out.println("Category name cannot be empty.");
            return;
        }

        Category category = new Category(categoryId, categoryName);

        Product product = new Product(
            productId,
            name,
            price,
            quantity,
            category
        );

        products.add(product);

        productBrowser.saveProducts(products);

        System.out.println("Product added successfully!");
        System.out.println(
            "Added: " + product.getProductName()
            + " (ID: " + product.getProductId() + ")"
        );
    }

    // ==================== EDIT PRODUCT ====================

    private void editProduct()
            throws IOException, InvalidProductException {

        System.out.println("\n========== EDIT PRODUCT ==========");

        ArrayList<Product> products = productBrowser.getProducts();

        if (products.isEmpty()) {
            System.out.println("No products available to edit.");
            return;
        }

        displayProductList(products);

        System.out.print("\nEnter the product ID to edit: ");
        int productId = Integer.parseInt(scanner.nextLine().trim());

        Product product = findProduct(products, productId);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("\nSelected product:");
        displayProduct(product);

        System.out.println(
            "\nPress Enter without typing anything to keep the current value."
        );

        System.out.print(
            "New product name [" + product.getProductName() + "]: "
        );
        String name = scanner.nextLine().trim();

        if (!name.isEmpty()) {
            product.setProductName(name);
        }

        System.out.print(
            "New price [" + product.getPrice() + "]: "
        );
        String priceInput = scanner.nextLine().trim();

        if (!priceInput.isEmpty()) {
            double price = Double.parseDouble(priceInput);

            if (!Double.isFinite(price) || price < 0) {
                System.out.println(
                    "Price must be a valid non-negative number."
                );
                return;
            }

            product.setPrice(price);
        }

        System.out.print(
            "New quantity [" + product.getQuantity() + "]: "
        );
        String quantityInput = scanner.nextLine().trim();

        if (!quantityInput.isEmpty()) {
            int quantity = Integer.parseInt(quantityInput);

            if (quantity < 0) {
                System.out.println("Quantity cannot be negative.");
                return;
            }

            product.setQuantity(quantity);
        }

        System.out.print(
            "New category ID ["
            + product.getCategory().getCategoryId() + "]: "
        );
        String categoryIdInput = scanner.nextLine().trim();

        System.out.print(
            "New category name ["
            + product.getCategory().getCategoryName() + "]: "
        );
        String categoryNameInput = scanner.nextLine().trim();

        if (!categoryIdInput.isEmpty() || !categoryNameInput.isEmpty()) {
            int categoryId = categoryIdInput.isEmpty()
                ? product.getCategory().getCategoryId()
                : Integer.parseInt(categoryIdInput);

            String categoryName = categoryNameInput.isEmpty()
                ? product.getCategory().getCategoryName()
                : categoryNameInput;

            if (categoryId <= 0) {
                System.out.println(
                    "Category ID must be greater than zero."
                );
                return;
            }

            if (categoryName.trim().isEmpty()) {
                System.out.println("Category name cannot be empty.");
                return;
            }

            product.setCategory(new Category(categoryId, categoryName));
        }

        productBrowser.saveProducts(products);

        System.out.println("Product updated successfully!");
        displayProduct(product);
    }

    // ==================== DELETE PRODUCT ====================

    private void deleteProduct()
            throws IOException, InvalidProductException {

        System.out.println("\n========== DELETE PRODUCT ==========");

        ArrayList<Product> products = productBrowser.getProducts();

        if (products.isEmpty()) {
            System.out.println("No products available to delete.");
            return;
        }

        displayProductList(products);

        System.out.print("\nEnter the product ID to delete: ");
        int productId = Integer.parseInt(scanner.nextLine().trim());

        Product product = findProduct(products, productId);

        if (product == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("\nSelected product:");
        displayProduct(product);

        System.out.print(
            "Are you sure you want to delete this product? (Y/N): "
        );
        String confirmation = scanner.nextLine().trim();

        if (!confirmation.equalsIgnoreCase("Y")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        products.remove(product);

        productBrowser.saveProducts(products);

        System.out.println("Product deleted successfully.");
    }

    // ==================== PRODUCT HELPERS ====================

    private Product findProduct(
            ArrayList<Product> products,
            int productId) {

        for (Product product : products) {
            if (product.getProductId() == productId) {
                return product;
            }
        }

        return null;
    }

    private void displayProductList(ArrayList<Product> products) {
        System.out.println("\n----- Product List -----");

        for (Product product : products) {
            displayProduct(product);
        }

        System.out.println("------------------------");
    }

    private void displayProduct(Product product) {
        System.out.println(
            "ID: " + product.getProductId()
            + " | Name: " + product.getProductName()
            + " | Price: ₹" + product.getPrice()
            + " | Quantity: " + product.getQuantity()
            + " | Category: " + product.getCategory().getCategoryName()
        );
    }

    // ==================== CUSTOMER MENU ====================

    private void customerMenu() {
        int choice = 0;

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

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());

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
                        System.out.println("Logged out successfully.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");

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

    // ==================== ADD TO CART ====================

    private void addProductToCart()
            throws IOException, InvalidProductException,
            InvalidQuantityException {

        System.out.print("Enter product ID: ");
        int productId = Integer.parseInt(scanner.nextLine().trim());

        ArrayList<Product> products = productBrowser.getProducts();
        Product selectedProduct = findProduct(products, productId);

        if (selectedProduct == null) {
            System.out.println("Product not found.");
            return;
        }

        System.out.println("1. Add 1 item");
        System.out.println("2. Enter quantity");
        System.out.print("Choose option: ");

        int quantityChoice = Integer.parseInt(scanner.nextLine().trim());

        if (quantityChoice == 1) {
            if (selectedProduct.getQuantity() < 1) {
                System.out.println("This product is out of stock.");
                return;
            }

            cart.addItem(selectedProduct);
            System.out.println(
                selectedProduct.getProductName()
                + " added to cart successfully."
            );

        } else if (quantityChoice == 2) {
            System.out.print("Enter quantity: ");
            int quantity = Integer.parseInt(scanner.nextLine().trim());

            if (quantity <= 0) {
                System.out.println("Quantity must be greater than zero.");
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

        } else {
            System.out.println("Invalid choice.");
        }
    }

    // ==================== REMOVE FROM CART ====================

    private void removeProductFromCart() {
        ArrayList<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty.");
            return;
        }

        System.out.println("\n----- Shopping Cart -----");
        cart.viewCart();

        System.out.print("Enter product ID to remove: ");
        int productId = Integer.parseInt(scanner.nextLine().trim());

        boolean found = false;

        for (CartItem item : cartItems) {
            if (item.getProduct().getProductId() == productId) {
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Product not found in cart.");
            return;
        }

        cart.removeItem(productId);
        System.out.println("Product removed from cart successfully.");
    }

    // ==================== CHECKOUT ====================

    private void checkout()
            throws IOException, InvalidProductException,
            InvalidQuantityException {

        ArrayList<CartItem> cartItems = cart.getItems();

        if (cartItems.isEmpty()) {
            System.out.println("Cart is empty. Cannot checkout.");
            return;
        }

        System.out.println("\n========== CHECKOUT ==========");

        Customer customer = loggedInCustomer;
        Order order = new Order(nextOrderId++, customer);

        for (CartItem item : cartItems) {
            order.addItem(item.getProduct(), item.getQuantity());
        }

        System.out.println("\n----- ORDER DETAILS -----");
        order.displayOrder();

        Discount discount = new Discount(10);
        double orderTotal = order.calculateTotal();
        double discountAmount = discount.getDiscountAmount(orderTotal);
        double finalTotal = discount.applyDiscount(orderTotal);

        System.out.println("\n----- DISCOUNT -----");
        System.out.println("Order Total: ₹" + orderTotal);
        System.out.println("Discount: " + discount.getPercentage() + "%");
        System.out.println("Discount Amount: ₹" + discountAmount);
        System.out.println("Final Total: ₹" + finalTotal);

        System.out.println("\n----- PAYMENT -----");
        System.out.println("1. Card Payment");
        System.out.println("2. UPI Payment");
        System.out.print("Choose payment method: ");

        int paymentChoice = Integer.parseInt(scanner.nextLine().trim());
        Payment payment;

        if (paymentChoice == 1) {
            System.out.print("Enter card number: ");
            String cardNumber = scanner.nextLine();

            payment = new CardPayment(
                nextPaymentId++, finalTotal, cardNumber
            );

        } else if (paymentChoice == 2) {
            System.out.print("Enter UPI ID: ");
            String upiId = scanner.nextLine();

            payment = new UPIPayment(
                nextPaymentId++, finalTotal, upiId
            );

        } else {
            System.out.println("Invalid payment method.");
            return;
        }

        payment.makePayment();

        ArrayList<Product> products = productBrowser.getProducts();

        for (CartItem item : cartItems) {
            for (Product product : products) {
                if (product.getProductId()
                        == item.getProduct().getProductId()) {

                    product.setQuantity(
                        product.getQuantity() - item.getQuantity()
                    );
                    break;
                }
            }
        }

        productBrowser.saveProducts(products);

        Invoice invoice = new Invoice(
            nextInvoiceId++, order, discount
        );
        invoice.generateInvoice();

        System.out.println("\nCheckout completed successfully.");
        System.out.println("==============================");

        cart = new Cart();
    }
}
