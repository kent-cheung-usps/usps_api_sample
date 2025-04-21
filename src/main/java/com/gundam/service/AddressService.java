package com.gundam.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.gundam.dto.AddressRequest;

@Service
public class AddressService {
	private static final Logger LOGGER = Logger.getLogger(AddressService.class.getName());

	private static final String TOKEN_URL = "https://apis.usps.com/oauth2/v3/token";
	private static final String ADDRESS_URL = "https://apis.usps.com/addresses/v3/address";
//	private static final String ADDRESS_URL = "https://apis.usps.com/addresses/v3/address?streetAddress=3120%20M%20St&secondaryAddress=NW&city=Washington&state=DC";
	
	private static final String CLIENT_ID = "PG28G7RV9bMTTb52APgg7H96euRjojbJhrpI6wGSpNEIJAGZ";
	private static final String CLIENT_SECRET = "ENdIT9R658phiHUQDSGU9Mlx8qEfLlKPQdQ2QFj0CDotwZDyqK5AZhmGdjDfbG76";

	public String validateAddress(AddressRequest addressRequest) throws Exception {
        HttpClient client = HttpClient.newHttpClient();

        // Step 1: Get OAuth Token
        String accessToken = getAccessToken(client);
        if (accessToken == null) {
            throw new Exception("Failed to retrieve access token.");
        }

        LOGGER.info("Access Token retrieved successfully: " + accessToken);

        // Step 2: Construct Query Parameters
        String queryParams = String.format("streetAddress=%s&secondaryAddress=%s&city=%s&state=%s",
                addressRequest.getStreetAddress(),
                addressRequest.getSecondaryAddress(),
                addressRequest.getCity(),
                addressRequest.getState());

        // Step 3: Validate Address
//        "https://apis.usps.com/addresses/v3/address?streetAddress=3120%20M%20St&secondaryAddress=NW&city=Washington&state=DC"
        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create(ADDRESS_URL + "?" + queryParams))
        		.uri(URI.create("https://apis.usps.com/addresses/v3/address?streetAddress=3120%20M%20St&secondaryAddress=NW&city=Washington&state=DC"))
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
		requestBody.put("client_id", CLIENT_ID);
		requestBody.put("client_secret", CLIENT_SECRET);
		requestBody.put("grant_type", "client_credentials");

		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(TOKEN_URL))
				.header("Content-Type", "application/json")
				.timeout(Duration.ofSeconds(30))
				.POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
				.build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		LOGGER.info(Integer.toString(response.statusCode()));
		if (response.statusCode() != 200) {
			LOGGER.severe(
					"Token request failed with status: " + response.statusCode() + ", response: " + response.body());
			return null;
		}

		// Parse access token from response
        JSONObject jsonResponse = new JSONObject(response.body());
        String accessToken = jsonResponse.optString("access_token");
        LOGGER.info(accessToken);
        if (accessToken == null || accessToken.isEmpty()) {
            LOGGER.severe("Access token not found in response: " + response.body());
            return null;
        }
        return accessToken;
	}
}