package com.pluralsight;

import java.sql.*;//SQL classes for database communication
import java.util.Scanner;//To read user input from the console

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // Used to get user input

        //Search actors by last name
        try {
            System.out.print("Enter an actor's last name: ");
            String lastName = scanner.nextLine();

            //SQL to get actors with matching last name
            String sql = "SELECT actor_id, first_name, last_name FROM actor WHERE last_name = ?";

            try (
                    Connection conn = DatabaseManager.getConnection();//Get connection from pool
                    PreparedStatement stmt = conn.prepareStatement(sql) // Create prepared query
            ) {
                stmt.setString(1, lastName); // Plug in user input safely

                try (ResultSet rs = stmt.executeQuery()) {
                    boolean found = false;

                    System.out.println("\nMatching actors:");
                    while (rs.next()) {
                        found = true;
                        int id = rs.getInt("actor_id");
                        String first = rs.getString("first_name");
                        String last = rs.getString("last_name");

                        System.out.printf("ID: %-3d  Name: %s %s\n", id, first, last);
                    }

                    if (!found) {
                        System.out.println("No actors found with last name: " + lastName);
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }

        // ----------------------
        // PART 2: Show films for selected actor
        // ----------------------
        try {
            System.out.print("\nEnter actor's first name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter actor's last name: ");
            String lastName = scanner.nextLine();

            // SQL to find movies for a given actor name
            String sql = """
                SELECT f.title
                FROM film f
                JOIN film_actor fa ON f.film_id = fa.film_id
                JOIN actor a ON a.actor_id = fa.actor_id
                WHERE a.first_name = ? AND a.last_name = ?
                ORDER BY f.title
                """;

            try (
                    Connection conn = DatabaseManager.getConnection();
                    PreparedStatement stmt = conn.prepareStatement(sql)
            ) {
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);

                try (ResultSet rs = stmt.executeQuery()) {
                    boolean hasMovies = false;

                    System.out.println("\nMovies featuring " + firstName + " " + lastName + ":");
                    while (rs.next()) {
                        hasMovies = true;
                        String title = rs.getString("title");
                        System.out.println("- " + title);
                    }

                    if (!hasMovies) {
                        System.out.println("No movies found for that actor.");
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving movies: " + e.getMessage());
        }

        // Close the scanner at the very end
        scanner.close();
    }
}
