package com.mcconihay.nasamediaexplorer.dto;

import java.time.LocalDateTime;

/**
 * Response object representing a collection.
 * Returned when collection data is retrieved.
 */
public class CollectionResponse {

    /** Unique identifier of the collection. */
    public Long collectionId;

    /** Identifier of the user who owns the collection. */
    public Long userId;

    /** Name of the collection. */
    public String name;

    /** Description of the collection. */
    public String description;

    /** Timestamp indicating when the collection was created. */
    public LocalDateTime createdAt;
}