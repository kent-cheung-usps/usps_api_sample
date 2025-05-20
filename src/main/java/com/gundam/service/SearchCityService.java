package com.gundam.service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.gundam.config.DemoPropertiesCls;

@Service
public class SearchCityService {
	private static final Logger logger = LoggerFactory.getLogger(SearchCityService.class);
	
	private final DemoPropertiesCls demoPropertiesCls;
	
	@Value("${usps.city-url}")
	private String cityUrl;


    @Autowired
    public SearchCityService(DemoPropertiesCls demoPropertiesCls) {
        this.demoPropertiesCls = demoPropertiesCls;
    }

	public String searchCityByZipcode(String zipCode) throws Exception {
		HttpClient client = HttpClient.newHttpClient();

		// Step 1: Get OAuth Token
		String accessToken = getAccessToken(client);
		if (accessToken == null) {
			throw new Exception("Failed to retrieve access token.");
		}

		logger.info("Access Token retrieved successfully: " + accessToken);

		// Step 2: Construct Query Parameters
		String queryParams = String.format("ZIPCode=%s", zipCode);

		// Step 3: Search City by ZIP Code
		logger.info("cityUrl : " + cityUrl);
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(cityUrl + "?" + queryParams))
				.header("accept", "application/json").header("authorization", "Bearer " + accessToken).GET().build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new Exception("City search request failed with status: " + response.statusCode() + ", response: "
					+ response.body());
		}

		return response.body();
	}

    private String getAccessToken(HttpClient client) throws Exception {
        JSONObject requestBody = new JSONObject();
        requestBody.put("client_id", demoPropertiesCls.getClientId());
        requestBody.put("client_secret", demoPropertiesCls.getClientSecret());
        requestBody.put("grant_type", "client_credentials");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(demoPropertiesCls.getTokenUrl()))
                .header("Content-Type", "application/json")
                .timeout(Duration.ofSeconds(30))
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        logger.info(Integer.toString(response.statusCode()));
        if (response.statusCode() != 200) {
            logger.error(
                    "Token request failed with status: " + response.statusCode() + ", response: " + response.body());
            return null;
        }

        JSONObject responseBody = new JSONObject(response.body());
        return responseBody.getString("access_token");
    }}
