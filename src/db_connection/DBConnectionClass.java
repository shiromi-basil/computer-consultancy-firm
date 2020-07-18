package db_connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnectionClass {
    public Connection DBConnection;

    /**
     * This method launches as soon this AlertBox.fxml is loaded
     *
     * @return Connection This returns the connection between the database and the program.
     */
    public Connection getConnection() {
        // The multiQueries has been allowed in the database URL to accept several queries from the program
        String dbURL = "jdbc:mysql://localhost?allowMultiQueries=true";
        String username = "root";
        String password = "";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DBConnection = DriverManager.getConnection(dbURL, username, password);
        } catch (Exception e) {
            System.out.println("This database file does not exist");
        }

        return DBConnection;
    }
}
