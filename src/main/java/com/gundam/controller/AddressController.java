package com.gundam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	private static final Logger logger = LoggerFactory.getLogger(AddressController.class);
	    
    private AddressService addressService;
    
    @Autowired
    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }
    
    @PostMapping("/validate")
    public ResponseEntity<String> validateAddress(@RequestBody AddressRequest addressRequest) {
        try {
            String response = addressService.validateAddress(addressRequest);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
        	logger.error("Error while Validating Address: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
