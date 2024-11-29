package cat.uvic.teknos.bookstore.services.security;

import cat.uvic.teknos.bookstore.cryptoutils.CryptoUtils;
import cat.uvic.teknos.bookstore.services.exception.ServerErrorException;
import rawhttp.core.RawHttpRequest;
import rawhttp.core.RawHttpResponse;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.util.Optional;

public class ServerSecurityHandler {
    private final KeyStore keyStore;
    private final String keystorePassword;
    private static final String SYMMETRIC_KEY_HEADER = "X-Symmetric-Key";
    private static final String BODY_HASH_HEADER = "X-Body-Hash";

    public ServerSecurityHandler(String keystorePath, String keystorePassword) {
        this.keystorePassword = keystorePassword;
        try {
            this.keyStore = KeyStore.getInstance("PKCS12");
            this.keyStore.load(getClass().getResourceAsStream(keystorePath), keystorePassword.toCharArray());
        } catch (Exception e) {
            throw new ServerErrorException("Error initializing security handler", e);
        }
    }

    public String decryptRequest(RawHttpRequest request) throws IOException {
        try {
            // Get encrypted symmetric key from header
            String encryptedKey = request.getHeaders().getFirst(SYMMETRIC_KEY_HEADER)
                    .orElseThrow(() -> new SecurityException("Missing symmetric key header"));

            // Get server's private key
            PrivateKey privateKey = (PrivateKey) keyStore.getKey("server", keystorePassword.toCharArray());

            // Decrypt symmetric key
            String symmetricKeyB64 = CryptoUtils.asymmetricDecrypt(encryptedKey, privateKey);
            SecretKey symmetricKey = CryptoUtils.decodeSecretKey(symmetricKeyB64);

            // Get request body if exists
            Optional<String> encryptedBody = request.getBody().map(body -> {
                try {
                    return new String(body.asRawBytes());
                } catch (IOException e) {
                    throw new ServerErrorException("Error reading request body", e);
                }
            });

            // If there's a body, verify hash and decrypt
            if (encryptedBody.isPresent()) {
                String bodyHash = request.getHeaders().getFirst(BODY_HASH_HEADER)
                        .orElseThrow(() -> new SecurityException("Missing body hash header"));

                String calculatedHash = CryptoUtils.getHash(encryptedBody.get());
                if (!bodyHash.equals(calculatedHash)) {
                    throw new SecurityException("Body hash verification failed");
                }

                return CryptoUtils.decrypt(encryptedBody.get(), symmetricKey);
            }

            return null;
        } catch (Exception e) {
            throw new ServerErrorException("Error processing encrypted request", e);
        }
    }

    public String encryptResponse(String responseBody, String encryptedKeyHeader) {
        try {
            if (responseBody == null || responseBody.isEmpty()) {
                return responseBody;
            }

            // Get server's private key
            PrivateKey privateKey = (PrivateKey) keyStore.getKey("server", keystorePassword.toCharArray());

            // Decrypt symmetric key from request
            String symmetricKeyB64 = CryptoUtils.asymmetricDecrypt(encryptedKeyHeader, privateKey);
            SecretKey symmetricKey = CryptoUtils.decodeSecretKey(symmetricKeyB64);

            // Encrypt response
            return CryptoUtils.encrypt(responseBody, symmetricKey);
        } catch (Exception e) {
            throw new ServerErrorException("Error encrypting response", e);
        }
    }
}