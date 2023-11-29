package com.uolhost.apitest.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {
    public UserAlreadyRegisteredException(String msg) {
        super(msg);
    }
}
