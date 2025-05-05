package com.gundam.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gundam.dto.SearchCityRequest;
import com.gundam.service.SearchCityService;

@RestController
@RequestMapping("/search")
public class SearchCityController {
    private static final Logger logger = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private SearchCityService searchCityService;

    @PostMapping("/city")
    public ResponseEntity<String> searchCityByZipcode(@RequestBody SearchCityRequest searchCityRequest) {
        try {
            String response = searchCityService.searchCityByZipcode(searchCityRequest.getZipCode());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error while searching city by ZIP code: " + e.getMessage(), e);
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
