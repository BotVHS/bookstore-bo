package cat.uvic.teknos.bookstore.client.utils;

public class JsonFormatter {
    public static String format(String json) {
        return json.replace(",", ",\n")
                .replace("{", "{\n")
                .replace("}", "\n}");
    }
}