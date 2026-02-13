package com.mcconihay.nasamediaexplorer.mapper;

import com.mcconihay.nasamediaexplorer.dto.CollectionRequest;
import com.mcconihay.nasamediaexplorer.dto.CollectionResponse;
import com.mcconihay.nasamediaexplorer.entity.CollectionEntity;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;

/**
 * Utility class for converting Collection DTOs
 * to entity models and vice versa.
 */
public final class CollectionMapper {

    private CollectionMapper() {}

    /**
     * Converts a CollectionRequest into a CollectionEntity.
     *
     * @param req  request DTO
     * @param user owning user
     * @return CollectionEntity
     */
    public static CollectionEntity toEntity(CollectionRequest req, UserEntity user) {
        CollectionEntity e = new CollectionEntity();
        e.setUser(user);
        e.setName(req.name);
        e.setDescription(req.description);
        return e;
    }

    /**
     * Converts a CollectionEntity into a response DTO.
     *
     * @param e collection entity
     * @return CollectionResponse DTO
     */
    public static CollectionResponse toResponse(CollectionEntity e) {
        CollectionResponse r = new CollectionResponse();
        r.collectionId = e.getCollectionId();
        r.userId = (e.getUser() != null) ? e.getUser().getUserId() : null;
        r.name = e.getName();
        r.description = e.getDescription();
        r.createdAt = e.getCreatedAt();
        return r;
    }
}