package com.allegromini.front.exception;

public class AuthorizationServiceException extends RuntimeException {
    public AuthorizationServiceException(String message) {
        super(message);
    }
}
