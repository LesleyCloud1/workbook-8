package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource;//Provides the connection pool
import java.sql.Connection;//Used to interact with the database
import java.sql.PreparedStatement;//Used for secure, parameterized queries
import java.sql.ResultSet;//Stores the results of a query
import java.sql.SQLException;//Handles SQL-related errors

public class CustomerService {

    //This method displays customer details, using a pooled connection
    public static void displayCustomers(BasicDataSource dataSource) {
        //SQL query to fetch contact info for all customers, sorted by country
        String sql = "SELECT ContactName, CompanyName, City, Country, Phone " +
                "FROM Customers ORDER BY Country";

        //Try-with-resources to ensure all resources are closed properly
        try (
                //Get a connection from the pool
                Connection conn = dataSource.getConnection();

                //Prepare the SQL statement
                PreparedStatement stmt = conn.prepareStatement(sql);

                //Execute the query and get the results
                ResultSet rs = stmt.executeQuery()
        ) {
            //Print header for the customer table
            System.out.printf("%-25s %-30s %-15s %-15s %-15s\n", "Contact", "Company", "City", "Country", "Phone");
            System.out.println("------------------------------------------------------------------------------------------");

            //Loop through and print each row of customer data
            while (rs.next()) {
                String contact = rs.getString("ContactName");
                String company = rs.getString("CompanyName");
                String city = rs.getString("City");
                String country = rs.getString("Country");
                String phone = rs.getString("Phone");

                //Print each customer in aligned columns
                System.out.printf("%-25s %-30s %-15s %-15s %-15s\n", contact, company, city, country, phone);
            }

        } catch (SQLException e) {
            //Show any SQL-related errors
            System.out.println("Error retrieving customer data: " + e.getMessage());
        }
    }
}
