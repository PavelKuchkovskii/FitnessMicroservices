package org.kucher.userservice.config.exceptions.api;

public class InvalidConfirmRegistrationTokenException extends RuntimeException {

    public InvalidConfirmRegistrationTokenException(String message) {
        super(message);
    }
}
