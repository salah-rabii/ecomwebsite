package com.myecommerce.exception;

public class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }

    // Optionnel : Ajoutez un constructeur avec cause
    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }
}