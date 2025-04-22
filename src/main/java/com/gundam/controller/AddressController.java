package com.gundam.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gundam.dto.AddressRequest;
import com.gundam.service.AddressService;

@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @PostMapping("/validate")
    public ResponseEntity<String> validateAddress(@RequestBody AddressRequest addressRequest) {
        try {
        	
        	System.setProperty("http.proxyHost", "proxy.usps.gov");
        	System.setProperty("http.proxyPort", "8080");
        	System.setProperty("https.proxyHost", "proxy.usps.gov");
        	System.setProperty("https.proxyPort", "8080");
        	System.setProperty("https.protocols", "TLSv1.2");
        	
            String response = addressService.validateAddress(addressRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
