
package ecommerce;

import ecommerce.model.Admin;
import ecommerce.service.AdminAuthenticationService;

import java.io.IOException;
import java.util.Scanner;

public class AdminSetup {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AdminAuthenticationService adminService =
                new AdminAuthenticationService();

        try {
            System.out.println("=== Initial Admin Setup ===");

            System.out.print("Enter admin name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Enter admin email: ");
            String email = scanner.nextLine().trim();

            System.out.print("Create admin password: ");
            String password = scanner.nextLine();

            Admin admin = adminService.initializeFirstAdmin(
                    name, email, password
            );

            if (admin == null) {
                System.out.println(
                    "Admin setup has already been completed. "
                    + "A new initial admin cannot be created."
                );
            } else {
                System.out.println(
                    "Initial admin created successfully."
                );
                System.out.println(
                    "You can now log in through the admin login option."
                );
            }

        } catch (IllegalArgumentException e) {
            System.out.println("Setup failed: " + e.getMessage());
        } catch (IOException e) {
            System.out.println(
                "Unable to save admin details: " + e.getMessage()
            );
        } finally {
            scanner.close();
        }
    }
}
