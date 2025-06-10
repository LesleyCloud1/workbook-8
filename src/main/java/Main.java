package com.pluralsight;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        //Define the database connection details
        String url = "jdbc:mysql://localhost:3306/northwind";
        String username = "root";
        String password = "Cloud1234!";

        try {
            //Load the MySQL JDBC driver so Java can talk to the MySQL database
            Class.forName("com.mysql.cj.jdbc.Driver");

            //Try to connect to the database using the provided URL, username, and password
            try (Connection conn = DriverManager.getConnection(url, username, password)) {

                // SQL query to get product information from the Products table
                String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

                //Create a Statement object to run the SQL query
                Statement stmt = conn.createStatement();

                //Execute the query and store the results in a ResultSet object
                ResultSet rs = stmt.executeQuery(sql);

                //Loop through the result set and display each product's info
                while (rs.next()) {
                    int id = rs.getInt("ProductID"); //Get the product ID
                    String name = rs.getString("ProductName"); //Get the product name
                    double price = rs.getDouble("UnitPrice"); //Get the product price
                    int stock = rs.getInt("UnitsInStock"); //Get how many units are in stock

                    //Print out the product information in a readable format
                    System.out.println("Product Id: " + id);
                    System.out.println("Name: " + name);
                    System.out.printf("Price: %.2f\n", price); //Format price to 2 decimal places
                    System.out.println("Stock: " + stock);
                    System.out.println("------------------------"); //Separator between products
                }

            } catch (SQLException e) {
                //This block handles any SQL/database errors
                System.out.println("Database error: " + e.getMessage());
            }

        } catch (ClassNotFoundException e) {
            //This block handles the error if the JDBC driver is not found
            System.out.println("MySQL Driver not found: " + e.getMessage());
        }
    }
}
