package db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class connection {
    public static Connection getConnection() {

        try {
            String url = "jdbc:postgresql://localhost:5432/bank_db";
            String username = "postgres";
            String password = "your_password";

            Connection connection = DriverManager.getConnection(
                    url, username, password
            );

//            System.out.println("PostgreSQL Connected Successfully!");

            return connection;

        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            e.printStackTrace();
        }
        return null;
    }
}
