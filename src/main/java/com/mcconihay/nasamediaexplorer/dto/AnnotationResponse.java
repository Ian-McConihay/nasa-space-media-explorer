package com.mcconihay.nasamediaexplorer.dto;

import java.time.LocalDateTime;

/**
 * Response object representing an annotation.
 * Returned to clients when annotation data is requested.
 */
public class AnnotationResponse {

    /** Unique identifier of the annotation. */
    public Long annotationId;

    /** Identifier of the user who created the annotation. */
    public Long userId;

    /** Identifier of the annotated item. */
    public Long itemId;

    /** Text note associated with the annotation. */
    public String note;

    /** Timestamp indicating when the annotation was created. */
    public LocalDateTime createdAt;

    /** Timestamp indicating the last update to the annotation. */
    public LocalDateTime updatedAt;
}