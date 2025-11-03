package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {
        Properties props = new Properties();
        try (InputStream input = DBConnection.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new DataAccessException("Could not find db.properties", null);
            }
            props.load(input);
        } catch (IOException e) {
            throw new DataAccessException("Could not load db.properties", e);
        }

        try {
            Class.forName(props.getProperty("db.driver"));
            return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.username"),
                props.getProperty("db.password")
            );
        } catch (ClassNotFoundException | SQLException e) {
            throw new DataAccessException("Could not connect to the database", e);
        }
    }
}