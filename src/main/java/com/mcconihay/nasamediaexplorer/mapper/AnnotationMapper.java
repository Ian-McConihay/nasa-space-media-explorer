package com.mcconihay.nasamediaexplorer.mapper;

import com.mcconihay.nasamediaexplorer.dto.AnnotationRequest;
import com.mcconihay.nasamediaexplorer.dto.AnnotationResponse;
import com.mcconihay.nasamediaexplorer.entity.AnnotationEntity;
import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;

/**
 * Utility class responsible for converting Annotation objects
 * between DTOs and entity models.
 *
 * This class is not intended to be instantiated.
 */
public final class AnnotationMapper {

    private AnnotationMapper() {}

    /**
     * Converts an AnnotationRequest into an AnnotationEntity.
     *
     * @param req  incoming request DTO
     * @param user associated user
     * @param item associated item
     * @return populated AnnotationEntity
     */
    public static AnnotationEntity toEntity(AnnotationRequest req, UserEntity user, ItemEntity item) {
        AnnotationEntity e = new AnnotationEntity();
        e.setUser(user);
        e.setItem(item);
        e.setNote(req.note);
        return e;
    }

    /**
     * Converts an AnnotationEntity into a response DTO.
     *
     * @param e annotation entity
     * @return AnnotationResponse DTO
     */
    public static AnnotationResponse toResponse(AnnotationEntity e) {
        AnnotationResponse r = new AnnotationResponse();
        r.annotationId = e.getAnnotationId();
        r.userId = (e.getUser() != null) ? e.getUser().getUserId() : null;
        r.itemId = (e.getItem() != null) ? e.getItem().getItemId() : null;
        r.note = e.getNote();
        r.createdAt = e.getCreatedAt();
        r.updatedAt = e.getUpdatedAt();
        return r;
    }
}