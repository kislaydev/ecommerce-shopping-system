package ecommerce.service;

public class Discount {
    private double percentage;
    public Discount(double percentage) {
        if(percentage < 0 || percentage > 100){
            throw new IllegalArgumentException("percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }
    public double applyDiscount(double total) {
        double discountAmount = (total *  percentage)/100;
        return total-discountAmount;
    }
    public double getDiscountAmount(double total) {
        return (total * percentage)/100;
    }
    public double getPercentage() {
        return percentage;
    }
    public void setPercentage(double percentage) {
        if(percentage < 0 || percentage > 100){
            throw new IllegalArgumentException("percentage must be between 0 and 100");
        }
        this.percentage = percentage;
    }
}
