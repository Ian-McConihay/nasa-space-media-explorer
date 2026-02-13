package com.mcconihay.nasamediaexplorer.integration.nasa;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.mcconihay.nasamediaexplorer.dto.ApodResponse;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

/**
 * Client responsible for interacting with NASA's
 * Astronomy Picture of the Day (APOD) API.
 *
 * Provides methods for retrieving today's image,
 * a specific date, or a date range.
 */
@Component
public class NasaApodClient {

    private final RestClient restClient;
    private final String apiKey;

    /**
     * Constructs the APOD client.
     *
     * @param restClient shared REST client
     * @param apiKey NASA API key
     */
    public NasaApodClient(
            RestClient restClient,
            @Value("${nasa.api.key}") String apiKey) {

        this.restClient = restClient;
        this.apiKey = apiKey;
    }

    /**
     * Retrieves today's Astronomy Picture of the Day.
     *
     * @return APOD response
     */
    public ApodResponse fetchToday() {

        return restClient.get()
                .uri(uri -> uri
                        .path("/planetary/apod")
                        .queryParam("api_key", apiKey)
                        .build())
                .retrieve()
                .body(ApodResponse.class);
    }

    /**
     * Retrieves the APOD entry for a specific date.
     *
     * @param date the requested date
     * @return APOD response
     */
    public ApodResponse fetchByDate(LocalDate date) {

        return restClient.get()
                .uri(uri -> uri
                        .path("/planetary/apod")
                        .queryParam("api_key", apiKey)
                        .queryParam("date", date)
                        .build())
                .retrieve()
                .body(ApodResponse.class);
    }

    /**
     * Retrieves APOD entries for a date range.
     * NASA returns an array for this endpoint.
     *
     * @param start start date
     * @param end end date
     * @return list of APOD responses
     */
    public List<ApodResponse> fetchRange(
            LocalDate start,
            LocalDate end) {

        ApodResponse[] response =
                restClient.get()
                        .uri(uri -> uri
                                .path("/planetary/apod")
                                .queryParam("api_key", apiKey)
                                .queryParam("start_date", start)
                                .queryParam("end_date", end)
                                .build())
                        .retrieve()
                        .body(ApodResponse[].class);

        return Arrays.asList(response);
    }
}