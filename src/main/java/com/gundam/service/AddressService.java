package com.gundam.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gundam.dto.AddressRequest;


@Service
public class AddressService {
    private static final Logger logger = LoggerFactory.getLogger(AddressService.class);

    @Value("${usps.token-url}")
    private String tokenUrl;

    @Value("${usps.address-url}")
    private String addressUrl;

    @Value("${usps.client-id}")
    private String clientId;

    @Value("${usps.client-secret}")
    private String clientSecret;

    public String validateAddress(AddressRequest addressRequest) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Step 1: Get OAuth Token
        String accessToken = getAccessToken(client);
        if (accessToken == null) {
            throw new Exception("Failed to retrieve access token.");
        }

        logger.info("Access Token retrieved successfully: " + accessToken);

        // Step 2: Construct Query Parameters
        String queryParams = String.format("streetAddress=%s&secondaryAddress=%s&city=%s&state=%s",
                addressRequest.getStreetAddress(),
                addressRequest.getSecondaryAddress(),
                addressRequest.getCity(),
                addressRequest.getState());

        // Step 3: Validate Address
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(addressUrl + "?" + queryParams))
                .header("accept", "application/json")
                .header("x-user-id", "") // Empty x-user-id as per USPS requirements
                .header("authorization", "Bearer " + accessToken)
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() != 200) {
            throw new Exception("Address request failed with status: " + response.statusCode() + ", response: " + response.body());
        }

        return response.body();
    }

    private String getAccessToken(HttpClient client) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("client_id", clientId);
        requestBody.put("client_secret", clientSecret);
        requestBody.put("grant_type", "client_credentials");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(tokenUrl))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(30))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(Integer.toString(response.statusCode()));
        if (response.statusCode() != 200) {
        	logger.error("Token request failed with status: " + response.statusCode() + ", response: " + response.body());
            return null;
        }

        JSONObject responseBody = new JSONObject(response.body());
        return responseBody.getString("access_token");
    }
}
