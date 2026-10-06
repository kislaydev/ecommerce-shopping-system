package ecommerce.repository;

import ecommerce.exception.InvalidProductException;
import ecommerce.model.Category;
import ecommerce.model.Product;

import java.io.IOException;
import java.util.ArrayList;

public class ProductRepository implements ProductDataSource {

    private static final String FILE_NAME = "products.txt";

    @Override
    // Save all products.txt to the file
    public void saveProducts(ArrayList<Product> products) throws IOException {

        StringBuilder data = new StringBuilder();

        for (Product product : products) {

            data.append(product.getProductId()).append("|");
            data.append(product.getProductName()).append("|");
            data.append(product.getPrice()).append("|");
            data.append(product.getQuantity()).append("|");
            data.append(product.getCategory().getCategoryId()).append("|");
            data.append(product.getCategory().getCategoryName());

            data.append("\n");
        }

        FileManager.saveToFile(FILE_NAME, data.toString());
    }

    @Override
    // Load all products.txt from the file
    public ArrayList<Product> loadProducts()
            throws IOException, InvalidProductException {

        ArrayList<Product> products = new ArrayList<>();

        String data = FileManager.readFromFile(FILE_NAME);

        if (data.isEmpty()) {
            return products;
        }

        String[] lines = data.split("\n");

        for (String line : lines) {

            if (line.trim().isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\|");

            int productId = Integer.parseInt(parts[0]);
            String productName = parts[1];
            double price = Double.parseDouble(parts[2]);
            int quantity = Integer.parseInt(parts[3]);

            int categoryId = Integer.parseInt(parts[4]);
            String categoryName = parts[5];

            Category category = new Category(categoryId, categoryName);

            Product product = new Product(
                    productId,
                    productName,
                    price,
                    quantity,
                    category
            );

            products.add(product);
        }

        return products;
    }
}