package cat.uvic.teknos.bookstore.client.managers;

import cat.uvic.teknos.bookstore.client.config.ApiConfig;
import cat.uvic.teknos.bookstore.client.http.HttpClientManager;
import cat.uvic.teknos.bookstore.client.http.ResponseHandler;
import cat.uvic.teknos.bookstore.client.utils.InputScanner;
import cat.uvic.teknos.bookstore.client.utils.InputValidator;

import java.net.http.HttpResponse;

public class AuthorManager {
    public static void manage() {
        while (true) {
            System.out.println("\n=== Author Management ===");
            System.out.println("1. List all authors");
            System.out.println("2. Get author by ID");
            System.out.println("3. Add new author");
            System.out.println("4. Update author");
            System.out.println("5. Delete author");
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
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/authors");
            ResponseHandler.handleWithOutput(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getById() {
        try {
            int id = InputScanner.readInt("Enter author ID: ");
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/authors/" + id);
            ResponseHandler.handleWithOutput(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void create() {
        try {
            System.out.println("Enter author details:");
            String firstName = InputScanner.readString("First Name: ");
            String lastName = InputScanner.readString("Last Name: ");
            String birthDate = InputScanner.readString("Birth Date (YYYY-MM-DD): ");
            String biography = InputScanner.readString("Biography: ");
            String nationality = InputScanner.readString("Nationality: ");

            // Validate inputs
            if (firstName.isEmpty() || lastName.isEmpty()) {
                System.out.println("Error: First name and last name are required");
                return;
            }

            if (!InputValidator.isValidDate(birthDate)) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD");
                return;
            }

            String json = """
                {
                    "firstName": "%s",
                    "lastName": "%s",
                    "birthDate": "%s",
                    "biography": "%s",
                    "nationality": "%s"
                }""".formatted(firstName, lastName, birthDate, biography, nationality);

            HttpResponse<String> response = HttpClientManager.post(ApiConfig.BASE_URL + "/authors", json);
            ResponseHandler.handle(response, "Author created successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void update() {
        try {
            int id = InputScanner.readInt("Enter author ID to update: ");

            // Verify author exists
            HttpResponse<String> checkResponse = HttpClientManager.get(ApiConfig.BASE_URL + "/authors/" + id);
            if (checkResponse.statusCode() != 200) {
                System.out.println("Error: Author not found");
                return;
            }

            System.out.println("Enter new details (press Enter to skip):");
            String firstName = InputScanner.readString("First Name: ");
            String lastName = InputScanner.readString("Last Name: ");
            String birthDate = InputScanner.readString("Birth Date (YYYY-MM-DD): ");
            String biography = InputScanner.readString("Biography: ");
            String nationality = InputScanner.readString("Nationality: ");

            if (!birthDate.isEmpty() && !InputValidator.isValidDate(birthDate)) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD");
                return;
            }

            String json = createUpdateJson(firstName, lastName, birthDate, biography, nationality);

            HttpResponse<String> response = HttpClientManager.put(ApiConfig.BASE_URL + "/authors/" + id, json);
            ResponseHandler.handle(response, "Author updated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void delete() {
        try {
            int id = InputScanner.readInt("Enter author ID to delete: ");

            HttpResponse<String> checkResponse = HttpClientManager.get(ApiConfig.BASE_URL + "/authors/" + id);
            if (checkResponse.statusCode() != 200) {
                System.out.println("Error: Author not found");
                return;
            }

            System.out.print("Are you sure you want to delete this author? (y/n): ");
            String confirm = InputScanner.readString("");
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Operation cancelled");
                return;
            }

            HttpResponse<String> response = HttpClientManager.delete(ApiConfig.BASE_URL + "/authors/" + id);
            ResponseHandler.handle(response, "Author deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String createUpdateJson(String firstName, String lastName, String birthDate, String biography, String nationality) {
        StringBuilder json = new StringBuilder("{\n");
        boolean needsComma = false;

        if (!firstName.isEmpty()) {
            json.append("    \"firstName\": \"").append(firstName).append("\"");
            needsComma = true;
        }
        if (!lastName.isEmpty()) {
            if (needsComma) json.append(",\n");
            json.append("    \"lastName\": \"").append(lastName).append("\"");
            needsComma = true;
        }
        if (!birthDate.isEmpty()) {
            if (needsComma) json.append(",\n");
            json.append("    \"birthDate\": \"").append(birthDate).append("\"");
            needsComma = true;
        }
        if (!biography.isEmpty()) {
            if (needsComma) json.append(",\n");
            json.append("    \"biography\": \"").append(biography).append("\"");
            needsComma = true;
        }
        if (!nationality.isEmpty()) {
            if (needsComma) json.append(",\n");
            json.append("    \"nationality\": \"").append(nationality).append("\"");
        }
        json.append("\n}");

        return json.toString();
    }
}