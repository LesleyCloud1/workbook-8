package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource; // For managing the connection pool
import java.sql.Connection;                      // Represents a connection to the database
import java.sql.SQLException;                    // Handles any SQL errors

public class DatabaseManager {

    // This is the connection pool (only one for the whole app)
    private static BasicDataSource dataSource;

    // Static block: runs once when the class is first used
    static {
        dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/sakila"); // Sakila DB URL
        dataSource.setUsername("root");                          // DB username
        dataSource.setPassword("Cloud1234!");                    // DB password
        dataSource.setMinIdle(5);                                // Minimum idle connections
        dataSource.setMaxIdle(10);                               // Maximum idle connections
        dataSource.setMaxOpenPreparedStatements(100);            // Prepared statement caching
    }

    // Public method for getting a pooled connection
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // Get one connection from the pool
    }

    // Call this to cleanly shut down the pool when app exits
    public static void closeDataSource() {
        try {
            if (dataSource != null) {
                dataSource.close(); // Closes the pool and all active connections
            }
        } catch (SQLException e) {
            System.out.println("Error closing data source: " + e.getMessage());
        }
    }
}
