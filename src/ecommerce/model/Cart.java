package ecommerce.model;

import java.util.ArrayList;
    /* A cart can contain multiple CartItem objects
    ArrayList is a Java Collection that stores multiple
    objects in an ordered, dynamically-sized list */

public class Cart {
    private ArrayList<CartItem> items;
    public Cart(){
        items = new ArrayList<CartItem>();
    }
    public void addItem(Product product, int quantity){
        CartItem item = new CartItem(product, quantity);
        items.add(item);
    }
    public void removeItem(int productId){
        for(int i = 0; i< items.size(); i++){
            CartItem item = items.get(i);
            if(item.getProduct().getProductId() == productId){
                items.remove(i);
                return;
            }
        }
    }
    public void updateQuantity(int productId, int quantity){
        for(int i = 0; i< items.size(); i++){
            CartItem item = items.get(i);
            if(item.getProduct().getProductId() == productId){
                item.setQuantity(quantity);
                return;
            }
        }
    }
    public double calculateTotal(){
        double total = 0;
        for(int i = 0; i< items.size(); i++){
            CartItem item = items.get(i);
            total = total + item.getSubtotal();
        }
        return total;
    }
    public void viewCart(){
        if(items.size() ==  0){
            System.out.println("Cart is empty.");
        }
        System.out.println("\n----- Shopping Cart -----");
        for(int i = 0; i< items.size(); i++){
            CartItem item = items.get(i);
            System.out.println("Product: " + item.getProduct().getProductName() + " | Quantity: " + item.getQuantity() + " | Subtotal: ₹" + item.getSubtotal());
        }
        System.out.println("-------------------------");
        System.out.println("Total: ₹" + calculateTotal());
    }
}
