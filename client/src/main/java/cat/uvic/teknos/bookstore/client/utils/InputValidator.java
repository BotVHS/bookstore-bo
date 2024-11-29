package cat.uvic.teknos.bookstore.client.utils;

public class InputValidator {
    public static boolean isValidEmail(String email) {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    public static boolean isValidDate(String date) {
        return date != null && date.matches("^\\d{4}-\\d{2}-\\d{2}$");
    }

    public static boolean isValidPrice(String price) {
        try {
            float value = Float.parseFloat(price);
            return value >= 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidRating(int rating) {
        return rating >= 1 && rating <= 5;
    }

    public static boolean isValidOrderStatus(String status) {
        return status != null &&
                (status.equals("PENDING") ||
                        status.equals("SHIPPED") ||
                        status.equals("DELIVERED"));
    }
}