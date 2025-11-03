package util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class PasswordManager {

    public static byte[] getSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    public static String hashPassword(String password, byte[] salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt);
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedPassword) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean verifyPassword(String enteredPassword, String storedHash, byte[] storedSalt) {
        String hashedEnteredPassword = hashPassword(enteredPassword, storedSalt);
        return hashedEnteredPassword.equals(storedHash);
    }
}
