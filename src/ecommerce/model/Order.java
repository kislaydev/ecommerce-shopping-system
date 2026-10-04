package ecommerce.model;
import java.util.ArrayList;

public class Order {
    private int orderId;
    private Customer customer;

//  CONCEPT: Collection - ArrayList
//  An order can contain multiple OrderItems,
//  so we use an ArrayList to store them.
//
    private ArrayList<OrderItem> items;
    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        items = new ArrayList<OrderItem>();
    }
    public int getOrderId() {
        return orderId;
    }
    public Customer getCustomer() {
        return customer;
    }
    public void addItem(Product product, int quantity) {
        OrderItem item = new OrderItem(product, quantity);
        items.add(item);
    }
    public double calculateTotal() {
        double total = 0;
        for (int i = 0; i< items.size(); i++) {
            OrderItem item = items.get(i);
            total = total + item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
    public void displayOrder(){
        System.out.println("\n----- Order Details -----");
        System.out.println("Order ID: " + orderId);
        System.out.println("Customer: " + customer.getName());
        for (int i = 0; i < items.size(); i++) {
            OrderItem item = items.get(i);
            System.out.println(
                    "Product: " + item.getProduct().getProductName()
                            + " | Quantity: " + item.getQuantity()
                            + " | Subtotal: ₹" + item.getSubtotal()
            );
        }
        System.out.println("-------------------------");
        System.out.println("Order Total: ₹" + calculateTotal());
    }
}
