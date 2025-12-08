package com.Exceptions;

public class CommentServiceException extends RuntimeException {
    public CommentServiceException(String messString) {
        throw new RuntimeException(messString);
    }
}
