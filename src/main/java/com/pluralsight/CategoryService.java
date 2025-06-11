package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;//For connection pooling
import java.sql.Connection;//Represents a connection to the database
import java.sql.PreparedStatement;//For executing SQL queries securely
import java.sql.ResultSet;//Holds query results
import java.sql.SQLException;//Handles SQL errors
import java.util.Scanner;//Reads user input

public class CategoryService {

    //Displays all categories and prompts user to choose one to view its products
    public static void displayCategoriesAndProducts(BasicDataSource dataSource, Scanner scanner) {
        //SQL query to list all categories
        String sql = "SELECT CategoryID, CategoryName FROM Categories ORDER BY CategoryID";

        System.out.println("\nCategories:");
        System.out.printf("%-10s %-30s\n", "Category ID", "Category Name");
        System.out.println("------------------------------------------");

        try (
                Connection conn = dataSource.getConnection();//Get connection from pool
                PreparedStatement stmt = conn.prepareStatement(sql);//Prepare SQL statement
                ResultSet rs = stmt.executeQuery()//Execute and get results
        ) {
            while (rs.next()) {
                int id = rs.getInt("CategoryID");
                String name = rs.getString("CategoryName");
                System.out.printf("%-10d %-30s\n", id, name);
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving categories: " + e.getMessage());
            return;
        }

        //Prompt user to choose a category
        System.out.print("\nEnter a Category ID to view its products: ");
        int categoryId = scanner.nextInt();

        //Call method to show products in that category
        displayProductsByCategory(dataSource, categoryId);
    }

    //Displays products for a specific category
    private static void displayProductsByCategory(BasicDataSource dataSource, int categoryId) {
        //SQL query with a placeholder for categoryId
        String sql = "SELECT ProductID, ProductName, UnitPrice, UnitsInStock " +
                "FROM Products WHERE CategoryID = ? ORDER BY ProductName";

        System.out.println("\nProducts in Category " + categoryId + ":");
        System.out.printf("%-10s %-30s %-10s %-10s\n", "ID", "Name", "Price", "Stock");
        System.out.println("-------------------------------------------------------------");

        try (
                Connection conn = dataSource.getConnection();            //Get connection from pool
                PreparedStatement stmt = conn.prepareStatement(sql)     //Prepare SQL statement
        ) {
            stmt.setInt(1, categoryId); //Insert the user's chosen ID into the query

            try (ResultSet rs = stmt.executeQuery()) { //Execute and get results
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
        } catch (SQLException e) {
            System.out.println("Error retrieving products by category: " + e.getMessage());
        }
    }
}
