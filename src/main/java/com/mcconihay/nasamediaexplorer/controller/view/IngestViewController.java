package com.mcconihay.nasamediaexplorer.controller.view;

import com.mcconihay.nasamediaexplorer.service.NasaIngestService;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Handles ingest operations triggered from the UI.
 */
@Controller
@RequestMapping("/admin/ingest")
public class IngestViewController {

    private final NasaIngestService ingestService;

    public IngestViewController(NasaIngestService ingestService) {
        this.ingestService = ingestService;
    }

    /**
     * Displays ingest page.
     */
    @GetMapping
    public String ingestPage() {
        return "admin/ingest";
    }

    /**
     * Ingest APOD today.
     */
    @PostMapping("/apod")
    public String ingestToday() {

        ingestService.ingestTodayApod();

        return "redirect:/items";
    }

    /**
     * Ingest NASA images.
     */
    @PostMapping("/images")
    public String ingestImages(@RequestParam String query) {

        ingestService.ingestImagesByQuery(query);

        return "redirect:/items";
    }

    /**
     * Ingest OSDR datasets.
     */
    @PostMapping("/osdr")
    public String ingestOsdr(@RequestParam String query) {

        ingestService.ingestOsdrByQuery(query);

        return "redirect:/items";
    }
}