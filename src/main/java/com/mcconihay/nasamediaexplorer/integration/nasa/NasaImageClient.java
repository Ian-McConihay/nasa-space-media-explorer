package com.mcconihay.nasamediaexplorer.integration.nasa;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Client used to search NASA's public image library.
 *
 * This client targets a different host than the core NASA API,
 * so the full URI is constructed for each request.
 */
@Component
public class NasaImageClient {

    private final RestClient restClient;

    /**
     * Constructs the image client.
     *
     * @param restClient shared REST client
     */
    public NasaImageClient(RestClient restClient) {
        this.restClient = restClient;
    }

    /**
     * Searches for images matching the provided query.
     *
     * @param query search keyword
     * @return image search response
     */
    public NasaImageSearchResponse searchImages(String query) {

        return restClient.get()
                .uri(uri -> uri
                        .scheme("https")
                        .host("images-api.nasa.gov")
                        .path("/search")
                        .queryParam("q", query)
                        .queryParam("media_type", "image")
                        .build())
                .retrieve()
                .body(NasaImageSearchResponse.class);
    }
}