package com.mcconihay.nasamediaexplorer.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.mcconihay.nasamediaexplorer.dto.ItemResponse;
import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.mapper.ItemMapper;
import com.mcconihay.nasamediaexplorer.service.NasaIngestService;

/**
 * Administrative controller responsible for ingesting data from external NASA APIs.
 *
 * All endpoints require ADMIN privileges and allow controlled population
 * of the application's item database.
 */
@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final NasaIngestService nasaIngestService;

    /**
     * Creates a new AdminController.
     *
     * @param nasaIngestService service used to retrieve and store NASA data
     */
    public AdminController(NasaIngestService nasaIngestService) {
        this.nasaIngestService = nasaIngestService;
    }

    /**
     * Ingests today's Astronomy Picture of the Day.
     *
     * @return the saved item
     */
    @PostMapping("/ingest/apod")
    public ItemResponse ingestApod() {

        ItemEntity item = nasaIngestService.ingestTodayApod();

        return ItemMapper.toResponse(item);
    }

    /**
     * Searches NASA's Image Library and stores results.
     *
     * @param query search term
     * @return list of saved items
     */
    @PostMapping("/ingest/images")
    public List<ItemResponse> ingestImages(
            @RequestParam String query) {

        return nasaIngestService
                .ingestImagesByQuery(query)
                .stream()
                .map(ItemMapper::toResponse)
                .toList();
    }

    /**
     * Searches the OSDR dataset and stores matching studies.
     *
     * @param query search term
     * @return list of saved datasets
     */
    @PostMapping("/ingest/osdr")
    public List<ItemResponse> ingestOsdr(
            @RequestParam String query) {

        return nasaIngestService
                .ingestOsdrByQuery(query)
                .stream()
                .map(ItemMapper::toResponse)
                .toList();
    }
}