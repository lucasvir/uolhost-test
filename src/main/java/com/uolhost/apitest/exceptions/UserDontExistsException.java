package com.uolhost.apitest.exceptions;

public class UserDontExistsException extends RuntimeException {
    public UserDontExistsException(String msg) {
        super(msg);
    }
}
