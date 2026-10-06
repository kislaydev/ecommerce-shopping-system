package ecommerce.model;

public class UPIPayment extends Payment {
    private String upiId;

    public UPIPayment(int paymentId, double amount, String upiId) {
        super(paymentId, amount);

        // Check that the UPI ID is not empty.
        if (upiId == null || upiId.trim().isEmpty()) {
            throw new IllegalArgumentException("UPI ID cannot be empty");
        }
        // Check that the UPI ID contains @.
        if (!upiId.contains("@")) {
            throw new IllegalArgumentException("Invalid UPI ID");
        }

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
