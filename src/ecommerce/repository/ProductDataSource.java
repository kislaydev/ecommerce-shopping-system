package ecommerce.repository;

import ecommerce.exception.InvalidProductException;
import ecommerce.model.Product;
import java.io.IOException;
import java.util.ArrayList;

public interface ProductDataSource {
    ArrayList<Product> loadProducts() throws IOException, InvalidProductException;
    void saveProducts(ArrayList<Product> products) throws IOException;
}
