package ecommerce.model;

public class CardPayment extends Payment{
    private String cardNumber;
    public CardPayment(int paymentId, double amount, String cardNumber) {
        super(paymentId, amount);

        //Check that the card no is not empty and has 16 digits
        if(cardNumber == null || cardNumber.length() != 16){
            throw new IllegalArgumentException("Card number must have 16 characters");
        }

        //Check every character of the card no
        for(int i = 0; i<cardNumber.length(); i++){
            if(!Character.isDigit(cardNumber.charAt(i))){
                throw new IllegalArgumentException("Card number must contain only digits");
            }
        }
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
