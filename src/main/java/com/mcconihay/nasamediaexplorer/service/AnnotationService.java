package com.mcconihay.nasamediaexplorer.service;

import com.mcconihay.nasamediaexplorer.entity.AnnotationEntity;

import java.util.List;

/**
 * Service responsible for managing annotations.
 *
 * Provides operations for creating, retrieving,
 * and deleting user notes associated with items.
 */
public interface AnnotationService {

    /**
     * Persists a new annotation.
     *
     * @param annotation annotation to save
     * @return saved annotation
     */
    AnnotationEntity saveAnnotation(AnnotationEntity annotation);

    /**
     * Retrieves annotations linked to a specific item.
     *
     * @param itemId item identifier
     * @return list of annotations
     */
    List<AnnotationEntity> findByItemId(Long itemId);

    /**
     * Deletes an annotation by identifier.
     *
     * @param id annotation identifier
     */
    void deleteAnnotation(Long id);
}