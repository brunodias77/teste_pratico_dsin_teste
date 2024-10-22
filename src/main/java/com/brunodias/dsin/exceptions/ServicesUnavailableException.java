package com.brunodias.dsin.exceptions;

public class ServicesUnavailableException extends RuntimeException {
    public ServicesUnavailableException(String message) {
        super(message);
    }
}