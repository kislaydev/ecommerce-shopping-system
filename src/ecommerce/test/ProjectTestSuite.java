package ecommerce.test;

import ecommerce.exception.InvalidProductException;
import ecommerce.exception.InvalidQuantityException;
import ecommerce.model.*;
import ecommerce.repository.ProductRepository;
import ecommerce.service.Discount;
import ecommerce.service.Invoice;
import ecommerce.service.ProductBrowser;

import java.util.ArrayList;

public class ProjectTestSuite {

    private static int passed = 0;
    private static int failed = 0;

    public static void main(String[] args) {

        System.out.println("======================================");
        System.out.println("     E-COMMERCE SYSTEM TEST SUITE");
        System.out.println("======================================");

        // Product tests
        testProductCreation();
        testProductInvalidPrice();
        testProductInvalidQuantity();

        // Cart tests
        testCartAdd();
        testCartRepeatedAdd();
        testCartUpdateQuantity();
        testCartRemove();
        testCartTotal();
        testCartInvalidQuantity();

        // Order test
        testOrderTotal();

        // Payment tests
        testCardPayment();
        testUPIPayment();
        testInvalidCardPayment();
        testInvalidUPIPayment();

        // Discount tests
        testDiscount();
        testDiscountZeroPercent();
        testDiscountHundredPercent();
        testInvalidDiscount();

        // Invoice test
        testInvoice();

        // Browsing test
        testProductBrowsing();

        // Persistence test
        testPersistence();

        // Summary
        System.out.println("\n======================================");
        System.out.println("            TEST SUMMARY");
        System.out.println("======================================");
        System.out.println("Passed: " + passed);
        System.out.println("Failed: " + failed);
        System.out.println("Total : " + (passed + failed));
        System.out.println("======================================");
    }

    // --------------------------------------------------
    // PRODUCT TESTS
    // --------------------------------------------------

    private static void testProductCreation() {
        try {
            Category category = new Category(1, "Electronics");

            Product product =
                    new Product(101, "Laptop", 50000, 5, category);

            check(
                    "Product creation",
                    product.getProductName().equals("Laptop")
                            && product.getPrice() == 50000
                            && product.getQuantity() == 5
            );

        } catch (Exception e) {
            fail("Product creation", e.getMessage());
        }
    }

    private static void testProductInvalidPrice() {
        try {
            Category category = new Category(1, "Electronics");

            new Product(103, "Invalid", -100, 5, category);

            fail("Negative product price should throw exception");

        } catch (InvalidProductException e) {
            pass("Negative product price throws InvalidProductException");
        }
    }

    private static void testProductInvalidQuantity() {
        try {
            Category category = new Category(1, "Electronics");

            new Product(104, "Invalid", 1000, -1, category);

            fail("Negative product quantity should throw exception");

        } catch (InvalidProductException e) {
            pass("Negative product quantity throws InvalidProductException");
        }
    }

    // --------------------------------------------------
    // CART TESTS
    // --------------------------------------------------

    private static Product createLaptop()
            throws InvalidProductException {

        return new Product(
                101,
                "Laptop",
                50000,
                5,
                new Category(1, "Electronics")
        );
    }

    private static void testCartAdd() {
        try {
            Product laptop = createLaptop();

            Cart cart = new Cart();

            cart.addItem(laptop, 2);

            check(
                    "Add product to cart",
                    cart.getItems().size() == 1
                            && cart.getItems().get(0).getQuantity() == 2
            );

        } catch (Exception e) {
            fail("Add product to cart", e.getMessage());
        }
    }

    private static void testCartRepeatedAdd() {
        try {
            Product laptop = createLaptop();

            Cart cart = new Cart();

            cart.addItem(laptop, 2);
            cart.addItem(laptop, 3);

            check(
                    "Repeated product addition",
                    cart.getItems().size() == 1
                            && cart.getItems().get(0).getQuantity() == 5
            );

        } catch (Exception e) {
            fail("Repeated product addition", e.getMessage());
        }
    }

    private static void testCartUpdateQuantity() {
        try {
            Product laptop = createLaptop();

            Cart cart = new Cart();

            cart.addItem(laptop, 2);
            cart.updateQuantity(101, 4);

            check(
                    "Update cart quantity",
                    cart.getItems().get(0).getQuantity() == 4
            );

        } catch (Exception e) {
            fail("Update cart quantity", e.getMessage());
        }
    }

    private static void testCartRemove() {
        try {
            Product laptop = createLaptop();

            Cart cart = new Cart();

            cart.addItem(laptop, 2);
            cart.removeItem(101);

            check(
                    "Remove product from cart",
                    cart.getItems().isEmpty()
            );

        } catch (Exception e) {
            fail("Remove product from cart", e.getMessage());
        }
    }

    private static void testCartTotal() {
        try {
            Product laptop = createLaptop();

            Cart cart = new Cart();

            cart.addItem(laptop, 2);

            check(
                    "Cart total calculation",
                    cart.calculateTotal() == 100000
            );

        } catch (Exception e) {
            fail("Cart total calculation", e.getMessage());
        }
    }

    private static void testCartInvalidQuantity() {
        try {
            Product laptop = createLaptop();

            Cart cart = new Cart();

            cart.addItem(laptop, 0);

            fail("Zero quantity should throw exception");

        } catch (InvalidQuantityException e) {
            pass("Zero cart quantity throws InvalidQuantityException");

        } catch (InvalidProductException e) {
            fail("Zero cart quantity test", e.getMessage());
        }
    }

    // --------------------------------------------------
    // ORDER TEST
    // --------------------------------------------------

    private static void testOrderTotal() {
        try {
            Product laptop = createLaptop();

            Customer customer =
                    new Customer(
                            1,
                            "Sahil",
                            "sahil18@gmail.com"
                    );

            Order order =
                    new Order(5001, customer);

            order.addItem(laptop, 2);

            check(
                    "Order total calculation",
                    order.calculateTotal() == 100000
            );

        } catch (Exception e) {
            fail("Order total calculation", e.getMessage());
        }
    }

    // --------------------------------------------------
    // PAYMENT TESTS
    // --------------------------------------------------

    private static void testCardPayment() {
        try {
            CardPayment payment =
                    new CardPayment(
                            1001,
                            900,
                            "1234567890123456"
                    );

            check(
                    "Valid card payment",
                    payment.getAmount() == 900
                            && payment.getCardNumber()
                            .equals("1234567890123456")
            );

        } catch (Exception e) {
            fail("Valid card payment", e.getMessage());
        }
    }

    private static void testUPIPayment() {
        try {
            UPIPayment payment =
                    new UPIPayment(
                            1002,
                            900,
                            "sahil@upi"
                    );

            check(
                    "Valid UPI payment",
                    payment.getAmount() == 900
                            && payment.getUpiId()
                            .equals("sahil@upi")
            );

        } catch (Exception e) {
            fail("Valid UPI payment", e.getMessage());
        }
    }

    private static void testInvalidCardPayment() {
        try {
            new CardPayment(
                    1003,
                    900,
                    "12345"
            );

            fail("Invalid card number should throw exception");

        } catch (IllegalArgumentException e) {
            pass("Invalid card number throws exception");
        }
    }

    private static void testInvalidUPIPayment() {
        try {
            new UPIPayment(
                    1004,
                    900,
                    "invalidupi"
            );

            fail("Invalid UPI ID should throw exception");

        } catch (IllegalArgumentException e) {
            pass("Invalid UPI ID throws exception");
        }
    }

    // --------------------------------------------------
    // DISCOUNT TESTS
    // --------------------------------------------------

    private static void testDiscount() {
        try {
            Discount discount =
                    new Discount(10);

            double finalAmount =
                    discount.applyDiscount(1000);

            check(
                    "10 percent discount",
                    finalAmount == 900
            );

        } catch (Exception e) {
            fail("10 percent discount", e.getMessage());
        }
    }

    private static void testDiscountZeroPercent() {
        try {
            Discount discount =
                    new Discount(0);

            check(
                    "0 percent discount boundary",
                    discount.applyDiscount(1000) == 1000
            );

        } catch (Exception e) {
            fail("0 percent discount boundary", e.getMessage());
        }
    }

    private static void testDiscountHundredPercent() {
        try {
            Discount discount =
                    new Discount(100);

            check(
                    "100 percent discount boundary",
                    discount.applyDiscount(1000) == 0
            );

        } catch (Exception e) {
            fail("100 percent discount boundary", e.getMessage());
        }
    }

    private static void testInvalidDiscount() {
        try {
            new Discount(101);

            fail("Discount above 100 should throw exception");

        } catch (IllegalArgumentException e) {
            pass("Discount above 100 throws exception");
        }
    }

    // --------------------------------------------------
    // INVOICE TEST
    // --------------------------------------------------

    private static void testInvoice() {
        try {
            Product laptop = createLaptop();

            Customer customer =
                    new Customer(
                            1,
                            "Sahil",
                            "sahil18@gmail.com"
                    );

            Order order =
                    new Order(5002, customer);

            order.addItem(laptop, 1);

            Discount discount =
                    new Discount(10);

            Invoice invoice =
                    new Invoice(
                            9001,
                            order,
                            discount
                    );

            invoice.generateInvoice();

            pass("Invoice generation");

        } catch (Exception e) {
            fail("Invoice generation", e.getMessage());
        }
    }

    // --------------------------------------------------
    // PRODUCT BROWSING TEST
    // --------------------------------------------------

    private static void testProductBrowsing() {
        try {
            ProductBrowser browser =
                    new ProductBrowser();

            ArrayList<Product> products =
                    browser.getProducts();

            check(
                    "Product browsing/load",
                    products.size() >= 2
                            && products.get(0)
                            .getProductName()
                            .equals("Laptop")
            );

        } catch (Exception e) {
            fail("Product browsing/load", e.getMessage());
        }
    }

    // --------------------------------------------------
    // PERSISTENCE TEST
    // --------------------------------------------------

    private static void testPersistence() {
        try {
            ProductRepository repository =
                    new ProductRepository();

            ArrayList<Product> products =
                    repository.loadProducts();

            if (products.isEmpty()) {
                fail(
                        "Persistence test - no products loaded"
                );
                return;
            }

            Product firstProduct =
                    products.get(0);

            int originalQuantity =
                    firstProduct.getQuantity();

            firstProduct.setQuantity(
                    originalQuantity + 1
            );

            repository.saveProducts(products);

            ArrayList<Product> loadedAgain =
                    repository.loadProducts();

            int savedQuantity =
                    loadedAgain.get(0).getQuantity();

            // Restore original quantity
            firstProduct.setQuantity(
                    originalQuantity
            );

            repository.saveProducts(products);

            check(
                    "Persistence save and reload",
                    savedQuantity ==
                            originalQuantity + 1
            );

        } catch (Exception e) {
            fail(
                    "Persistence save and reload",
                    e.getMessage()
            );
        }
    }

    // --------------------------------------------------
    // TEST RESULT HELPERS
    // --------------------------------------------------

    private static void check(
            String testName,
            boolean condition
    ) {
        if (condition) {
            pass(testName);
        } else {
            fail(
                    testName,
                    "Condition was false"
            );
        }
    }

    private static void pass(String testName) {
        passed++;

        System.out.println(
                "[PASS] " + testName
        );
    }

    // Overloaded fail method
    private static void fail(
            String testName,
            String message
    ) {
        failed++;

        System.out.println(
                "[FAIL] " + testName
                        + " -> " + message
        );
    }

    private static void fail(String testName) {
        failed++;

        System.out.println(
                "[FAIL] " + testName
        );
    }
}