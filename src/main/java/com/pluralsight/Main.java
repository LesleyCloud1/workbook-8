package com.pluralsight;

import org.apache.commons.dbcp2.BasicDataSource; // Manages a pool of connections
import java.util.Scanner;                        // For reading user input
import java.sql.SQLException;                    // Handles database errors

public class Main {
    public static void main(String[] args) {
        // Create a Scanner to read input from the keyboard
        Scanner scanner = new Scanner(System.in);

        // Create and configure the BasicDataSource (connection pool)
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl("jdbc:mysql://localhost:3306/northwind");//DB URL
        dataSource.setUsername("root");//DB username
        dataSource.setPassword("Cloud1234!");//DB password
        dataSource.setMinIdle(5);//Minimum connections to keep idle
        dataSource.setMaxIdle(10);//Maximum idle connections
        dataSource.setMaxOpenPreparedStatements(100);//Number of cached statements

        boolean running = true; //Controls the menu loop

        //Menu loop
        while (running) {
            System.out.println("What do you want to do?");
            System.out.println("1) Display all products");
            System.out.println("2) Display all customers");
            System.out.println("3) Display all categories");
            System.out.println("0) Exit");
            System.out.print("Select an option: ");

            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    //Pass the data source to get a connection inside the service method
                    ProductService.displayProducts(dataSource);
                    break;
                case 2:
                    CustomerService.displayCustomers(dataSource);
                    break;
                case 3:
                    CategoryService.displayCategoriesAndProducts(dataSource, scanner);
                    break;
                case 0:
                    System.out.println("Goodbye!");
                    running = false;
                    try {
                        dataSource.close(); //Close the data source when the program ends
                    } catch (SQLException e) {
                        System.out.println("Failed to close data source: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close(); //Close scanner after exiting the loop
    }
}
