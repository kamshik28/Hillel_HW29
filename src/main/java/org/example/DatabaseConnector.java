package org.example;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {
    private static final String URL = "jdbc:postgresql://localhost:5432/Company";
    private static final String USER = "postgres";
    private static final String PASSWORD = "dima2808";

    private Connection connection;

    public Connection connect() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Підключення успішне!");
        } catch (SQLException e) {
            System.err.println("Помилка підключення: " + e.getMessage());
        }
        return connection;
    }

    public void executeQuery(String query) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
        } catch (SQLException e) {
            System.err.println("Помилка виконання запиту: " + e.getMessage());
        }
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("З'єднання закрито.");
            } catch (SQLException e) {
                System.err.println("Помилка закриття з'єднання: " + e.getMessage());
            }
        }
    }
}
