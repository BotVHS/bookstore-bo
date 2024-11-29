package cat.uvic.teknos.bookstore.cryptoutils;

import cat.uvic.teknos.bookstore.cryptoutils.exceptions.CryptoException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import javax.crypto.SecretKey;
import java.security.KeyPair;
import java.security.KeyPairGenerator;

import static org.junit.jupiter.api.Assertions.*;

class CryptoUtilsTest {
    private KeyPair keyPair;
    private SecretKey secretKey;
    private final String testMessage = "Test message with special chars: áéíóú !@#$%^&*()";

    @BeforeEach
    void setUp() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        keyPair = generator.generateKeyPair();

        secretKey = CryptoUtils.createSecretKey();
    }

    @Nested
    class SymmetricEncryptionTests {
        @Test
        void testSymmetricEncryptionDecryption() {
            String encrypted = CryptoUtils.encrypt(testMessage, secretKey);
            String decrypted = CryptoUtils.decrypt(encrypted, secretKey);

            assertNotEquals(testMessage, encrypted);
            assertEquals(testMessage, decrypted);
        }

        @Test
        void testSymmetricEncryptionWithEmptyString() {
            String encrypted = CryptoUtils.encrypt("", secretKey);
            String decrypted = CryptoUtils.decrypt(encrypted, secretKey);

            assertNotEquals("", encrypted);
            assertEquals("", decrypted);
        }

        @Test
        void testSymmetricEncryptionWithLongText() {
            String longText = "a".repeat(1000);
            String encrypted = CryptoUtils.encrypt(longText, secretKey);
            String decrypted = CryptoUtils.decrypt(encrypted, secretKey);

            assertNotEquals(longText, encrypted);
            assertEquals(longText, decrypted);
        }

        @Test
        void testSymmetricDecryptionWithWrongKey() {
            SecretKey wrongKey = CryptoUtils.createSecretKey();
            String encrypted = CryptoUtils.encrypt(testMessage, secretKey);

            assertThrows(CryptoException.class, () ->
                    CryptoUtils.decrypt(encrypted, wrongKey)
            );
        }
    }

    @Nested
    class AsymmetricEncryptionTests {
        @Test
        void testAsymmetricEncryptionDecryption() {
            String encrypted = CryptoUtils.asymmetricEncrypt(testMessage, keyPair.getPublic());
            String decrypted = CryptoUtils.asymmetricDecrypt(encrypted, keyPair.getPrivate());

            assertNotEquals(testMessage, encrypted);
            assertEquals(testMessage, decrypted);
        }

        @Test
        void testAsymmetricEncryptionWithEmptyString() {
            String encrypted = CryptoUtils.asymmetricEncrypt("", keyPair.getPublic());
            String decrypted = CryptoUtils.asymmetricDecrypt(encrypted, keyPair.getPrivate());

            assertNotEquals("", encrypted);
            assertEquals("", decrypted);
        }

        @Test
        void testAsymmetricDecryptionWithWrongKey() throws Exception {
            KeyPair wrongKeyPair = KeyPairGenerator.getInstance("RSA").generateKeyPair();
            String encrypted = CryptoUtils.asymmetricEncrypt(testMessage, keyPair.getPublic());

            assertThrows(CryptoException.class, () ->
                    CryptoUtils.asymmetricDecrypt(encrypted, wrongKeyPair.getPrivate())
            );
        }
    }

    @Nested
    class HashingTests {
        @Test
        void testHashConsistency() {
            String hash1 = CryptoUtils.getHash(testMessage);
            String hash2 = CryptoUtils.getHash(testMessage);

            assertEquals(hash1, hash2);
        }

        @Test
        void testHashWithEmptyInput() {
            String hash = CryptoUtils.getHash("");
            assertNotNull(hash);
            assertFalse(hash.isEmpty());
        }

        @Test
        void testDifferentInputsProduceDifferentHashes() {
            String hash1 = CryptoUtils.getHash("message1");
            String hash2 = CryptoUtils.getHash("message2");

            assertNotEquals(hash1, hash2);
        }
    }

    @Nested
    class EncodingTests {
        @Test
        void testSecretKeyEncodingDecoding() {
            String encoded = CryptoUtils.toBase64(secretKey.getEncoded());
            SecretKey decodedKey = CryptoUtils.decodeSecretKey(encoded);

            assertArrayEquals(secretKey.getEncoded(), decodedKey.getEncoded());
        }

        @Test
        void testBase64EncodingDecoding() {
            byte[] original = testMessage.getBytes();
            String encoded = CryptoUtils.toBase64(original);
            byte[] decoded = CryptoUtils.fromBase64(encoded);

            assertArrayEquals(original, decoded);
        }
    }

    @Nested
    class KeyGenerationTests {
        @Test
        void testSecretKeyProperties() {
            SecretKey key = CryptoUtils.createSecretKey();

            assertNotNull(key);
            assertEquals("AES", key.getAlgorithm());
            assertEquals(32, key.getEncoded().length);
        }

        @Test
        void testMultipleKeysAreDifferent() {
            SecretKey key1 = CryptoUtils.createSecretKey();
            SecretKey key2 = CryptoUtils.createSecretKey();

            assertNotArrayEquals(key1.getEncoded(), key2.getEncoded());
        }
    }

    private void assertNotArrayEquals(byte[] arr1, byte[] arr2) {
        assertFalse(java.util.Arrays.equals(arr1, arr2));
    }
}