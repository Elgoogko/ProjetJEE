package com.exceptions;

public class ServicesException extends RuntimeException {
    public ServicesException(String errmessString) {
        throw new RuntimeException(errmessString);
    }
}
