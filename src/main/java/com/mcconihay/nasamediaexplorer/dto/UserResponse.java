package com.mcconihay.nasamediaexplorer.dto;

import java.time.LocalDateTime;

/**
 * Response object representing a user.
 * Returned when user information is requested.
 */
public class UserResponse {

    /** Unique identifier of the user. */
    public Long userId;

    /** Username of the account. */
    public String username;

    /** Email address of the user. */
    public String email;

    /** Indicates whether the account is active. */
    public boolean enabled;

    /** Timestamp indicating when the user account was created. */
    public LocalDateTime createdAt;
}