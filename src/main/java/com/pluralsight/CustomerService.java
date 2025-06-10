package com.pluralsight;

import java.sql.Connection;//Needed to interact with the database
import java.sql.ResultSet; //Holds the data returned from a SQL query
import java.sql.SQLException;//Handles database-related errors
import java.sql.Statement;//Used to execute SQL commands

public class CustomerService {

    //This method displays customer information sorted by country
    public static void displayCustomers(Connection conn) throws SQLException {
        //SQL command to get customer details sorted by country
        String sql = "SELECT ContactName, CompanyName, City, Country, Phone FROM Customers ORDER BY Country";

        //Try-with-resources to ensure the Statement and ResultSet are closed automatically
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            //Loop through the result set and print each customer's info
            System.out.printf("%-25s %-30s %-15s %-15s %-15s\n", "Contact", "Company", "City", "Country", "Phone");
            System.out.println("------------------------------------------------------------------------------------------");

            while (rs.next()) {
                String contact = rs.getString("ContactName");
                String company = rs.getString("CompanyName");
                String city = rs.getString("City");
                String country = rs.getString("Country");
                String phone = rs.getString("Phone");

                System.out.printf("%-25s %-30s %-15s %-15s %-15s\n", contact, company, city, country, phone);
            }
        }
    }
}
