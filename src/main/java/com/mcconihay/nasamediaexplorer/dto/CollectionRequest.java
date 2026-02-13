package com.mcconihay.nasamediaexplorer.dto;

/**
 * Request object used to create a new collection.
 * Contains the owning user and basic collection details.
 */
public class CollectionRequest {

    /** Identifier of the user who owns the collection. */
    public Long userId;

    /** Name of the collection. */
    public String name;

    /** Optional description of the collection. */
    public String description;
}