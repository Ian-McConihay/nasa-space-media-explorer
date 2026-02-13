package com.mcconihay.nasamediaexplorer.integration.nasa;

import java.util.List;

/**
 * Represents the JSON response returned by NASA's image search API.
 * Structured to mirror the nested format of the external payload.
 */
public class NasaImageSearchResponse {

    /** Root collection object. */
    public Collection collection;

    /**
     * Container holding the returned items.
     */
    public static class Collection {
        public List<Item> items;
    }

    /**
     * Individual search result entry.
     */
    public static class Item {
        public List<Data> data;
        public List<Link> links;
    }

    /**
     * Metadata describing the media asset.
     */
    public static class Data {
        public String nasa_id;
        public String title;
        public String description;
    }

    /**
     * Link to a preview or thumbnail image.
     */
    public static class Link {
        public String href;
    }
}