package org.kucher.userservice.config.exceptions;

import io.jsonwebtoken.ExpiredJwtException;
import org.kucher.userservice.config.exceptions.api.*;
import org.kucher.userservice.config.exceptions.dto.MultipleError;
import org.kucher.userservice.config.exceptions.dto.MultipleErrorResponse;
import org.kucher.userservice.config.exceptions.dto.SingleErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

//Еще нужно ловить ошибки аунтефикации\авторизации ???
@ControllerAdvice
public class CustomExceptionHandler
        extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AlreadyChangedException.class, BadCredentialsException.class, ExpiredJwtException.class,
            InvalidConfirmRegistrationTokenException.class, UserNotActivatedException.class, AlreadyActivatedException.class})
    protected ResponseEntity<Object> handleAppHashIsLoaded(RuntimeException ex) {
        SingleErrorResponse error = new SingleErrorResponse("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.SERVICE_UNAVAILABLE);
    }

    //Еще нужно ловить сюда если Json Не смог десериализоваться
    @ExceptionHandler(value = EntityNotFoundException.class)
    protected ResponseEntity<Object> handleUserNotFound(Exception ex) {
        SingleErrorResponse error = new SingleErrorResponse("error", "The request contains invalid data. Please edit the request and resubmit it");
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({JwtTokenGenerationException.class, IllegalArgumentException.class, ClassCastException.class})
    protected ResponseEntity<Object> handleJwtTokenGeneration(RuntimeException ex) {
        SingleErrorResponse error = new SingleErrorResponse("error", "The server was unable to process the request correctly. Please contact the administrator");
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        MultipleErrorResponse errors = new MultipleErrorResponse("error");
        List<MultipleError> listErrors = new ArrayList<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            listErrors.add(new MultipleError(((FieldError) error).getField(), error.getDefaultMessage()));
        });

        errors.setErrors(listErrors);

        return new ResponseEntity<>(errors, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
