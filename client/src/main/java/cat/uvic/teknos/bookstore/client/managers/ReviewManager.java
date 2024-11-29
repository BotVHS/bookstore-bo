package cat.uvic.teknos.bookstore.client.managers;

import cat.uvic.teknos.bookstore.client.config.ApiConfig;
import cat.uvic.teknos.bookstore.client.http.HttpClientManager;
import cat.uvic.teknos.bookstore.client.utils.InputScanner;
import cat.uvic.teknos.bookstore.client.utils.InputValidator;
import cat.uvic.teknos.bookstore.client.utils.JsonFormatter;

import java.net.http.HttpResponse;

public class ReviewManager {
    public static void manage() {
        while (true) {
            System.out.println("\n=== Review Management ===");
            System.out.println("1. List all reviews");
            System.out.println("2. Get review by ID");
            System.out.println("3. Add new review");
            System.out.println("4. Update review");
            System.out.println("5. Delete review");
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
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/reviews");
            System.out.println("\nReviews:");
            System.out.println(JsonFormatter.format(response.body()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getById() {
        try {
            int id = InputScanner.readInt("Enter review ID: ");
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/reviews/" + id);
            if (response.statusCode() == 200) {
                System.out.println("\nReview details:");
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
            System.out.println("Enter review details:");
            int bookId = InputScanner.readInt("Book ID: ");
            int userId = InputScanner.readInt("User ID: ");
            int rating = InputScanner.readInt("Rating (1-5): ");
            if (rating < 1 || rating > 5) {
                System.out.println("Error: Rating must be between 1 and 5");
                return;
            }
            String comment = InputScanner.readString("Comment: ");
            String reviewDate = InputScanner.readString("Review Date (YYYY-MM-DD): ");
            if (!InputValidator.isValidDate(reviewDate)) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD");
                return;
            }

            String json = """
                {
                    "book": {
                        "id": %d
                    },
                    "user": {
                        "id": %d
                    },
                    "rating": %d,
                    "comment": "%s",
                    "reviewDate": "%s"
                }""".formatted(bookId, userId, rating, comment, reviewDate);

            HttpResponse<String> response = HttpClientManager.post(ApiConfig.BASE_URL + "/reviews", json);
            if (response.statusCode() == 200) {
                System.out.println("Review created successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void update() {
        try {
            int id = InputScanner.readInt("Enter review ID to update: ");
            String rating = InputScanner.readString("Rating (1-5) (Enter to skip): ");
            if (!rating.isEmpty() && (Integer.parseInt(rating) < 1 || Integer.parseInt(rating) > 5)) {
                System.out.println("Error: Rating must be between 1 and 5");
                return;
            }
            String comment = InputScanner.readString("Comment (Enter to skip): ");

            String json = """
                {
                    "rating": %s,
                    "comment": "%s"
                }""".formatted(rating.isEmpty() ? "null" : rating, comment);

            HttpResponse<String> response = HttpClientManager.put(ApiConfig.BASE_URL + "/reviews/" + id, json);
            if (response.statusCode() == 200) {
                System.out.println("Review updated successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void delete() {
        try {
            int id = InputScanner.readInt("Enter review ID to delete: ");
            HttpResponse<String> response = HttpClientManager.delete(ApiConfig.BASE_URL + "/reviews/" + id);
            if (response.statusCode() == 200) {
                System.out.println("Review deleted successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}