package model;

import util.DBConnection;
import util.DataAccessException;
import util.PasswordManager;

import java.sql.*;

public class UserDAO {
    public boolean saveUser(User user) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO users (username, password_hash, password_salt) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPasswordHash());
            ps.setString(3, user.getPasswordSalt());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DataAccessException("Could not save user", e);
        }
    }

    public User getUser(String username) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new User(
                    rs.getString("username"),
                    rs.getString("password_hash"),
                    rs.getString("password_salt")
                );
            }
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Could not get user", e);
        }
    }

    public boolean validateUser(String username, String password) {
        User user = getUser(username);
        if (user == null) {
            return false;
        }
        // Convert salt from hex string to byte array
        String saltString = user.getPasswordSalt();
        byte[] salt = new byte[saltString.length() / 2];
        for (int i = 0; i < salt.length; i++) {
            int index = i * 2;
            int j = Integer.parseInt(saltString.substring(index, index + 2), 16);
            salt[i] = (byte) j;
        }

        return PasswordManager.verifyPassword(password, user.getPasswordHash(), salt);
    }
}