package cat.uvic.teknos.bookstore.client.http;

import cat.uvic.teknos.bookstore.client.utils.JsonFormatter;

import java.net.http.HttpResponse;

public class ResponseHandler {
    public static void handle(HttpResponse<String> response, String successMessage) {
        if (response.statusCode() == 200) {
            System.out.println(successMessage);
        } else if (response.statusCode() == 404) {
            System.out.println("Error: Resource not found");
        } else if (response.statusCode() == 400) {
            System.out.println("Error: Bad request - " + response.body());
        } else {
            System.out.println("Error: " + response.body());
        }
    }

    public static void handleWithOutput(HttpResponse<String> response) {
        if (response.statusCode() == 200) {
            System.out.println(JsonFormatter.format(response.body()));
        } else {
            System.out.println("Error: " + response.body());
        }
    }
}