package ecommerce.service;

import javax.crypto.SecretKeyFactory; // SecretKeyFactory is used to create the password hash using PBKDF2
import javax.crypto.spec.PBEKeySpec; // PBEKeySpec stores the password, salt, number of iterations and hash size
import java.security.SecureRandom; // SecureRandom is used to generate a random salt for every password
import java.util.Base64; // Base64 converts byte data into String so that it can be stored in a file

public class PasswordHasher {

    private static final int salt_length = 16;
    private static final int iterations = 65536;
    // 65536 is 2^16 and gives PBKDF2 enough iterations to make password hashing slower
    // This makes password guessing harder while keeping the program fast enough
    private static final int key_len = 256;

    public String generateSalt() {
        // SecureRandom generates random data which we use as salt
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[salt_length];
        random.nextBytes(salt);
        // Base64 converts the salt from byte[] into String
        return Base64.getEncoder().encodeToString(salt);
    }
    public String hashPassword(String password, String salt) {
        try {
            // PBKDF2 creates the password hash using the password and salt
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), Base64.getDecoder().decode(salt), iterations, key_len);
            // SecretKeyFactory performs the PBKDF2 hashing
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            byte[] hash = factory.generateSecret(spec).getEncoded();
            // Convert the hash into String so that it can be stored in a file
            return Base64.getEncoder().encodeToString(hash);
        }
        catch (Exception e) {
            throw new RuntimeException("Password hashing failed");
        }
    }
}