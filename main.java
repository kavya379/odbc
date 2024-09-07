package jdbc_example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Main {

    private static final String DATABASE_URL = "jdbc:sqlite:sample.db";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        try {
            // Load SQLite JDBC driver
            Class.forName("org.sqlite.JDBC");

            // Establish a connection to the database
            connection = DriverManager.getConnection(DATABASE_URL);
            statement = connection.createStatement();

            // Create a table
            String createTableSQL = "CREATE TABLE IF NOT EXISTS users (" +
                                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                                    "name TEXT NOT NULL, " +
                                    "email TEXT NOT NULL UNIQUE)";
            statement.execute(createTableSQL);

            // Insert data into the table
            String insertSQL = "INSERT INTO users (name, email) VALUES ('John Doe', 'john@example.com')";
            statement.executeUpdate(insertSQL);

            // Query data from the table
            String selectSQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(selectSQL);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                System.out.printf("ID: %d, Name: %s, Email: %s%n", id, name, email);
            }

            // Update data in the table
            String updateSQL = "UPDATE users SET email = 'john.doe@example.com' WHERE name = 'John Doe'";
            statement.executeUpdate(updateSQL);

            // Delete data from the table
            String deleteSQL = "DELETE FROM users WHERE name = 'John Doe'";
            statement.executeUpdate(deleteSQL);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
