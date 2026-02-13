package com.mcconihay.nasamediaexplorer.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mcconihay.nasamediaexplorer.integration.nasa.NasaOsdrClient;
import com.mcconihay.nasamediaexplorer.dto.ApodResponse;
import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.NasaSourceApi;
import com.mcconihay.nasamediaexplorer.integration.nasa.NasaApodClient;
import com.mcconihay.nasamediaexplorer.integration.nasa.NasaImageClient;
import com.mcconihay.nasamediaexplorer.repository.ItemRepository;
import com.mcconihay.nasamediaexplorer.service.NasaIngestService;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Transactional
/**
 * Default implementation of {@link NasaIngestService}.
 *
 * Coordinates external NASA API calls, transforms responses,
 * prevents duplicate records, and persists items.
 *
 * This service acts as the boundary between external data
 * providers and the internal persistence model.
 */
public class NasaIngestServiceImpl implements NasaIngestService {

    private final NasaApodClient apodClient;
    private final ItemRepository itemRepository;
    private final NasaImageClient imageClient;
    private final NasaOsdrClient osdrClient;
    private final ObjectMapper objectMapper;

    public NasaIngestServiceImpl(
            NasaApodClient apodClient,
            ItemRepository itemRepository,
            NasaImageClient imageClient,
            NasaOsdrClient osdrClient,
            ObjectMapper objectMapper) {

        this.apodClient = apodClient;
        this.itemRepository = itemRepository;
        this.imageClient = imageClient;
        this.osdrClient = osdrClient;
        this.objectMapper = objectMapper;
    }
    @Override
    public ItemEntity ingestTodayApod() {
        return ingestApodByDate(LocalDate.now());
    }

    @Override
    public ItemEntity ingestApodByDate(LocalDate date) {

        String nasaId = date.toString();

        // idempotency check
        return itemRepository
                .findBySourceApiAndNasaId(NasaSourceApi.APOD, nasaId)
                .orElseGet(() -> {

                    ApodResponse apod = apodClient.fetchByDate(date);

                    ItemEntity item = new ItemEntity();

                    item.setSourceApi(NasaSourceApi.APOD);
                    item.setNasaId(apod.date);
                    item.setTitle(apod.title);
                    item.setDescription(apod.explanation);
                    item.setMediaType(apod.media_type);
                    item.setAssetUrl(apod.url);
                    item.setApodDate(LocalDate.parse(apod.date));

                    return itemRepository.save(item);
                });
    }

    @Override
    public List<ItemEntity> ingestApodRange(LocalDate start, LocalDate end) {

        List<ApodResponse> responses =
                apodClient.fetchRange(start, end);

        return responses.stream()
                .map(apod -> {

                    return itemRepository
                            .findBySourceApiAndNasaId(
                                    NasaSourceApi.APOD,
                                    apod.date)
                            .orElseGet(() -> {

                                ItemEntity item = new ItemEntity();

                                item.setSourceApi(NasaSourceApi.APOD);
                                item.setNasaId(apod.date);
                                item.setTitle(apod.title);
                                item.setDescription(apod.explanation);
                                item.setMediaType(apod.media_type);
                                item.setAssetUrl(apod.url);
                                item.setApodDate(LocalDate.parse(apod.date));

                                return itemRepository.save(item);
                            });
                })
                .toList();
    }
    
    @Override
    public List<ItemEntity> ingestImagesByQuery(String query) {

        var response = imageClient.searchImages(query);

        if (response == null ||
            response.collection == null ||
            response.collection.items == null) {

            return List.of();
        }

        List<ItemEntity> savedItems = new ArrayList<>();

        int limit = Math.min(response.collection.items.size(), 25);

        for (int i = 0; i < limit; i++) {

            var item = response.collection.items.get(i);
            
            if (item.data == null || item.data.isEmpty()) continue;

            var data = item.data.get(0);

            String nasaId = data.nasa_id;

            if (itemRepository.existsBySourceApiAndNasaId(
                    NasaSourceApi.IMAGE,
                    nasaId)) {
                continue;
            }

            ItemEntity entity = new ItemEntity();

            entity.setSourceApi(NasaSourceApi.IMAGE);
            entity.setNasaId(nasaId);
            entity.setTitle(data.title);
            entity.setDescription(data.description);
            entity.setMediaType("image");

            if (item.links != null && !item.links.isEmpty()) {
                entity.setThumbnailUrl(item.links.get(0).href);
            }

            savedItems.add(itemRepository.save(entity));
        }

        return savedItems;
    }
    
    @Override
    public List<ItemEntity> ingestOsdrByQuery(String query) {

        try {

            String json = osdrClient.search(query);

            JsonNode root = objectMapper.readTree(json);

            JsonNode results = root
                    .path("hits")
                    .path("hits");

            List<ItemEntity> saved = new ArrayList<>();

            int limit = Math.min(results.size(), 25);

            for (int i = 0; i < limit; i++) {

                JsonNode source = results
                        .get(i)
                        .path("_source");

                String nasaId =
                        source.path("Accession").asText();

                String title =
                        source.path("Study Title")
                              .asText("OSDR Study");

                String description =
                        source.path("Study Description")
                              .asText("");

                if (!itemRepository.existsBySourceApiAndNasaId(
                        NasaSourceApi.OSDR, nasaId)) {

                    ItemEntity item = new ItemEntity();

                    item.setSourceApi(NasaSourceApi.OSDR);
                    item.setNasaId(nasaId);
                    item.setTitle(title);
                    item.setDescription(description);
                    item.setMediaType("dataset");

                    saved.add(itemRepository.save(item));
                }
            }

            return saved;

        } catch (Exception e) {

            throw new RuntimeException("Failed to ingest OSDR data", e);
        }
    }
    
}
