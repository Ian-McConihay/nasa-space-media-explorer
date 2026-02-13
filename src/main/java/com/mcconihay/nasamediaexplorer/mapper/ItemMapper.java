package com.mcconihay.nasamediaexplorer.mapper;

import com.mcconihay.nasamediaexplorer.dto.ItemRequest;
import com.mcconihay.nasamediaexplorer.dto.ItemResponse;
import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.NasaSourceApi;

/**
 * Mapper responsible for translating Item DTOs
 * to entity models and response objects.
 */
public final class ItemMapper {

    private ItemMapper() {}

    /**
     * Converts an ItemRequest into an ItemEntity.
     *
     * @param req request DTO
     * @return populated ItemEntity
     */
    public static ItemEntity toEntity(ItemRequest req) {

        ItemEntity e = new ItemEntity();

        e.setSourceApi(NasaSourceApi.valueOf(req.sourceApi.toUpperCase()));
        e.setNasaId(req.nasaId);
        e.setTitle(req.title);
        e.setMediaType(req.mediaType);
        e.setDescription(req.description);
        e.setThumbnailUrl(req.thumbnailUrl);
        e.setAssetUrl(req.assetUrl);
        e.setApodDate(req.apodDate);

        return e;
    }

    /**
     * Converts an ItemEntity into a response DTO.
     *
     * @param e item entity
     * @return ItemResponse DTO
     */
    public static ItemResponse toResponse(ItemEntity e) {

        ItemResponse r = new ItemResponse();

        r.itemId = e.getItemId();
        r.sourceApi = e.getSourceApi().name();
        r.nasaId = e.getNasaId();
        r.title = e.getTitle();
        r.mediaType = e.getMediaType();
        r.description = e.getDescription();
        r.thumbnailUrl = e.getThumbnailUrl();
        r.assetUrl = e.getAssetUrl();
        r.apodDate = e.getApodDate();
        r.fetchedAt = e.getFetchedAt();

        return r;
    }
}