package com.example.anapo.user.exception;

public class DuplicateBookmarkException extends RuntimeException {
    public DuplicateBookmarkException(String message) {
        super(message);
    }
}