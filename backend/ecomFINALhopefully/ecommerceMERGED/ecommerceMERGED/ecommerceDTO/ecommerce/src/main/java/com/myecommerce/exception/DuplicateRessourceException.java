package com.myecommerce.exception;

public class DuplicateRessourceException extends RuntimeException {
    public DuplicateRessourceException(String message) {
        super(message);
    }
    public DuplicateRessourceException(String message, Throwable cause) {
        super(message, cause);
    }
}