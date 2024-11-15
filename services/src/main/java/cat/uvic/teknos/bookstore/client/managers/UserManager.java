package cat.uvic.teknos.bookstore.client.managers;

import cat.uvic.teknos.bookstore.client.config.ApiConfig;
import cat.uvic.teknos.bookstore.client.http.HttpClientManager;
import cat.uvic.teknos.bookstore.client.utils.InputScanner;
import cat.uvic.teknos.bookstore.client.utils.InputValidator;
import cat.uvic.teknos.bookstore.client.utils.JsonFormatter;

import java.net.http.HttpResponse;

public class UserManager {
    public static void manage() {
        while (true) {
            System.out.println("\n=== User Management ===");
            System.out.println("1. List all users");
            System.out.println("2. Get user by ID");
            System.out.println("3. Create new user");
            System.out.println("4. Update user");
            System.out.println("5. Delete user");
            System.out.println("0. Back to main menu");

            int option = InputScanner.readInt("Select an option: ");

            switch (option) {
                case 1:
                    listAll();
                    break;
                case 2:
                    getById();
                    break;
                case 3:
                    create();
                    break;
                case 4:
                    update();
                    break;
                case 5:
                    delete();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    private static void listAll() {
        try {
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/users");
            System.out.println("\nUsers:");
            System.out.println(JsonFormatter.format(response.body()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getById() {
        try {
            int id = InputScanner.readInt("Enter user ID: ");
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/users/" + id);
            if (response.statusCode() == 200) {
                System.out.println("\nUser details:");
                System.out.println(JsonFormatter.format(response.body()));
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void create() {
        try {
            System.out.println("Enter user details:");
            String firstName = InputScanner.readString("First Name: ");
            if (firstName.isEmpty()) {
                System.out.println("Error: First name is required");
                return;
            }

            String lastName = InputScanner.readString("Last Name: ");
            if (lastName.isEmpty()) {
                System.out.println("Error: Last name is required");
                return;
            }

            String email = InputScanner.readString("Email: ");
            if (!InputValidator.isValidEmail(email)) {
                System.out.println("Error: Invalid email format");
                return;
            }

            String passwordHash = InputScanner.readString("Password: ");
            if (passwordHash.isEmpty()) {
                System.out.println("Error: Password is required");
                return;
            }

            String address = InputScanner.readString("Address: ");
            if (address.isEmpty()) {
                System.out.println("Error: Address is required");
                return;
            }

            String city = InputScanner.readString("City: ");
            if (city.isEmpty()) {
                System.out.println("Error: City is required");
                return;
            }

            String country = InputScanner.readString("Country: ");
            if (country.isEmpty()) {
                System.out.println("Error: Country is required");
                return;
            }

            String postalCode = InputScanner.readString("Postal Code: ");
            if (postalCode.isEmpty()) {
                System.out.println("Error: Postal code is required");
                return;
            }

            String joinDate = InputScanner.readString("Join Date (YYYY-MM-DD): ");
            if (!InputValidator.isValidDate(joinDate)) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD");
                return;
            }

            String json = """
                {
                    "firstName": "%s",
                    "lastName": "%s",
                    "email": "%s",
                    "passwordHash": "%s",
                    "address": "%s",
                    "city": "%s",
                    "country": "%s",
                    "postalCode": "%s",
                    "joinDate": "%s"
                }""".formatted(firstName, lastName, email, passwordHash, address, city, country, postalCode, joinDate);

            HttpResponse<String> response = HttpClientManager.post(ApiConfig.BASE_URL + "/users", json);
            if (response.statusCode() == 200) {
                System.out.println("User created successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void update() {
        try {
            int id = InputScanner.readInt("Enter user ID to update: ");
            String address = InputScanner.readString("Address (Enter to skip): ");
            String city = InputScanner.readString("City (Enter to skip): ");
            String country = InputScanner.readString("Country (Enter to skip): ");
            String postalCode = InputScanner.readString("Postal Code (Enter to skip): ");

            String json = """
                {
                    "address": "%s",
                    "city": "%s",
                    "country": "%s",
                    "postalCode": "%s"
                }""".formatted(address, city, country, postalCode);

            HttpResponse<String> response = HttpClientManager.put(ApiConfig.BASE_URL + "/users/" + id, json);
            if (response.statusCode() == 200) {
                System.out.println("User updated successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void delete() {
        try {
            int id = InputScanner.readInt("Enter user ID to delete: ");
            HttpResponse<String> response = HttpClientManager.delete(ApiConfig.BASE_URL + "/users/" + id);
            if (response.statusCode() == 200) {
                System.out.println("User deleted successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}