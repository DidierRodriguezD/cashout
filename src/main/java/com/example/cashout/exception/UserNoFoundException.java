package com.example.cashout.exception;

public class UserNoFoundException extends RuntimeException {
    public UserNoFoundException(String message) {
        super(message);
    }
}
