package com.mcconihay.nasamediaexplorer.exception;

/**
 * Thrown when a requested resource cannot be found.
 * Commonly used in service layers when database records do not exist.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates a new exception with a descriptive message.
     *
     * @param message explanation of the missing resource
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}