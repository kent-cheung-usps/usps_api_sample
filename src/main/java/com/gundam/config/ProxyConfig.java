package com.gundam.config;

import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gundam.controller.AddressController;

import jakarta.annotation.PostConstruct;

@Component
public class ProxyConfig {
	
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

    private String currentFQDN;

    @PostConstruct
    public void configureProxySettings() {
        try {
            // Detect the current Domain
        	if( currentFQDN == null )
        		currentFQDN = InetAddress.getLocalHost().getCanonicalHostName();
            
            logger.info("** CURRENT FQDN == " + currentFQDN );

            // Set USPS proxy if the current domain matches the required domain in properties file
            if (currentFQDN.toLowerCase().contains(requiredDomain.toLowerCase())) {
                System.setProperty("http.proxyHost", httpProxyHost);
                System.setProperty("http.proxyPort", httpProxyPort);
                System.setProperty("https.proxyHost", httpsProxyHost);
                System.setProperty("https.proxyPort", httpsProxyPort);
                System.setProperty("https.protocols", httpsProtocols);
                System.out.println("Proxy settings applied for domain: " + currentFQDN);
            } else {
            	logger.info("Executing outside domain " + requiredDomain + ". No proxy settings applied.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("Error while configuring proxy settings: " + e.getMessage());
        }

    }
}