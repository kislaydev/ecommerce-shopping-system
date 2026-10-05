package ecommerce.model;

public class CardPayment extends Payment{
    private String cardNumber;
    public CardPayment(int paymentId, double amount, String cardNumber) {
        super(paymentId, amount);
        this.cardNumber = cardNumber;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public void makePayment() {
        System.out.println("Card payment of ₹" + getAmount() + " successful.");
    }
}

//created a reference of the abstract Payment class
//and assigned different child class objects, such
//        as CardPayment and UPIPayment, to it.
//When makePayment() is called, the overridden
//method of the actual object is executed at runtime.
