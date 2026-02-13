package com.mcconihay.nasamediaexplorer.dto;

import java.time.LocalDate;

/**
 * Request object used to create or store a NASA media item.
 * Contains metadata retrieved from external NASA APIs.
 */
public class ItemRequest {

    /** Source API that provided the item (APOD, IMAGE, or OSDR). */
    public String sourceApi;

    /** NASA-provided identifier for the item. */
    public String nasaId;

    /** Title of the media item. */
    public String title;

    /** Type of media such as image, video, or dataset. */
    public String mediaType;

    /** Description or summary of the item. */
    public String description;

    /** URL to a thumbnail image. */
    public String thumbnailUrl;

    /** URL to the primary media asset. */
    public String assetUrl;

    /** Associated APOD date when applicable. */
    public LocalDate apodDate;
}