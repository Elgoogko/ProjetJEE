package com.Exceptions;

public class SearchException extends RuntimeException {
    public SearchException(String msg) {
        throw new RuntimeException(msg);
    }
}
