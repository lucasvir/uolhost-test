package com.uolhost.apitest.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.ResponseEntity.badRequest;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    ResponseEntity<String> handle400AlreadyRegisteredByEmail(UserAlreadyRegisteredException e) {
        return badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ResourceEmptyException.class)
    ResponseEntity<String> handle404ResourceEmpty(ResourceEmptyException e) {
        return badRequest().body(e.getMessage());
    }
}
