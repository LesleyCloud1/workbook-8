package com.pluralsight;

import java.sql.*;              //Imports Java SQL classes for working with databases
import java.util.Scanner;       //Imports Scanner class to get user input from keyboard

public class Main {
    public static void main(String[] args) {
        //Create a Scanner to read input from the keyboard
        Scanner scanner = new Scanner(System.in);

        //Try to establish a connection to the database using our helper class
        try (Connection conn = DatabaseManager.getConnection()) {

            boolean running = true; // Used to control the menu loop

            //Loop that keeps showing the menu until the user chooses to exit
            while (running) {
                // Display the main menu
                System.out.println("What do you want to do?");
                System.out.println("1) Display all products");
                System.out.println("2) Display all customers");
                System.out.println("0) Exit");
                System.out.print("Select an option: ");

                //Read user input
                int option = scanner.nextInt();

                //Use a switch statement to handle the user's choice
                switch (option) {
                    case 1:
                        //Call the method in ProductService to show product info
                        ProductService.displayProducts(conn);
                        break;
                    case 2:
                        //Call the method in CustomerService to show customer info
                        CustomerService.displayCustomers(conn);
                        break;
                    case 0:
                        //Set running to false to exit the loop and end the program
                        running = false;
                        break;
                    default:
                        //Inform the user if they enter an invalid number
                        System.out.println("Invalid option. Please try again.");
                }
            }

        } catch (Exception e) {
            //Handle any errors that occur during the connection or while running the program
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close(); //Always close your Scanner when you're done
        }
    }
}