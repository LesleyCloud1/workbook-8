package com.pluralsight;

import java.sql.Connection;//Used for connecting to the database
import java.sql.ResultSet;//Holds data returned by the query
import java.sql.SQLException;//Handles SQL-related errors
import java.sql.Statement;//Executes SQL queries
import java.util.Scanner;//Reads input from the user

public class CategoryService {

    //This method displays all categories, then asks the user to pick one to view its products
    public static void displayCategoriesAndProducts(Connection conn, Scanner scanner) throws SQLException {
        //SQL command to get category ID and name
        String sql = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";

        //First: display all categories
        System.out.println("\nCategories:");
        System.out.printf("%-10s %-30s\n", "Category ID", "Category Name");
        System.out.println("------------------------------------------");

        //Try-with-resources closes the Statement and ResultSet automatically
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                int id = rs.getInt("CategoryID");
                String name = rs.getString("CategoryName");
                System.out.printf("%-10d %-30s\n", id, name);
            }
        }

        //Prompt the user to select a category
        System.out.print("\nEnter a Category ID to view its products: ");
        int categoryId = scanner.nextInt();

        //Now display products in the selected category
        displayProductsByCategory(conn, categoryId);
    }

    //This method shows all products for a given category ID
    private static void displayProductsByCategory(Connection conn, int categoryId) throws SQLException {
        //SQL command to get product details in a given category
        String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                "FROM Products WHERE CategoryID = " + categoryId;

        System.out.println("\nProducts in Category " + categoryId + ":");
        System.out.printf("%-10s %-30s %-10s %-10s\n", "ID", "Name", "Price", "Stock");
        System.out.println("-------------------------------------------------------------");

        //Try-with-resources ensures resources are closed automatically
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            boolean hasResults = false;

            while (rs.next()) {
                hasResults = true;
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("UnitPrice");
                int stock = rs.getInt("UnitsInStock");

                System.out.printf("%-10d %-30s $%-9.2f %-10d\n", id, name, price, stock);
            }

            if (!hasResults) {
                System.out.println("No products found for this category.");
            }
        }
    }
}
