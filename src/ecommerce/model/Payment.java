package ecommerce.model;

//Payment is abstract because it represents a general
//payment concept. The actual payment method differs
//for different payment types, so the abstract
//makePayment() method is implemented by subclasses

public abstract class Payment {
    private int paymentId;
    private double amount;
    public Payment(int paymentId, double amount) {
        this.paymentId = paymentId;
        this.amount = amount;
    }
    public int getPaymentId() {
        return paymentId;
    }
    public double getAmount() {
        return amount;
    }
    public abstract void makePayment();
}
