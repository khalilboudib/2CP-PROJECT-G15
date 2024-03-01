import java.sql.*;

public class MyJDBC {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cp2_project";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "cp2_project";

    // Establish a connection to the database
    public static Connection connectToDataBase() throws SQLException, ClassNotFoundException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Load MySQL JDBC driver
            return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD); // Connect to the database using the provided URL, username, and password
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to load MySQL JDBC driver."); // If the JDBC driver class is not found, print an error message
            throw e; // Rethrow the exception to notify the caller about the failure
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database."); // If a SQL exception occurs during connection, print an error message
            throw e; // Rethrow the exception to notify the caller about the failure
        }
    }


    // Search for client and retreive his record
    public static boolean searchClient(Connection connection, Client client, ResultSet foundClient) throws SQLException {
        String sqlQuery = "Select * from clients where card_number= ? ";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1,client.getCardNumber()); // Set the placeholder to client card number
            foundClient = statement.executeQuery(); // Execute the update to add the client to the database
            if ( foundClient == null ) {
                return false ; // Not Found
            }
            else {
                return true ; // Found
            }
        } catch (SQLException e) {
            System.err.println("Error finding client: " + e.getMessage()); // If a SQL exception occurs during client search, print an error message
            throw e ;
        }
    }

    // Add a client to the database
    public static void addClient(Connection connection, Client client) throws SQLException {
        String sqlQuery = "INSERT INTO clients (card_number, card_expiring_date, first_name, last_name, user_adress, public_key) VALUES (?, ?, ?, ?, ?, ? , ?)";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            // Set the statement parameters for the client data
            setStatement(statement, client);
            // Execute the update to add the client to the database
            statement.executeUpdate();
        } catch (SQLException e) {
            // If a SQL exception occurs during client addition, print an error message
            System.err.println("Error adding client: " + e.getMessage());
            throw e ;
        }
    }

    // Set the statement parameters for adding a client
    public static void setStatement(PreparedStatement statement, Client client) throws SQLException {
        try {
            // Set each parameter of the prepared statement with client data
            statement.setLong(1, client.getCardNumber());
            statement.setDate(2, Date.valueOf(client.getExpiringDate()));
            statement.setString(3, client.getFirstName());
            statement.setString(4, client.getLastName());
            statement.setString(5, client.getUserAdress());
            statement.setBytes(6, client.getPublicKey());
            statement.setBytes(7,client.getServerPrivateKey());
        } catch (SQLException e) {
            System.err.println("Error setting statement: " + e.getMessage()); // If a SQL exception occurs during parameter setting, print an error message
            throw e; // Rethrow the exception to propagate it
        }
    }


    // Delete client from data base
    public static void deleteClient (Connection connection , Client client ) throws SQLException {
        String sqlQuery = "delete from clients where card_number= ? ";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setLong(1,client.getCardNumber()); // Set the placeholder to client card number
            statement.executeUpdate(); // Execute the update to delete the client from the database
        } catch (SQLException e) {
            System.err.println("Error deleting client: " + e.getMessage()); // If a SQL exception occurs during client delete, print an error message
            throw e ;
        }
    }


    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }


}
