package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // Database credentials
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=StoreMaster;encrypt=true;trustServerCertificate=true";
    private static final String USER = "PABASARAMADHAWA";
    private static final String PASSWORD = "PABASARAMADHAWA";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            if (conn != null) {
                System.out.println("Connected to StoreMaster database successfully!");
            }

        } catch (SQLException e) {
            System.out.println("Failed to connect to StoreMaster database.");
            e.printStackTrace();
        }
        return conn;
    }
}