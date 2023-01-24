package org.kucher.userservice.config.exceptions.api;

public class AlreadyActivatedException extends RuntimeException {
    public AlreadyActivatedException(String message) {
        super(message);
    }
}
