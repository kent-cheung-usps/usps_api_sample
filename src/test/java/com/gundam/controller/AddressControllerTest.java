package com.gundam.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Field;
import java.net.InetAddress;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.gundam.dto.AddressRequest;
import com.gundam.service.AddressService;

public class AddressControllerTest {

    private AddressService addressService;
    private AddressController addressController;
    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        // Create a mock of AddressService
        addressService = mock(AddressService.class);

        // Inject the mock into AddressController
        addressController = new AddressController(addressService);

        // Set up MockMvc
        mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    public void testConfigureProxySettings_DomainMatches() throws Exception {
        // Use reflection to set the private currentDomain variable
        Field currentDomainField = AddressController.class.getDeclaredField("currentDomain");
        currentDomainField.setAccessible(true);
        currentDomainField.set(addressController, "required.domain.com");

        // Use reflection to set the requiredDomain value
        Field requiredDomainField = AddressController.class.getDeclaredField("requiredDomain");
        requiredDomainField.setAccessible(true);
        requiredDomainField.set(addressController, "required.domain.com");

        // Use reflection to set the httpProxyHost value
        Field httpProxyHostField = AddressController.class.getDeclaredField("httpProxyHost");
        httpProxyHostField.setAccessible(true);
        httpProxyHostField.set(addressController, "localhost");

        // Use reflection to set the httpProxyPort value
        Field httpProxyPortField = AddressController.class.getDeclaredField("httpProxyPort");
        httpProxyPortField.setAccessible(true);
        httpProxyPortField.set(addressController, "8080");

        // Use reflection to set the httpsProxyHost value
        Field httpsProxyHostField = AddressController.class.getDeclaredField("httpsProxyHost");
        httpsProxyHostField.setAccessible(true);
        httpsProxyHostField.set(addressController, "localhost");

        // Use reflection to set the httpsProxyPort value
        Field httpsProxyPortField = AddressController.class.getDeclaredField("httpsProxyPort");
        httpsProxyPortField.setAccessible(true);
        httpsProxyPortField.set(addressController, "8443");

        // Use reflection to set the httpsProtocols value
        Field httpsProtocolsField = AddressController.class.getDeclaredField("httpsProtocols");
        httpsProtocolsField.setAccessible(true);
        httpsProtocolsField.set(addressController, "TLSv1.2");

        // Call the method
        addressController.configureProxySettings();

        // Verify that system properties are set
        assertEquals("localhost", System.getProperty("http.proxyHost"));
        assertEquals("8080", System.getProperty("http.proxyPort"));
        assertEquals("localhost", System.getProperty("https.proxyHost"));
        assertEquals("8443", System.getProperty("https.proxyPort"));
        assertEquals("TLSv1.2", System.getProperty("https.protocols"));
    }
    
    @Test
    public void testValidateAddress_Success() throws Exception {
        // Mock the AddressService response
        when(addressService.validateAddress(any(AddressRequest.class))).thenReturn("Valid Address");

        // Perform the POST request
        mockMvc.perform(post("/address/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"123 Test St\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Valid Address"));

        // Verify that the service was called
        verify(addressService, times(1)).validateAddress(any(AddressRequest.class));
    }

    @Test
    public void testValidateAddress_Failure() throws Exception {
        // Mock the AddressService to throw an exception
        when(addressService.validateAddress(any(AddressRequest.class))).thenThrow(new RuntimeException("Service error"));

        // Perform the POST request
        mockMvc.perform(post("/address/validate")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"address\":\"123 Test St\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error: Service error"));

        // Verify that the service was called
        verify(addressService, times(1)).validateAddress(any(AddressRequest.class));
    }
}