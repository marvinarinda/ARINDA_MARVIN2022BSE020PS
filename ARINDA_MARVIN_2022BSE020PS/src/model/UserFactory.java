package model;

import util.PasswordManager;

public class UserFactory {
    public static User createUser(String username, String password) {
        byte[] salt = PasswordManager.getSalt();
        String passwordHash = PasswordManager.hashPassword(password, salt);
        // Convert salt to a hex string for storage
        StringBuilder sb = new StringBuilder();
        for (byte b : salt) {
            sb.append(String.format("%02x", b));
        }
        String saltString = sb.toString();
        return new User(username, passwordHash, saltString);
    }
}