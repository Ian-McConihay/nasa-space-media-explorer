package com.mcconihay.nasamediaexplorer.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Response object representing a stored NASA media item.
 * Returned when item data is requested.
 */
public class ItemResponse {

    /** Unique identifier of the item. */
    public Long itemId;

    /** Source API from which the item originated. */
    public String sourceApi;

    /** NASA-provided identifier. */
    public String nasaId;

    /** Title of the item. */
    public String title;

    /** Media type of the item. */
    public String mediaType;

    /** Description of the item. */
    public String description;

    /** Thumbnail image URL. */
    public String thumbnailUrl;

    /** Primary asset URL. */
    public String assetUrl;

    /** APOD date when applicable. */
    public LocalDate apodDate;

    /** Timestamp indicating when the item was fetched and stored. */
    public LocalDateTime fetchedAt;
}