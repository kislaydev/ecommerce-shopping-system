package ecommerce.controller;

import ecommerce.service.ProductBrowser;

import java.io.IOException;
import java.util.Scanner;

import ecommerce.exception.InvalidProductException;

public class ConsoleMenu {

    private Scanner scanner;
    private ProductBrowser productBrowser;

    public ConsoleMenu() {
        scanner = new Scanner(System.in);
        productBrowser = new ProductBrowser();
    }

    public void start() {

        int choice;

        do {

            System.out.println("\n===== E-Commerce Shopping System =====");
            System.out.println("1. View All Products");
            System.out.println("2. Search Product");
            System.out.println("3. Browse by Category");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            try {

                switch (choice) {

                    case 1:
                        productBrowser.displayAllProducts();
                        break;

                    case 2:
                        System.out.print("Enter product name: ");
                        String name = scanner.nextLine();

                        productBrowser.searchByName(name);
                        break;

                    case 3:
                        System.out.print("Enter category name: ");
                        String category = scanner.nextLine();

                        productBrowser.displayByCategory(category);
                        break;

                    case 4:
                        System.out.println("Thank you for using the E-Commerce Shopping System.");
                        break;

                    default:
                        System.out.println("Invalid choice. Please try again.");
                }

            } catch (IOException | InvalidProductException e) {

                System.out.println("Error: " + e.getMessage());
            }

        } while (choice != 4);
    }
}