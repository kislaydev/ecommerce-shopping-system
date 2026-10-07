package ecommerce.model;

import java.util.ArrayList;
import ecommerce.exception.InvalidQuantityException;

/* A cart can contain multiple CartItem objects
   ArrayList is a Java Collection that stores multiple
   objects in an ordered, dynamically-sized list */

public class Cart {

    private ArrayList<CartItem> items;

    public Cart() {
        items = new ArrayList<CartItem>();
    }

    // Add one item to the cart.
    // This is method overloading because addItem()
    // has another version with different parameters.
    public void addItem(Product product) throws InvalidQuantityException {
        addItem(product, 1);
    }

    public void addItem(Product product, int quantity) throws InvalidQuantityException {

        // Check whether the product is already in the cart.
        for (int i = 0; i < items.size(); i++) {

            CartItem item = items.get(i);

            if (item.getProduct().getProductId() == product.getProductId()) {

                // If the product already exists, increase its quantity.
                int newQuantity = item.getQuantity() + quantity;

                item.setQuantity(newQuantity);

                return;
            }
        }

        // If the product is not already in the cart, create a new CartItem.
        CartItem item = new CartItem(product, quantity);
        items.add(item);
    }

    public void removeItem(int productId) {
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);

            if (item.getProduct().getProductId() == productId) {
                items.remove(i);
                return;
            }
        }
    }

    public void updateQuantity(int productId, int quantity) throws InvalidQuantityException {
        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);

            if (item.getProduct().getProductId() == productId) {
                item.setQuantity(quantity);
                return;
            }
        }
    }

    public double calculateTotal() {
        double total = 0;

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);
            total = total + item.getSubtotal();
        }

        return total;
    }

    public void viewCart() {

        if (items.size() == 0) {
            System.out.println("Cart is empty.");
        }

        System.out.println("\n----- Shopping Cart -----");

        for (int i = 0; i < items.size(); i++) {
            CartItem item = items.get(i);

            System.out.println(
                "Product: " + item.getProduct().getProductName()
                + " | Quantity: " + item.getQuantity()
                + " | Subtotal: ₹" + item.getSubtotal()
            );
        }

        System.out.println("-------------------------");
        System.out.println("Total: ₹" + calculateTotal());
    }

    // Used by the checkout flow to access cart items
    // return a copy of the ArrayList so other classes
    // can read the cart items but cannot directly
    // modify the Cart's internal collection
    public ArrayList<CartItem> getItems() {
        return new ArrayList<CartItem>(items);
    }
}