package com.mcconihay.nasamediaexplorer.service;

import com.mcconihay.nasamediaexplorer.entity.ItemEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * Service responsible for ingesting data from NASA APIs.
 *
 * Coordinates external API calls, transforms responses,
 * and persists items while preventing duplicates.
 */
public interface NasaIngestService {

    /**
     * Ingests the Astronomy Picture of the Day for today.
     *
     * @return saved item
     */
    ItemEntity ingestTodayApod();

    /**
     * Ingests APOD data for a specific date.
     *
     * @param date APOD date
     * @return saved item
     */
    ItemEntity ingestApodByDate(LocalDate date);

    /**
     * Ingests APOD entries across a date range.
     *
     * @param start start date
     * @param end end date
     * @return list of saved items
     */
    List<ItemEntity> ingestApodRange(LocalDate start, LocalDate end);

    /**
     * Searches and ingests NASA image assets.
     *
     * @param query search term
     * @return saved items
     */
    List<ItemEntity> ingestImagesByQuery(String query);

    /**
     * Searches and ingests OSDR research datasets.
     *
     * @param query search term
     * @return saved items
     */
    List<ItemEntity> ingestOsdrByQuery(String query);
}