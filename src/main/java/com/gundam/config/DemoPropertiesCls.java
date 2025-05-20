package com.gundam.config;

import com.gundam.util.FieldEncryptor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "usps")
public class DemoPropertiesCls {
    private String tokenUrl;
    private String addressUrl;
    private String clientId;
    private String clientSecret;

    public String getTokenUrl() {
        return decryptIfNeeded(tokenUrl);
    }

    public void setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
    }

    public String getAddressUrl() {
        return decryptIfNeeded(addressUrl);
    }

    public void setAddressUrl(String addressUrl) {
        this.addressUrl = addressUrl;
    }

    public String getClientId() {
        return decryptIfNeeded(clientId);
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return decryptIfNeeded(clientSecret);
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    private String decryptIfNeeded(String value) {
        if (value != null && value.startsWith("{enc}")) {
            try {
                return FieldEncryptor.decrypt(value.substring(5));
            } catch (Exception ex) {
                throw new RuntimeException("Failed to decrypt property value: " + value, ex);
            }
        }
        return value;
    }
}