
package ecommerce.repository;

import ecommerce.model.Admin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;

public class AdminRepository {

    private static final String FILE_NAME = "admins.txt";

    public ArrayList<Admin> loadAdmins() throws IOException {
        ArrayList<Admin> admins = new ArrayList<>();

        String data = FileManager.readFromFile(FILE_NAME);

        if (data.isEmpty()) {
            return admins;
        }

        String[] lines = data.split("\n");

        for (String line : lines) {
            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            String[] parts = line.split("\\|", -1);

            if (parts.length != 5) {
                throw new IOException(
                    "Invalid admin record in " + FILE_NAME
                );
            }

            int userId = Integer.parseInt(parts[0]);
            String name = decode(parts[1]);
            String email = decode(parts[2]);
            String passwordHash = parts[3];
            String salt = parts[4];

            admins.add(new Admin(
                userId, name, email, passwordHash, salt
            ));
        }

        return admins;
    }

    public void saveAdmins(ArrayList<Admin> admins)
            throws IOException {

        StringBuilder data = new StringBuilder();

        for (Admin admin : admins) {
            data.append(admin.getUserId()).append("|");
            data.append(encode(admin.getName())).append("|");
            data.append(encode(admin.getEmail())).append("|");
            data.append(admin.getPasswordHash()).append("|");
            data.append(admin.getSalt()).append("\n");
        }

        FileManager.saveToFile(FILE_NAME, data.toString());
    }

    private String encode(String value) {
        return Base64.getEncoder().encodeToString(
            value.getBytes(StandardCharsets.UTF_8)
        );
    }

    private String decode(String value) {
        return new String(
            Base64.getDecoder().decode(value),
            StandardCharsets.UTF_8
        );
    }
}
