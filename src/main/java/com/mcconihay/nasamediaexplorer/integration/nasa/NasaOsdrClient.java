package com.mcconihay.nasamediaexplorer.integration.nasa;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Client responsible for querying NASA's Open Science Data Repository (OSDR).
 *
 * The API returns raw JSON, which is later parsed by the ingest service.
 */
@Component
public class NasaOsdrClient {

    private final RestClient restClient;

    /**
     * Constructs the OSDR client.
     *
     * @param restClient shared REST client
     */
    public NasaOsdrClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Executes a search against the OSDR dataset catalog.
     *
     * @param term search keyword
     * @return raw JSON response
     */
    public String search(String term) {

        return restClient.get()
                .uri(uri -> uri
                        .scheme("https")
                        .host("osdr.nasa.gov")
                        .path("/osdr/data/search")
                        .queryParam("term", term)
                        .queryParam("size", 25)
                        .build())
                .retrieve()
                .body(String.class);
    }
}