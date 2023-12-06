package com.uolhost.apitest.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.UnexpectedRollbackException;
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

    @ExceptionHandler(UnexpectedRollbackException.class)
    ResponseEntity<String> handle404ResourceEmpty(UnexpectedRollbackException e) {
        return badRequest().body("{ \"message\": \"Endereço de e-mail deve ser válido\" }");
    }


    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handle404ResourceEmpty() {
        return badRequest().body("{ \"message\": \"Endereço de e-mail deve ser válido\" }");
    }

    @ExceptionHandler(GroupListNotAvailableException.class)
    ResponseEntity<String> handle400GroupListNotAvailable(GroupListNotAvailableException e) {
        return badRequest().body(e.getMessage());
    }
}
