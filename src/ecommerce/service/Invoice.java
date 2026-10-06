package ecommerce.service;

import ecommerce.model.Order;

public class Invoice {
    private int invoiceId;
    private Order order;
    private Discount discount;

    public Invoice(int invoiceId, Order order, Discount discount) {
        this.invoiceId = invoiceId;
        this.order = order;
        this.discount = discount;
    }
    public void generateInvoice() {
        double orderTotal = order.calculateTotal();
        double discountAmount = discount.getDiscountAmount(orderTotal);
        double finalTotal = discount.applyDiscount(orderTotal);

        System.out.println("\n========== INVOICE ==========");
        System.out.println("Invoice ID: " + invoiceId);
        System.out.println("Order ID: " + order.getOrderId());
        System.out.println("Customer: " + order.getCustomer().getName());

        order.displayOrder();

        System.out.println("Discount: " + discount.getPercentage() + "%");
        System.out.println("Discount Amount: ₹" + discountAmount);
        System.out.println("Final Total: ₹" + finalTotal);
        System.out.println("=============================");
    }
}
