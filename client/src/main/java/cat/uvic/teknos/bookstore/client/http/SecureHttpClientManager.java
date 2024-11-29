package cat.uvic.teknos.bookstore.client.http;

import cat.uvic.teknos.bookstore.client.config.ApiConfig;
import cat.uvic.teknos.bookstore.cryptoutils.CryptoUtils;

import javax.crypto.SecretKey;
import javax.net.ssl.SSLSession;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.util.Arrays;
import java.util.Optional;

public class SecureHttpClientManager {
    private static final HttpClient client = HttpClient.newHttpClient();
    private final KeyStore keyStore;
    private final Certificate serverCertificate;
    private final String keystorePassword;

    public SecureHttpClientManager(String keystorePath, String keystorePassword) {
        this.keystorePassword = keystorePassword;
        try {
            this.keyStore = KeyStore.getInstance("PKCS12");
            this.keyStore.load(getClass().getResourceAsStream(keystorePath), keystorePassword.toCharArray());
            this.serverCertificate = keyStore.getCertificate("server");
        } catch (Exception e) {
            throw new RuntimeException("Error initializing secure client", e);
        }
    }

    public HttpResponse<String> get(String url) throws IOException, InterruptedException {
        // Para GET, solo necesitamos una clave simétrica para descifrar la respuesta
        SecretKey symmetricKey = CryptoUtils.createSecretKey();
        String encryptedKey = CryptoUtils.asymmetricEncrypt(
                CryptoUtils.toBase64(symmetricKey.getEncoded()),
                serverCertificate.getPublicKey()
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("X-Symmetric-Key", encryptedKey)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, symmetricKey);
    }

    public HttpResponse<String> post(String url, String body) throws IOException, InterruptedException {
        // Crear y encriptar clave simétrica
        SecretKey symmetricKey = CryptoUtils.createSecretKey();
        String encryptedKey = CryptoUtils.asymmetricEncrypt(
                CryptoUtils.toBase64(symmetricKey.getEncoded()),
                serverCertificate.getPublicKey()
        );

        // Encriptar cuerpo y calcular hash
        String encryptedBody = CryptoUtils.encrypt(body, symmetricKey);
        String bodyHash = CryptoUtils.getHash(encryptedBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("X-Symmetric-Key", encryptedKey)
                .header("X-Body-Hash", bodyHash)
                .POST(HttpRequest.BodyPublishers.ofString(encryptedBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, symmetricKey);
    }

    public HttpResponse<String> put(String url, String body) throws IOException, InterruptedException {
        SecretKey symmetricKey = CryptoUtils.createSecretKey();
        String encryptedKey = CryptoUtils.asymmetricEncrypt(
                CryptoUtils.toBase64(symmetricKey.getEncoded()),
                serverCertificate.getPublicKey()
        );

        String encryptedBody = CryptoUtils.encrypt(body, symmetricKey);
        String bodyHash = CryptoUtils.getHash(encryptedBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("X-Symmetric-Key", encryptedKey)
                .header("X-Body-Hash", bodyHash)
                .PUT(HttpRequest.BodyPublishers.ofString(encryptedBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, symmetricKey);
    }

    public HttpResponse<String> delete(String url) throws IOException, InterruptedException {
        SecretKey symmetricKey = CryptoUtils.createSecretKey();
        String encryptedKey = CryptoUtils.asymmetricEncrypt(
                CryptoUtils.toBase64(symmetricKey.getEncoded()),
                serverCertificate.getPublicKey()
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("X-Symmetric-Key", encryptedKey)
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return handleResponse(response, symmetricKey);
    }

    private HttpResponse<String> handleResponse(HttpResponse<String> response, SecretKey symmetricKey) {
        try {
            if (response.statusCode() == 200 && response.body() != null && !response.body().isEmpty()) {
                // Verificar hash si existe
                String responseHash = response.headers().firstValue("X-Body-Hash").orElse(null);
                if (responseHash != null) {
                    String calculatedHash = CryptoUtils.getHash(response.body());
                    if (!responseHash.equals(calculatedHash)) {
                        throw new SecurityException("Response body hash verification failed");
                    }
                }

                // Descifrar cuerpo si existe
                String decryptedBody = CryptoUtils.decrypt(response.body(), symmetricKey);
                return new HttpResponse<String>() {
                    @Override
                    public int statusCode() {
                        return response.statusCode();
                    }

                    @Override
                    public HttpRequest request() {
                        return response.request();
                    }

                    @Override
                    public Optional<HttpResponse<String>> previousResponse() {
                        return response.previousResponse();
                    }

                    @Override
                    public HttpHeaders headers() {
                        return response.headers();
                    }

                    @Override
                    public String body() {
                        return decryptedBody;
                    }

                    @Override
                    public Optional<SSLSession> sslSession() {
                        return response.sslSession();
                    }

                    @Override
                    public URI uri() {
                        return response.uri();
                    }

                    @Override
                    public HttpClient.Version version() {
                        return response.version();
                    }
                };
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error processing secure response", e);
        }
    }

    public void verifyServerCertificate() {
        try {
            serverCertificate.verify(serverCertificate.getPublicKey());
        } catch (Exception e) {
            throw new SecurityException("Server certificate verification failed", e);
        }
    }
}