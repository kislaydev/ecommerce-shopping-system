
package ecommerce.service;

import ecommerce.model.Admin;
import ecommerce.repository.AdminRepository;

import java.io.IOException;
import java.util.ArrayList;

public class AdminAuthenticationService {

    private final AdminRepository adminRepository;
    private final PasswordHasher passwordHasher;

    public AdminAuthenticationService() {
        adminRepository = new AdminRepository();
        passwordHasher = new PasswordHasher();
    }

    // Creates the first admin only if no admin account exists.
    public Admin initializeFirstAdmin(
            String name, String email, String password)
            throws IOException {

        ArrayList<Admin> admins = adminRepository.loadAdmins();

        // Prevent creation of another initial admin.
        if (!admins.isEmpty()) {
            return null;
        }

        if (name == null || name.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.isEmpty()) {
            throw new IllegalArgumentException(
                "Name, email and password are required."
            );
        }

        String salt = passwordHasher.generateSalt();
        String passwordHash =
                passwordHasher.hashPassword(password, salt);

        Admin admin = new Admin(
            1, name.trim(), email.trim(),
            passwordHash, salt
        );

        admins.add(admin);
        adminRepository.saveAdmins(admins);

        return admin;
    }

    // Authenticates an existing admin.
    public Admin login(String email, String password)
            throws IOException {

        if (email == null || password == null) {
            return null;
        }

        ArrayList<Admin> admins = adminRepository.loadAdmins();

        for (Admin admin : admins) {
            if (admin.getEmail().equalsIgnoreCase(email.trim())) {

                String enteredHash =
                        passwordHasher.hashPassword(
                            password, admin.getSalt()
                        );

                if (enteredHash.equals(admin.getPasswordHash())) {
                    return admin;
                }

                return null;
            }
        }

        return null;
    }
}
