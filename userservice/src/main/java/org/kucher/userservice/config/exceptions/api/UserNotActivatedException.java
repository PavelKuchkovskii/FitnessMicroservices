package org.kucher.userservice.config.exceptions.api;

public class UserNotActivatedException extends RuntimeException {

    public UserNotActivatedException(String message) {
        super(message);
    }
}
