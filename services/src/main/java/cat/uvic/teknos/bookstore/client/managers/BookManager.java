package cat.uvic.teknos.bookstore.client.managers;

import cat.uvic.teknos.bookstore.client.config.ApiConfig;
import cat.uvic.teknos.bookstore.client.http.HttpClientManager;
import cat.uvic.teknos.bookstore.client.http.ResponseHandler;
import cat.uvic.teknos.bookstore.client.utils.InputScanner;
import cat.uvic.teknos.bookstore.client.utils.InputValidator;

import java.net.http.HttpResponse;

public class BookManager {
    public static void manage() {
        while (true) {
            System.out.println("\n=== Book Management ===");
            System.out.println("1. List all books");
            System.out.println("2. Get book by ID");
            System.out.println("3. Add new book");
            System.out.println("4. Update book");
            System.out.println("5. Delete book");
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
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/books");
            ResponseHandler.handleWithOutput(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getById() {
        try {
            int id = InputScanner.readInt("Enter book ID: ");
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/books/" + id);
            ResponseHandler.handleWithOutput(response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void create() {
        try {
            System.out.println("Enter book details:");
            String title = InputScanner.readString("Title: ");
            if (title.isEmpty()) {
                System.out.println("Error: Title is required");
                return;
            }

            int authorId = InputScanner.readInt("Author ID: ");
            // Verify author exists
            HttpResponse<String> authorResponse = HttpClientManager.get(ApiConfig.BASE_URL + "/authors/" + authorId);
            if (authorResponse.statusCode() != 200) {
                System.out.println("Error: Author not found");
                return;
            }

            String isbn = InputScanner.readString("ISBN: ");

            String price = InputScanner.readString("Price: ");
            if (!InputValidator.isValidPrice(price)) {
                System.out.println("Error: Invalid price");
                return;
            }

            String genre = InputScanner.readString("Genre: ");
            String publishDate = InputScanner.readString("Publish Date (YYYY-MM-DD): ");
            if (!InputValidator.isValidDate(publishDate)) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD");
                return;
            }

            String publisher = InputScanner.readString("Publisher: ");
            int pageCount = InputScanner.readInt("Page Count: ");

            String json = """
                {
                    "title": "%s",
                    "author": {
                        "id": %d
                    },
                    "isbn": "%s",
                    "price": %s,
                    "genre": "%s",
                    "publishDate": "%s",
                    "publisher": "%s",
                    "pageCount": %d
                }""".formatted(title, authorId, isbn, price, genre, publishDate, publisher, pageCount);

            HttpResponse<String> response = HttpClientManager.post(ApiConfig.BASE_URL + "/books", json);
            ResponseHandler.handle(response, "Book created successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void update() {
        try {
            int id = InputScanner.readInt("Enter book ID to update: ");

            // Verify book exists
            HttpResponse<String> checkResponse = HttpClientManager.get(ApiConfig.BASE_URL + "/books/" + id);
            if (checkResponse.statusCode() != 200) {
                System.out.println("Error: Book not found");
                return;
            }

            System.out.println("Enter new details (press Enter to skip):");
            String title = InputScanner.readString("Title: ");
            String price = InputScanner.readString("Price: ");
            String genre = InputScanner.readString("Genre: ");

            if (!price.isEmpty() && !InputValidator.isValidPrice(price)) {
                System.out.println("Error: Invalid price");
                return;
            }

            String json = createUpdateJson(title, price, genre);
            HttpResponse<String> response = HttpClientManager.put(ApiConfig.BASE_URL + "/books/" + id, json);
            ResponseHandler.handle(response, "Book updated successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void delete() {
        try {
            int id = InputScanner.readInt("Enter book ID to delete: ");

            // Verify book exists
            HttpResponse<String> checkResponse = HttpClientManager.get(ApiConfig.BASE_URL + "/books/" + id);
            if (checkResponse.statusCode() != 200) {
                System.out.println("Error: Book not found");
                return;
            }

            System.out.print("Are you sure you want to delete this book? (y/n): ");
            String confirm = InputScanner.readString("");
            if (!confirm.equalsIgnoreCase("y")) {
                System.out.println("Operation cancelled");
                return;
            }

            HttpResponse<String> response = HttpClientManager.delete(ApiConfig.BASE_URL + "/books/" + id);
            ResponseHandler.handle(response, "Book deleted successfully!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static String createUpdateJson(String title, String price, String genre) {
        StringBuilder json = new StringBuilder("{\n");
        boolean needsComma = false;

        if (!title.isEmpty()) {
            json.append("    \"title\": \"").append(title).append("\"");
            needsComma = true;
        }
        if (!price.isEmpty()) {
            if (needsComma) json.append(",\n");
            json.append("    \"price\": ").append(price);
            needsComma = true;
        }
        if (!genre.isEmpty()) {
            if (needsComma) json.append(",\n");
            json.append("    \"genre\": \"").append(genre).append("\"");
        }
        json.append("\n}");

        return json.toString();
    }
}