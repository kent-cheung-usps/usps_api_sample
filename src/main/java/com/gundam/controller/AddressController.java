package com.gundam.controller;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gundam.dto.AddressRequest;
import com.gundam.service.AddressService;

import jakarta.annotation.PostConstruct;

@RestController
@RequestMapping("/address")
public class AddressController {

	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
	
	@Value("${proxy.http.host}")
	private String httpProxyHost;

	@Value("${proxy.http.port}")
	private String httpProxyPort;

	@Value("${proxy.https.host}")
	private String httpsProxyHost;

	@Value("${proxy.https.port}")
	private String httpsProxyPort;

	@Value("${proxy.https.protocols}")
	private String httpsProtocols;

	@Value("${proxy.required.domain}")
	private String requiredDomain;

    @Autowired
    private AddressService addressService;
    
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostConstruct
    public void configureProxySettings() {
        try {
            // Detect the current Domain
            String currentDomain = InetAddress.getLocalHost().getCanonicalHostName();
            
            logger.info("** CURRENT HOST == " + currentDomain );

            // Set USPS proxy if the current domain matches the required domain in properties file
            if (currentDomain.equalsIgnoreCase(requiredDomain)) {
                System.setProperty("http.proxyHost", httpProxyHost);
                System.setProperty("http.proxyPort", httpProxyPort);
                System.setProperty("https.proxyHost", httpsProxyHost);
                System.setProperty("https.proxyPort", httpsProxyPort);
                System.setProperty("https.protocols", httpsProtocols);
                System.out.println("Proxy settings applied for domain: " + currentDomain);
            } else {
            	logger.info("Executing outside domain " + requiredDomain + ". No proxy settings applied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error while configuring proxy settings: " + e.getMessage());
        }
    }
    
    @PostMapping("/validate")
    public ResponseEntity<String> validateAddress(@RequestBody AddressRequest addressRequest) {
        try {
            String response = addressService.validateAddress(addressRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
