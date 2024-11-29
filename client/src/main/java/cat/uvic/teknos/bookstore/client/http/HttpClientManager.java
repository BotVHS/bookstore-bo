package cat.uvic.teknos.bookstore.client.http;

import cat.uvic.teknos.bookstore.client.config.SecurityConfig;
import java.io.IOException;
import java.net.http.HttpResponse;

public class HttpClientManager {
    private static final SecureHttpClientManager secureClient = new SecureHttpClientManager(
            SecurityConfig.CLIENT_KEYSTORE_PATH,
            SecurityConfig.CLIENT_KEYSTORE_PASSWORD
    );

    public static HttpResponse<String> get(String url) throws IOException, InterruptedException {
        return secureClient.get(url);
    }

    public static HttpResponse<String> post(String url, String json) throws IOException, InterruptedException {
        return secureClient.post(url, json);
    }

    public static HttpResponse<String> put(String url, String json) throws IOException, InterruptedException {
        return secureClient.put(url, json);
    }

    public static HttpResponse<String> delete(String url) throws IOException, InterruptedException {
        return secureClient.delete(url);
    }
}