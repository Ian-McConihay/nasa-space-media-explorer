package com.mcconihay.nasamediaexplorer.exception;

/**
 * Thrown when an attempt is made to create a resource that already exists.
 * Typically used to enforce uniqueness constraints within the application.
 */
public class DuplicateResourceException extends RuntimeException {

    /**
     * Creates a new exception with a descriptive message.
     *
     * @param message explanation of the duplicate condition
     */
    public DuplicateResourceException(String message) {
        super(message);
    }
}