package com.mcconihay.nasamediaexplorer.dto;

/**
 * Request object used to create a new user account.
 * The password is provided in plain text and encoded before storage.
 */
public class UserCreateRequest {

    /** Username for the account. */
    public String username;

    /** Email address associated with the user. */
    public String email;

    /** Plain text password that will be encrypted before persistence. */
    public String password;

    /** Indicates whether the account is enabled. */
    public boolean enabled = true;
}