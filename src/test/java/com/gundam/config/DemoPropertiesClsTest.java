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
}
