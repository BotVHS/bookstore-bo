package cat.uvic.teknos.bookstore.client.managers;

import cat.uvic.teknos.bookstore.client.config.ApiConfig;
import cat.uvic.teknos.bookstore.client.http.HttpClientManager;
import cat.uvic.teknos.bookstore.client.utils.InputScanner;
import cat.uvic.teknos.bookstore.client.utils.InputValidator;
import cat.uvic.teknos.bookstore.client.utils.JsonFormatter;

import java.net.http.HttpResponse;

public class OrderManager {
    public static void manage() {
        while (true) {
            System.out.println("\n=== Order Management ===");
            System.out.println("1. List all orders");
            System.out.println("2. Get order by ID");
            System.out.println("3. Create new order");
            System.out.println("4. Update order");
            System.out.println("5. Delete order");
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
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/orders");
            System.out.println("\nOrders:");
            System.out.println(JsonFormatter.format(response.body()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getById() {
        try {
            int id = InputScanner.readInt("Enter order ID: ");
            HttpResponse<String> response = HttpClientManager.get(ApiConfig.BASE_URL + "/orders/" + id);
            if (response.statusCode() == 200) {
                System.out.println("\nOrder details:");
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
            System.out.println("Enter order details:");
            int userId = InputScanner.readInt("User ID: ");
            String orderDate = InputScanner.readString("Order Date (YYYY-MM-DD): ");
            if (!InputValidator.isValidDate(orderDate)) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD");
                return;
            }
            float totalPrice = Float.parseFloat(InputScanner.readString("Total Price: "));
            if (!InputValidator.isValidPrice(String.valueOf(totalPrice))) {
                System.out.println("Error: Invalid price");
                return;
            }
            String shippingAddress = InputScanner.readString("Shipping Address: ");
            String orderStatus = InputScanner.readString("Order Status (PENDING/SHIPPED/DELIVERED): ");
            String json = """
                {
                    "user": {
                        "id": %d
                    },
                    "orderDate": "%s",
                    "totalPrice": %.2f,
                    "shippingAddress": "%s",
                    "orderStatus": "%s"
                }""".formatted(userId, orderDate, totalPrice, shippingAddress, orderStatus);

            HttpResponse<String> response = HttpClientManager.post(ApiConfig.BASE_URL + "/orders", json);
            if (response.statusCode() == 200) {
                System.out.println("Order created successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void update() {
        try {
            int id = InputScanner.readInt("Enter order ID to update: ");
            String orderStatus = InputScanner.readString("Order Status (Enter to skip): ");
            String shippingAddress = InputScanner.readString("Shipping Address (Enter to skip): ");
            String json = """
                {
                    "orderStatus": "%s",
                    "shippingAddress": "%s"
                }""".formatted(orderStatus, shippingAddress);

            HttpResponse<String> response = HttpClientManager.put(ApiConfig.BASE_URL + "/orders/" + id, json);
            if (response.statusCode() == 200) {
                System.out.println("Order updated successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void delete() {
        try {
            int id = InputScanner.readInt("Enter order ID to delete: ");
            HttpResponse<String> response = HttpClientManager.delete(ApiConfig.BASE_URL + "/orders/" + id);
            if (response.statusCode() == 200) {
                System.out.println("Order deleted successfully!");
            } else {
                System.out.println("Error: " + response.body());
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}