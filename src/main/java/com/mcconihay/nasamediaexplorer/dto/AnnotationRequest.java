package com.mcconihay.nasamediaexplorer.dto;

/**
 * Request object used to create a new annotation.
 * Contains the identifiers for the user and item,
 * along with the note content submitted by the client.
 */
public class AnnotationRequest {

    /** Identifier of the user creating the annotation. */
    public Long userId;

    /** Identifier of the item being annotated. */
    public Long itemId;

    /** Text note associated with the item. */
    public String note;
}