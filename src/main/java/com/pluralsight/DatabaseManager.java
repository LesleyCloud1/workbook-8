package com.pluralsight;

import java.sql.Connection;//Connects to the database

import java.sql.DriverManager;//Creates the connection using credentials and URL

import java.sql.SQLException;//Handles database connection errors

public class DatabaseManager {

    //Connection details for the database
    private static final String URL = "jdbc:mysql://localhost:3306/northwind";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "Cloud1234!";

    //Returns a Connection object to use in your app
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        //Load the MySQL JDBC driver
        Class.forName("com.mysql.cj.jdbc.Driver");

        //Create and return the connection
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
