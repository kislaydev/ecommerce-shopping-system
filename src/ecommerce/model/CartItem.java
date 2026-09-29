package ecommerce.model;

public class CartItem {

    private Product product; /* Composition is used here
                                A CartItem "has a" Product
                                we basically are storing a Project object inside CartItem
                                instead of copying all the product info again
                               */
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }
    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public double getSubtotal() {
        return product.getPrice() * quantity;
    }
    /*Object interaction occurs when objects communicate
    by invoking each other's methods to perform a task.
    In this scenario, a CartItem object calls the getPrice()
    method of its internal Product object to retrieve the base cost.
    It then multiplies this value by the cart quantity to
    dynamically calculate the subtotal without exposing
    or duplicating the product's underlying data.
     */


}
