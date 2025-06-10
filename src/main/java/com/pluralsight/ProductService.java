package com.pluralsight;

import java.sql.Connection;//Allows working with the database connection
import java.sql.ResultSet;//Holds the results from a SQL query
import java.sql.SQLException;//Handles SQL-related errors
import java.sql.Statement;//Used to run SQL commands

public class ProductService {

    //This method queries the database and displays product information
    public static void displayProducts(Connection conn) throws SQLException {
        //SQL statement to get product details
        String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock FROM Products";

        //Try-with-resources: closes Statement and ResultSet automatically
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            //Loop through the result set and print each product
            System.out.printf("%-10s %-30s %-10s %-10s\n", "ID", "Name", "Price", "Stock");
            System.out.println("-------------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("UnitPrice");
                int stock = rs.getInt("UnitsInStock");

                System.out.printf("%-10d %-30s $%-9.2f %-10d\n", id, name, price, stock);
            }
        }
    }
}
