package com.uolhost.apitest.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    ResponseEntity<String> handle400AlreadyRegisteredByEmail(UserAlreadyRegisteredException e) {
        return badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ResourceEmptyException.class)
    ResponseEntity<String> handle404ResourceEmpty(ResourceEmptyException e) {
        return notFound().build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handle404ResourceEmpty(ConstraintViolationException e) {
        return badRequest().body("{\"error\": \"E-mail must be well-formed address\" }");
    }
}
