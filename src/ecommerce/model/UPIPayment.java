package ecommerce.model;

public class UPIPayment extends Payment {
    private String upiId;
    public UPIPayment(int paymentId, double amount, String upiId) {
        super(paymentId, amount);
        this.upiId = upiId;
    }
    public String getUpiId() {
        return upiId;
    }

    @Override
    public void makePayment() {
        System.out.println("UPI payment of ₹" + getAmount() + " successful.");
    }
}
