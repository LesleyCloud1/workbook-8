package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;//Imports the connection pool class
import java.sql.Connection;//Allows us to talk to the database
import java.sql.PreparedStatement;//Safer and more efficient than raw SQL strings
import java.sql.ResultSet;//Holds the results of our query
import java.sql.SQLException;//Handles any database errors

public class ProductService {

    //This method displays all product details using a connection from the data source (pool)
    public static void displayProducts(BasicDataSource dataSource) {
        //SQL query to get the product info from the database
        String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products ORDER BY ProductName";

        //Try-with-resources: ensures all resources are closed properly (no memory leaks)
        try (
                //Ask the data source for a connection from the pool
                Connection conn = dataSource.getConnection();

                //Create a prepared SQL statement with the query
                PreparedStatement stmt = conn.prepareStatement(sql);

                //Execute the query and get the results
                ResultSet rs = stmt.executeQuery()
        ) {
            //Print a header row for formatting
            System.out.printf("%-10s %-30s %-10s %-10s\n", "ID", "Name", "Price", "Stock");
            System.out.println("-------------------------------------------------------------");

            //Loop through the results and display each product's data
            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("UnitPrice");
                int stock = rs.getInt("UnitsInStock");

                //Print each product nicely aligned in columns
                System.out.printf("%-10d %-30s $%-9.2f %-10d\n", id, name, price, stock);
            }

        } catch (SQLException e) {
            //Print any SQL errors that happen during the process
            System.out.println("Error retrieving product data: " + e.getMessage());
        }
    }
}
