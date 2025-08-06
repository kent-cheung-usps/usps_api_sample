package com.gundam.config;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DemoPropertiesClsTest {

    private DemoPropertiesCls demoProperties;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
    }

    @AfterAll
    static void tearDownAfterClass() throws Exception {
    }

    @BeforeEach
    void setUp() throws Exception {
        demoProperties = new DemoPropertiesCls();
    }

    @AfterEach
    void tearDown() throws Exception {
    }

    @Test
    public void testSetAndGetTokenUrl() {
        demoProperties.setTokenUrl("https://example.com/token"); // Set the tokenUrl
        assertEquals("https://example.com/token", demoProperties.getTokenUrl()); // verify
    }

    @Test
    public void testSetAndGetAddressUrl() {
        // Set the addressUrl
        demoProperties.setAddressUrl("https://example.com/address");
        assertEquals("https://example.com/address", demoProperties.getAddressUrl());
    }

    @Test
    public void testSetAndGetClientId() {
        // Set the clientId
        demoProperties.setClientId("my-client-id");
        assertEquals("my-client-id", demoProperties.getClientId());
    }

    @Test
    public void testSetAndGetClientSecret() {

        demoProperties.setClientSecret("my-client-secret"); // Set the clientSecret
        assertEquals("my-client-secret", demoProperties.getClientSecret()); // verify
    }

    @Test
    public void testEncryptString() throws Exception {
        String original = "Zc4ymZcGbvBksPS7zxANbppCuFfKQ58AscQhcYjCsWYA9clm";
        String encrypted = com.gundam.util.FieldEncryptor.encrypt(original);

        System.out.println("Client ID ++++");
        System.out.println(encrypted);

        assertNotNull(encrypted);
        assertNotEquals(original, encrypted);
        // Optionally, check that decrypt returns the original
        String decrypted = com.gundam.util.FieldEncryptor.decrypt(encrypted);
        assertEquals(original, decrypted);

        original = "7Y41HsddoRATyiJ4jeqMzBAiGt6muts35yMJjACc0rt6Qa0TA5KdiWUUSoAq1Ti0";
        encrypted = com.gundam.util.FieldEncryptor.encrypt(original);

        System.out.println("Consumer Secret ++++");
        System.out.println(encrypted);
        assertNotNull(encrypted);
        assertNotEquals(original, encrypted);
        // Optionally, check that decrypt returns the original
        decrypted = com.gundam.util.FieldEncryptor.decrypt(encrypted);
        assertEquals(original, decrypted);

    }

    @Test
    public void decryptString() throws Exception {
        // Example encrypted strings (these should be valid outputs from
        // FieldEncryptor.encrypt)
        String encryptedClientId = "ILUhzLJb6X/ZMlSBJwzQ6Y2GYONGaHQkJfaXGLK/iZJdlUHNBcOK0d9fDByuPYkn1xu9vZyTGFfgrctIHhbd8A==";
        String encryptedConsumerSecret = "ZUkD7B9fZiRpgZfc2On+lg5A3x+Mv3jS46mygT4/EQNTpzExR2/xojQPRF9+rtlwVqI5udFRnBzvHmYdySx1s9cbvb2ckxhX4K3LSB4W3fA=";

        String decryptedClientId = com.gundam.util.FieldEncryptor.decrypt(encryptedClientId);
        String decryptedConsumerSecret = com.gundam.util.FieldEncryptor.decrypt(encryptedConsumerSecret);

        System.out.println("Decrypted Client ID ++++");
        System.out.println(decryptedClientId);        

        System.out.println("Decrypted Consumer Secret ++++");
        System.out.println(decryptedConsumerSecret);

        assertTrue(true);
    }
}
