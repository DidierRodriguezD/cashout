package com.example.cashout.exception;

public class CashoutNoFoundException extends RuntimeException{
    public CashoutNoFoundException(String message) {
        super(message);
    }
}
