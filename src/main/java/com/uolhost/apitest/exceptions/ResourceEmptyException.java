package com.uolhost.apitest.exceptions;

public class ResourceEmptyException extends RuntimeException {
    public ResourceEmptyException(String msg) {
        super(msg);
    }
}
