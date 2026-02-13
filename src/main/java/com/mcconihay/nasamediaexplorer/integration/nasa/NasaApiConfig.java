package com.mcconihay.nasamediaexplorer.integration.nasa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

/**
 * Configuration class responsible for creating a shared {@link RestClient}
 * used to communicate with NASA APIs.
 *
 * The base URL is externalized in application properties to allow
 * environment-specific configuration without code changes.
 */
@Configuration
public class NasaApiConfig {

    /**
     * Creates a reusable RestClient configured with the NASA base URL.
     *
     * @param baseUrl the base endpoint for NASA APIs
     * @return configured RestClient instance
     */
    @Bean
    public RestClient restClient(
            @Value("${nasa.api.base-url}") String baseUrl) {

        return RestClient.builder()
                .baseUrl(baseUrl)
                .build();
    }
}