package com.mcconihay.nasamediaexplorer.dto;

/**
 * DTO representing the response from NASA APOD API.
 * Used to map JSON fields returned by the API.
 */
public class ApodResponse {

    /** Title of the APOD item */
    public String title;

    /** Description or explanation of the APOD item */
    public String explanation;

    /** URL to the media asset */
    public String url;

    /** Type of media (image or video) */
    public String media_type;

    /** Date of the APOD entry */
    public String date;
}