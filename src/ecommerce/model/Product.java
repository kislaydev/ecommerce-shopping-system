package ecommerce.model;

import ecommerce.exception.InvalidProductException;

public class Product {

    private int productId;
    private String productName;
    private double price;
    private int quantity;
    private Category category;

    public Product(int productId, String productName, double price, int quantity, Category category)
            throws InvalidProductException {

        validateProduct(productId, productName, price, quantity, category);

        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.category = category;
    }

    private void validateProduct(int productId, String productName, double price,
                                 int quantity, Category category)
            throws InvalidProductException {

        if (productId <= 0) {
            throw new InvalidProductException("Product ID must be greater than 0");
        }

        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidProductException("Product name cannot be empty");
        }

        if (price < 0) {
            throw new InvalidProductException("Price cannot be negative");
        }

        if (quantity < 0) {
            throw new InvalidProductException("Product quantity cannot be negative");
        }

        if (category == null) {
            throw new InvalidProductException("Category cannot be null");
        }
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) throws InvalidProductException {

        if (productId <= 0) {
            throw new InvalidProductException("Product ID must be greater than 0");
        }

        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) throws InvalidProductException {

        if (productName == null || productName.trim().isEmpty()) {
            throw new InvalidProductException("Product name cannot be empty");
        }

        this.productName = productName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) throws InvalidProductException {

        if (price < 0) {
            throw new InvalidProductException("Price cannot be negative");
        }

        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) throws InvalidProductException {

        if (quantity < 0) {
            throw new InvalidProductException("Product quantity cannot be negative");
        }

        this.quantity = quantity;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) throws InvalidProductException {

        if (category == null) {
            throw new InvalidProductException("Category cannot be null");
        }

        this.category = category;
    }
}