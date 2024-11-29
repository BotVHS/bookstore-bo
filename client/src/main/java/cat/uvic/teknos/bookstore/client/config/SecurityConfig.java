package cat.uvic.teknos.bookstore.client.config;

public class SecurityConfig {
    public static final String CLIENT_KEYSTORE_PATH = "/keystores/client1.p12";
    public static final String CLIENT_KEYSTORE_PASSWORD = "Teknos01.";
    public static final String SERVER_ALIAS = "server";
    public static final String CLIENT_ALIAS = "client1";

    public static final String SYMMETRIC_KEY_HEADER = "X-Symmetric-Key";
    public static final String BODY_HASH_HEADER = "X-Body-Hash";

    public static final String ASYMMETRIC_ALGORITHM = "RSA";
    public static final String SYMMETRIC_ALGORITHM = "AES";
    public static final String HASH_ALGORITHM = "SHA-256";

    public static final int RSA_KEY_SIZE = 2048;
    public static final int AES_KEY_SIZE = 256;
}