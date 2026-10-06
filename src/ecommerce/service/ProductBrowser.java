package ecommerce.service;

import ecommerce.exception.InvalidProductException;
import ecommerce.model.Product;
import ecommerce.repository.ProductRepository;

import java.io.IOException;
import java.util.ArrayList;

public class ProductBrowser {

    private ProductRepository productRepository;

    public ProductBrowser() {
        productRepository = new ProductRepository();
    }

    // Display all products
    public void displayAllProducts()
            throws IOException, InvalidProductException {

        ArrayList<Product> products = productRepository.loadProducts();

        if (products.isEmpty()) {
            System.out.println("No products available.");
            return;
        }

        System.out.println("\n----- Product List -----");

        for (Product product : products) {
            displayProduct(product);
        }

        System.out.println("------------------------");
    }

    // Search products by name
    public void searchByName(String name)
            throws IOException, InvalidProductException {

        ArrayList<Product> products = productRepository.loadProducts();

        boolean found = false;

        System.out.println("\n----- Search Results -----");

        for (Product product : products) {

            if (product.getProductName()
                    .toLowerCase()
                    .contains(name.toLowerCase())) {

                displayProduct(product);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No product found.");
        }

        System.out.println("--------------------------");
    }

    // Display products belonging to a category
    public void displayByCategory(String categoryName)
            throws IOException, InvalidProductException {

        ArrayList<Product> products = productRepository.loadProducts();

        boolean found = false;

        System.out.println("\n----- Category: "
                + categoryName + " -----");

        for (Product product : products) {

            if (product.getCategory()
                    .getCategoryName()
                    .equalsIgnoreCase(categoryName)) {

                displayProduct(product);
                found = true;
            }
        }

        if (!found) {
            System.out.println("No products found in this category.");
        }

        System.out.println("--------------------------");
    }

    // Get all products for the checkout flow
    public ArrayList<Product> getProducts()
            throws IOException, InvalidProductException {

        return productRepository.loadProducts();
    }

    // Save the updated product list to the file
    public void saveProducts(ArrayList<Product> products)
        throws IOException{
        productRepository.saveProducts(products);
    }

    // Display one product
    private void displayProduct(Product product) {

        System.out.println(
                "ID: " + product.getProductId()
                + " | Name: " + product.getProductName()
                + " | Price: ₹" + product.getPrice()
                + " | Quantity: " + product.getQuantity()
                + " | Category: "
                + product.getCategory().getCategoryName()
        );
    }
}